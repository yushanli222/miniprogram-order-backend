package com.iwe3.hzl.service.impl;

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
        List<Coupon> list=couponDao.selectAllCoupon();
        return ResultGenerator.genSuccessResult(list);
    }
    @Override
    public Result recevieCoupon(String openid,Integer couId){
        //根据优惠券id和openid去查这个用户是否领取过优惠券
        //判断优惠券id，如果是新人用券，只能领取一次
        //如果是其他优惠券，领取后判断是否已经使用，如果没使用，就不能再领
        //如果已经使用了，可再领取一份
        //判断如果优惠券id=1，表示是新用户，如果查到数据就不能再领了
        if(couId == 1){
            MyCoupon myCoupon=couponDao.selectMyCoupon(openid);
            //表示已经领取过
            if(myCoupon != null){
                return ResultGenerator.genFailResult("只能领取一次");
            }
        }else{
            //领取其他优惠券
            List<MyCoupon> myCoupons=couponDao.selectMyCoupons(openid,couId);
            //判断其他的优惠券是否已经使用，如果存在可以使用的优惠卷，那么就不能再领
            if(myCoupons !=null){
                for(MyCoupon myCoupon:myCoupons){
                    String status=myCoupon.getStatus();
                    if("available".equals(status)){
                        return ResultGenerator.genFailResult("有未使用优惠券");
                    }
                }
            }
        }
        //上面的判断完
        int i=couponDao.insertMyCoupon(openid,couId);
        if(i>0){
            //领取成功
            return ResultGenerator.genSuccessResult();
        }
        return ResultGenerator.genFailResult("领取失败");
    }

    @Override
    public Result getMyCoupons(String openid, String status) {
        //查询到所有的优惠券
        List<MyCoupon> list = couponDao.getMyCoupons(openid);
        //需要把我领取的每张优惠券的具体信息，绑定到coupon属性
        //使用循环遍历我的所有优惠券集合
        for(MyCoupon myCoupon:list){
            //拿到我领取的每一个 我的优惠券信息，获取对应的优惠券id
            Integer couid=myCoupon.getCouid();
            Coupon coupon = couponDao.selectCouponByCouid(couid);
            myCoupon.setCoupon(coupon);
        }
        if("all".equals(status)){
            return ResultGenerator.genSuccessResult(list);
        }
        List<MyCoupon> newList=new ArrayList<>();
        list.stream().filter(s->s.getStatus().equals(status))
                .forEach(s->newList.add(s));
        return ResultGenerator.genSuccessResult(newList);
    }

    @Override
    public Result getAvailableCoupons(String openid) {
        List<MyCoupon> myCoupons = couponDao.selectAvailableCoupons(openid);
        for(MyCoupon myCoupon:myCoupons){
            Integer couid=myCoupon.getCouid();
            Coupon coupon = couponDao.selectCouponByCouid(couid);
            myCoupon.setCoupon(coupon);
        }

        return ResultGenerator.genSuccessResult(myCoupons);
    }

}