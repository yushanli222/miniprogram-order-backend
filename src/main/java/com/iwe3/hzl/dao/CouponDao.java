package com.iwe3.hzl.dao;

import com.iwe3.hzl.pojo.Coupon;
import com.iwe3.hzl.pojo.MyCoupon;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface CouponDao {

    @Select("select * from coupon")
    List<Coupon> selectAllCoupon();

    //查询新用户优惠券
    @Select("select * from mycoupon where openid =#{openid} and couid=#{couId}")
    MyCoupon selectMyCoupon(@Param("openid") String openid, @Param("couId") int couId);

    @Select("select * from mycoupon where openid=#{openid} and couid =#{couId}")
    List<MyCoupon> selectMyCoupons(@Param("openid") String openid,
                                   @Param("couId") Integer couId);

    @Select("select * from coupon where id = #{couid}")
    Coupon selectCouponByCouid(@Param("couid") Integer couid);

    @Select("select * from mycoupon where openid = #{openid}")
    List<MyCoupon> getMyCoupons(@Param("openid") String openid);

    @Select("select * from mycoupon where couid = #{couid}")
    List<MyCoupon> getMyCouponByCouid(@Param("couid") Integer couid);
}