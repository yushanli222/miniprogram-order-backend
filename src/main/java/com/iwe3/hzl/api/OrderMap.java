package com.iwe3.hzl.api;

import com.iwe3.hzl.pojo.Detail;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/*
 * 封装小程序传至后端的订单信息
 */
@Data
public class OrderMap implements Serializable {

    private String oid;

    private Integer tableId;

    private Integer status;

    private Integer totalPrice;

    private Date createTime;

    private List<Detail> orderDetailList;
}
