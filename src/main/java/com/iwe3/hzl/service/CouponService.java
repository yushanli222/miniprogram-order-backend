package com.iwe3.hzl.service;

import com.iwe3.hzl.util.Result;

public interface CouponService {



    Result getCoupons();

    Result recevieCoupon(String openid, Integer couId);

    Result getMyCoupons(String openid, String status);

    Result getAvailableCoupons(String openid);
}