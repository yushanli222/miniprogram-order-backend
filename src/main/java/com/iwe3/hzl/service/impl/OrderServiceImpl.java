package com.iwe3.hzl.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.iwe3.hzl.api.OrderMap;
import com.iwe3.hzl.api.OrderVo;
import com.iwe3.hzl.dao.CouponDao;
import com.iwe3.hzl.dao.CustomerDao;
import com.iwe3.hzl.dao.FoodDao;
import com.iwe3.hzl.dao.OrderDao;
import com.iwe3.hzl.pojo.Customer;
import com.iwe3.hzl.pojo.Detail;
import com.iwe3.hzl.pojo.Order;
import com.iwe3.hzl.service.OrderService;
import com.iwe3.hzl.util.Result;
import com.iwe3.hzl.util.ResultGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {


    @Autowired
    private CustomerDao customerDao;
    @Autowired
    private CouponDao couponDao;
    @Autowired
    private OrderDao orderDao;
    @Autowired
    private FoodDao foodDao;

    @Override
    @Transactional  //事务
    public Result createOrder(OrderVo orderVo, Integer couId) {

        //将orderVo中的订单相关数据提取为Order对象
        Order order = new Order();
        order.setOpenid(orderVo.getOpenid());
        order.setTableId(orderVo.getTableId());
        order.setTotalPrice(orderVo.getTotalPrice());
        order.setEatNumber(orderVo.getEatNumber());
        order.setRemark(orderVo.getRemark());
        order.setPayway(orderVo.getPayway());

        //把orderVo中的detail详情取出 ，生成Detail对象，放到集合
        List<Detail> details = new Gson().fromJson(orderVo.getOrderDetail(),
                new TypeToken<List<Detail>>() {}.getType());

        //查询用户的余额
        String openid = order.getOpenid();
        Customer customer = customerDao.selectCustomerById(openid);
        Double balance = customer.getMoney();  //获取余额

        //判断是否使用优惠券了 , 不为-1表示使用了优惠券 ，使用了以后
        //需要把优惠券改为已使用
        if (couId != -1){
            //修改优惠券状态为已用
            couponDao.updateCouponStatus(openid,couId);
        }
        //开始创建订单，判断余额是否充足
        Integer totalPrice = orderVo.getTotalPrice();

        if (balance >= totalPrice){
            //余额充足的情况
            order.setStatus(1); //把订单状态设为待上餐
            //扣减用户余额
            customerDao.subCustomerMoney(openid, Double.valueOf(totalPrice));
            //创建订单
            orderDao.insertOrder(order);
            //添加详情到详情表，把所有菜品拿出来，加入到详情表
            for (Detail detail : details) {
                //设置详情表的订单id
                detail.setOrderId(order.getOid());

                orderDao.addOrderDetail(detail);
                //要修改菜品的数量,根据菜品的名称修改菜品是数量
                foodDao.updateFoodStorage(detail.getFoodName(),detail.getFoodQuantity());
            }
            return ResultGenerator.genSuccessResult();
        }else {
            //余额不充足的情况
            order.setStatus(0); //把订单状态设为待支付
            //创建订单
            orderDao.insertOrder(order);
            //添加详情到详情表，把所有菜品拿出来，加入到详情表
            for (Detail detail : details) {
                //设置详情表的订单id
                detail.setOrderId(order.getOid());
                orderDao.addOrderDetail(detail);
                //要修改菜品的数量,根据菜品的名称修改菜品是数量
                foodDao.updateFoodStorage(detail.getFoodName(), detail.getFoodQuantity());
            }
            return ResultGenerator.genFailResult("余额不足,请重新支付！");
        }
    }
    @Override
    public PageInfo<OrderMap> getOrderByStatus(String openid, Integer status, Integer currenPage, Integer pageSize) {
        PageHelper.startPage(currenPage, pageSize);
        List<OrderMap> orderMaps = orderDao.selectOrderByStatus(openid,status);
        for(OrderMap orderMap : orderMaps){
            String oid = orderMap.getOid();
            List<Detail> details = orderDao.selectDetailByOid(oid);
            orderMap.setOrderDetailList(details);
        }
        PageInfo<OrderMap> orderMapPageInfo = new PageInfo<>(orderMaps);
        return orderMapPageInfo;
    }
    @Override
    @Transactional  // 开启事务：扣用户余额 + 修改订单状态 必须同时成功或同时回滚，防止数据错乱
    public Result toPayOrder(Integer oid, Double money, String openid) {
        // ====================== 1、根据订单ID查询订单基础信息，做多层安全校验 ======================
        // 1.1 根据订单主键oid查询订单
        Order order = orderDao.selectOrderById(oid);

        // 校验1：订单不存在，直接返回失败
        if (order == null) {
            return ResultGenerator.genFailResult("订单不存在，支付终止");
        }

        // 校验2：防止越权操作：只能支付当前登录用户自己的订单
        if (!order.getOpenid().equals(openid)) {
            return ResultGenerator.genFailResult("非法操作，不允许支付他人订单");
        }

        // 校验3：只能支付【待支付】状态的订单（status=0 待支付；status=1 已支付/待上菜，不可重复支付）
        if (order.getStatus() != 0) {
            return ResultGenerator.genFailResult("该订单已完成支付，无需重复付款");
        }

        // 校验4：校验前端传入的支付金额和数据库订单真实金额一致，防止前端篡改支付金额作弊
        // 【唯一修改处：将精确equals改为浮点差值容错判断，解决精度报错】
        if (Math.abs(order.getTotalPrice() - money) > 0.01) {
            return ResultGenerator.genFailResult("订单支付金额异常，请刷新订单后重试");
        }

        // ====================== 2、查询当前登录用户账户余额，判断余额是否可以完成支付 ======================
        // 根据用户openid查询用户账户信息
        Customer customer = customerDao.selectCustomerById(openid);
        // 获取用户当前账户余额
        Double userBalance = customer.getMoney();

        // 余额不足，直接返回支付失败
        if (userBalance < money) {
            return ResultGenerator.genFailResult("账户余额不足，支付失败，请充值后重试");
        }

        // ====================== 3、扣减用户账户余额 ======================
        // 调用DAO层方法，从当前用户余额中扣除本次订单支付金额
        customerDao.subCustomerMoney(openid, money);

        // ====================== 4、修改订单状态：从【待支付0】更新为【已支付1】 ======================
        order.setStatus(1);
        // 把更新后的订单状态同步到数据库
        orderDao.updateOrderStatus(order);

        // ====================== 5、支付全部流程执行完毕，返回成功结果给前端 ======================
        return ResultGenerator.genSuccessResult("订单支付成功！");
    }
    @Override
    @Transactional
    public Result cancelOrder(Integer oid) {
        //1. 根据订单id查询订单
        Order order = orderDao.selectOrderById(oid);
        //订单不存在
        if(order == null){
            return ResultGenerator.genFailResult("订单不存在");
        }
        //只能取消【待支付 status=0】的订单
        if(order.getStatus() != 0){
            return ResultGenerator.genFailResult("只有待支付订单可以取消");
        }
        //执行取消，状态改为2
        int row = orderDao.cancelOrder(oid);
        if(row > 0){
            return ResultGenerator.genSuccessResult("订单取消成功");
        }
        return ResultGenerator.genFailResult("订单取消失败");
    }
}

