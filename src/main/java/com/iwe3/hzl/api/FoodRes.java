package com.iwe3.hzl.api;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.io.Serializable;


/**
 * 返回给小程序的菜品页，包含菜品id、菜品名、菜品单价、菜品存储量、菜品介绍、菜品图片
 */
@Data
public class FoodRes implements Serializable {

    @JsonProperty("id")
    private Integer fid;

    @JsonProperty("name")
    private String fname;

    @JsonProperty("price")
    private Integer price;

    @JsonProperty("stock")
    private Integer storage;//库存

    @JsonProperty("desc")
    private String intro;

    @JsonProperty("icon")
    private String imgPath;

    //我们手动构建一个属性quantity
    @JsonProperty("quantity")
    private Integer quantity = 0;
}
