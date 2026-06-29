package com.iwe3.hzl.api;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

/**
 * 封装小程序创建的订单请求(包含订单详细内容)
 */
@Data
public class OrderVo {

    @JsonProperty("openid")
    private String openid;

    @JsonProperty("payway")
    private String payway;

    @JsonProperty("tableId")
    private Integer tableId;

    @JsonProperty("eatNumber")
    private Integer eatNumber;

    @JsonProperty("totalPrice")
    private Integer totalPrice;

    @JsonProperty("remark")
    private String remark;

    @JsonProperty("orderDetail")
    private String orderDetail;
}
