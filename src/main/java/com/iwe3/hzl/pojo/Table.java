package com.iwe3.hzl.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data   /* getter和setter方法  */
@NoArgsConstructor  /*无参构造*/
@AllArgsConstructor /*有参构造*/
public class Table {
    private Integer tableId;
    private Integer useNumber;
}
