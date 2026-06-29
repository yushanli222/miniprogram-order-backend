package com.iwe3.hzl.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Detail implements Serializable {
    private Integer did;
    private String orderId;
    private String foodName;
    private Integer foodQuantity;
    private Integer price;
    private String foodIcon;
}
