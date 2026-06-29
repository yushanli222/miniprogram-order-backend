package com.iwe3.hzl.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Customer {
    private String openid; //
    private String nickname;
    private String phoneNumber;
    private String avatarUrl;
    private Double money; //默认值 0.00
}
