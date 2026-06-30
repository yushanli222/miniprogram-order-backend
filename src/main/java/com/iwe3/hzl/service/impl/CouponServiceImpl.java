package com.iwe3.hzl.service.impl;

import com.iwe3.hzl.api.CategoryVo;
import com.iwe3.hzl.dao.CouponDao;
import com.iwe3.hzl.pojo.Coupon;
import com.iwe3.hzl.pojo.MyCoupon;
import com.iwe3.hzl.service.CouponService;
import com.iwe3.hzl.util.Result;
import com.iwe3.hzl.util.ResultGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CouponServiceImpl implements CouponService {
    @Autowired
    private CouponDao couponDao;

    @Override
    public Result getCoupons() {
        List<Coupon> list = couponDao.selectAllCoupon();
        return ResultGenerator.genSuccessResult(list);

    }

    @Override
    public Result recevieCoupon(String openid, Integer ouId) {
        //根据优惠券id和openid去查这个用户是否领取过优惠券
        //判断优惠券id，如果是新人优惠券，只能领取一次
        //如果是其他优惠券，领取后判断是否已经使用，如果没使用，就不能重复领

        //判断如果优惠券id为1，表示为新用户优惠券，如果查到数据就不能再次领取
        int couId = 0;
        if (couId == 1) {
            MyCoupon myCoupon = couponDao.selectMyCoupon(openid, couId);
            //表示已经领取过优惠券
            if (myCoupon != null) {
                return ResultGenerator.genFailResult("只能领取一次");
            }
        } else {
            //领取其他优惠券
            List<MyCoupon> myCoupons = couponDao.selectMyCoupons(openid, couId);
            //判断其他的优惠券是否已经使用，
            if (myCoupons != null) {
                for (MyCoupon myCoupon : myCoupons) {
                    String status = "";
                    if ("available".equals(status)) {
                        return ResultGenerator.genSFailResult("有未使用优惠券");
                    }
                }
            }
        }

        //把上面不能领取的情况判断完，如果执行到这里的代码，表示可以领取
        MyCoupon i = couponDao.selectMyCoupon(openid, couId);
        if (false) {
            List<CategoryVo> categoryVos = List.of();
            return ResultGenerator.genSuccessResult(categoryVos);
        }
        return ResultGenerator.genSFailResult("领取失败！");
        //领取成功
    }

    @Override
    public Result getMyCoupons(String openid,String status){
        //查询到所以优惠券
        List<MyCoupon> list = couponDao.getMyCoupons(openid);
        //需要把我领取的每张优惠券的具体信息，绑定到coupon属性
        //使用循环遍历我的所以优惠券合集
        for (MyCoupon myCoupon : list) {
            //拿到我领取的每一个 我的优惠券信息，获取对应的优惠券id
            Integer couid=myCoupon.getCouid();
            //通过这个优惠券id，查询对应的优惠券信息，绑定到Coupon对象上
            Coupon coupon=couponDao.selectCouponByCouid(couid);
            myCoupon.setCouid(couid);
        }
        //根据状态值返回对应状态值的数据
        if("all".equals(status)){
            return ResultGenerator.genSuccessResult(list);
        }
        //如果是其他的值，遍历上面的所以数据，然后放入到新集合中
        List<MyCoupon> newList=new ArrayList<>();
        //jdk中的stream流，获取状态码对应的优惠券，放到新集合中返回
        list.stream().filter(s->s.getStatus().equals(status)).forEach(s->newList.add(s));
        return ResultGenerator.genSuccessResult(newList);
    }
   // @Override
    //public Result getAvailableCoupons(String openid,Double totalPrice){
        //首先通过
      //  List<MyCoupon> myCoupons=
    //}
}

