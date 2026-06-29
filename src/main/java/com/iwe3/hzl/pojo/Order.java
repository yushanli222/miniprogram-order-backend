package com.iwe3.hzl.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Order {
    private String oid;
    private Integer tableId;
    private String createTime;
    private Integer totalPrice;
    private String openid;
    private String payway;
    private Integer eatNumber;
    private String remark;
    private Integer status;
}
