package com.iwe3.hzl.api;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 商品(包含类目)
 */
@Data
public class CategoryVo implements Serializable {

    @JsonProperty("name")
    private String kindName;

    @JsonProperty("type")
    private Integer cid;

    @JsonProperty("foods")
    private List<FoodRes> foodResList;
}
