package com.iwe3.hzl.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Category {
    private Integer cid;
    private String kindName;
}
/*
Java中有一种面向对象的思想
对象关系映射：将实体类中的属性和表中字段名称保持一致
 */
