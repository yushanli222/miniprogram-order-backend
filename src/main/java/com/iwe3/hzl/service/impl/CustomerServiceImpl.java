package com.iwe3.hzl.service.impl;

import com.iwe3.hzl.api.CategoryVo;
import com.iwe3.hzl.dao.CustomerDao;
import com.iwe3.hzl.pojo.Customer;
import com.iwe3.hzl.service.CustomerService;
import com.iwe3.hzl.util.Result;
import com.iwe3.hzl.util.ResultGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerServiceImpl implements CustomerService {
    @Autowired
    private CustomerDao customerDao;

    @Override
    public Result addCustomer(Customer customer) {
        String openid = customer.getOpenid();
        Customer customer1 = customerDao.selectCustomerById(openid);
        if (customer1 != null) {
            return ResultGenerator.genFailResult("用户已经存在！");
        }
        int i = customerDao.insertCustomer(customer);
        if (i > 0) {
            List<CategoryVo> categoryVos = List.of();
            return ResultGenerator.genSuccessResult(categoryVos);

        }
        return ResultGenerator.genFailResult("注册失败！");

    }

    @Override
    public Result getUserInfo(String openid) {
        Customer customer = customerDao.selectCustomerById(openid);
        return ResultGenerator.genSuccessResult(customer);
    }
    //查询用户信息，获取到用户中的信息余额
    @Override
    public Result getBanlance(String openid) {
        Customer customer = customerDao.selectCustomerById(openid);
        Double money = customer.getMoney();
        return ResultGenerator.genSuccessResult(money);
    }
    //充值方法
    @Override
    public Result recharge(Double money, String openid) {
        int i = customerDao.updateCustomerMoney(money,openid);
        if (i > 0) {
            return ResultGenerator.genSuccessResult("充值成功");
        }
        return ResultGenerator.genFailResult("充值失败");
    }
}