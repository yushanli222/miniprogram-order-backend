package com.iwe3.hzl.controller;

import com.iwe3.hzl.service.CouponService;
import com.iwe3.hzl.util.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/wx")//为了类被读取加载
public class CouponController {
    @Autowired
    private CouponService couponService;

    @RequestMapping("getAllCoupon")
    public Result getAllCoupon() {
        return  couponService.getCoupons();
    }

    @RequestMapping("recevieCoupon")
    public  Result recevieCoupon(String openid,Integer couId) {
        return couponService.recevieCoupon(openid,couId);
    }
    @RequestMapping("getMyCoupons")
    public Result getMyCoupons(String openid,String status) {
        return couponService.getMyCoupons(openid,status);
    }
    @RequestMapping("getAllAvailableCoupons")
    public  Result getAllAvailableCoupons(String openid) {
        return couponService.getAvailableCoupons(openid);
    }
}