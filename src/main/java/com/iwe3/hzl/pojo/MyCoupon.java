package com.iwe3.hzl.pojo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

@Data
public class MyCoupon {
    private Integer id;
    private String openid;
    private Integer couid;
    private String status;
    @JsonFormat(pattern = "yyyy-MM-dd",timezone = "GMT+8")
    private Date collTime;
    @JsonFormat(pattern = "yyyy-MM-dd",timezone = "GMT+8")
    private Date useTime;
    // 优惠券信息
    private Coupon coupon;
}
