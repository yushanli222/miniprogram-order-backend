package com.iwe3.hzl.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Food {
    private Integer fid;
    private String fname;
    private String fkind;
    private Integer price;
    private String intro;
    private Integer storage;
    private String imgPath;
    private Integer isRecommend;
}
