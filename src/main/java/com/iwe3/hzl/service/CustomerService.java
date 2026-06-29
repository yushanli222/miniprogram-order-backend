package com.iwe3.hzl.service;

import com.iwe3.hzl.pojo.Customer;
import com.iwe3.hzl.util.Result;

public interface CustomerService {
    Result addCustomer(Customer customer);

    Result getUserInfo(String openid);

    Result getBanlance(String openid);

    Result recharge(Double money, String openid);
}
