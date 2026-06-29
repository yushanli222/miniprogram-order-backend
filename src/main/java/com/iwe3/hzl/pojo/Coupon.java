package com.iwe3.hzl.pojo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

@Data
public class Coupon {
    private Integer id;
    private String cname;
    private Integer cvalue;
    private Integer ccondition;
    //时间返回JSON
    @JsonFormat(pattern = "yyyy-MM-dd",timezone = "GMT+8")
    private Date startTime;
    @JsonFormat(pattern = "yyyy-MM-dd",timezone = "GMT+8")
    private Date endTime;
    private String status;
}
