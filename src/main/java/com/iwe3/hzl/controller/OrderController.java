package com.iwe3.hzl.controller;


import com.github.pagehelper.PageInfo;
import com.iwe3.hzl.api.OrderMap;
import com.iwe3.hzl.api.OrderVo;
import com.iwe3.hzl.service.OrderService;
import com.iwe3.hzl.util.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/wx/")
public class OrderController {
    @Autowired
    private OrderService orderService;

    @RequestMapping("createOrder")
    public Result createOrder(OrderVo orderVo , Integer couId) {

        return orderService.createOrder(orderVo,couId);
    }
    @RequestMapping("getOrderByStatus")
    public Object getOrderByStatus(String openid ,
                                   Integer status,
                                   Integer currentPage ,
                                   Integer pageSize){
        PageInfo<OrderMap> orderMapPageInfo=
                orderService.getOrderByStatus(openid,status,currentPage,pageSize);
        return orderMapPageInfo;
    }
    @RequestMapping("toPayOrder")
    public Result toPayOrder(Integer oid,
                             Double money,
                             String openid){
        return orderService.toPayOrder(oid,money,openid);


    }
    @RequestMapping("cancelOrder")
    public Result cancelOrder(Integer oid){
        return orderService.cancelOrder(oid);
    }
}
