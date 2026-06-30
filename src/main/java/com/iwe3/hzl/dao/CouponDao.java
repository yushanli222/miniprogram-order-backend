package com.iwe3.hzl.dao;

import com.iwe3.hzl.pojo.Coupon;
import com.iwe3.hzl.pojo.MyCoupon;
import org.apache.ibatis.annotations.*;

import java.util.List;
@Mapper
public interface CouponDao {
    @Select("select * from coupon")
    List<Coupon> selectAllCoupon();

    @Select("select * from mycoupon where openid = #{openid} and couId = 1")
    MyCoupon selectMyCoupon(String openid);

    @Select("select * from mycoupon where openid = #{openid} and couId = #{couId}")
    List<MyCoupon> selectMyCoupons(@Param("openid") String openid,@Param("couId") Integer couId);

    @Insert("insert into mycoupon values(null,#{openid},#{couId},'available',now(),null) ")
    int insertMyCoupon(@Param("openid") String openid, @Param("couId") Integer couId);

    @Select("select  * from mycoupon where openid = #{openid}")
    List<MyCoupon> getMyCoupons(String openid);

    @Select("select * from coupon where id = #{couid}")
    Coupon selectCouponByCouid(Integer couid);

    @Select("select * from mycoupon where openid = #{openid} and status ='available'")
    List<MyCoupon> selectAvailableCoupons(String openid);

    @Update("update mycoupon set status = 'used' where openid = #{openid} and couId = #{couId}")
    void updateCouponStatus(@Param("openid") String openid,
                            @Param("couId") Integer couId);
}