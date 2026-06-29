package com.iwe3.hzl.controller;

import com.iwe3.hzl.pojo.Customer;
import com.iwe3.hzl.service.CustomerService;
import com.iwe3.hzl.util.Result;
import com.iwe3.hzl.util.ResultGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
@RestController
@RequestMapping("/wx")
public class CustomerController {
    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private CustomerService customerService;

    @RequestMapping("wxLogin")
    public Result wxLogin(String code) {
        String appid = "wxc2910637cb0e3f04";
        String secret ="4f15c42f8218991b4454423eaecd5294";
        String url = "https://api.weixin.qq.com/sns/jscode2session?appid=" + appid + "&secret=" + secret + "&js_code=" + code + "&grant_type=authorization_code";
        String result = restTemplate.getForObject(url, String.class);
        return ResultGenerator.genSuccessResult(result);
    }
    @RequestMapping("customerRegister")
    public Result customerLogin(Customer customer) {
        //完成注册
        return  customerService.addCustomer(customer);
    }
    @RequestMapping("getUserInfo")
    public Result getUserInfo(String openid) {
        return customerService.getUserInfo(openid);
    }
    @RequestMapping("getBanlance")
    public Result getBanlance(String openid) {
        return  customerService.getBanlance(openid);
    }
    @RequestMapping("recharge")
    public Result recharge(Double money, String openid) {
        return customerService.recharge(money,openid);
    }
}