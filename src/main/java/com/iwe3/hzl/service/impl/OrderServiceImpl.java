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
import com.iwe3.hzl.service.FoodService;
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
            customerDao.subCustomerMoney(openid,totalPrice);
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
            return ResultGenerator.genSuccessResult("下单成功");
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
                foodDao.updateFoodStorage(detail.getFoodName(),detail.getFoodQuantity());
            }
            return ResultGenerator.genFailResult("余额不足,请重新支付！");
        }
    }


    @Override
    public PageInfo<OrderMap> getOrderByStatus(String openid,
                                               Integer status,
                                               Integer currentPage,
                                               Integer pageSize) {
        //分页查询
        PageHelper.startPage(currentPage, pageSize);

        List<OrderMap> orderMaps = orderDao.selectOrderByStatus(openid,status);
        //获取orderMaps集合中的订单信息
        for (OrderMap orderMap : orderMaps) {
            String oid = orderMap.getOid();
            //通过oid查询所有的详情
            List<Detail> details = orderDao.selectDetailByOid(oid);
            orderMap.setOrderDetailList(details);
        }
        //把相关信息放入分页对象
        PageInfo<OrderMap> orderMapPageInfo = new PageInfo<>(orderMaps);
        return orderMapPageInfo;
    }

}
