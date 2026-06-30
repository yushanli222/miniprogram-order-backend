package com.iwe3.hzl.service;

import com.github.pagehelper.PageInfo;
import com.iwe3.hzl.api.OrderMap;
import com.iwe3.hzl.api.OrderVo;
import com.iwe3.hzl.util.Result;

public interface OrderService {
    Result createOrder(OrderVo orderVo, Integer couId);
    PageInfo<OrderMap> getOrderByStatus(String openid,
                                        Integer status,
                                        Integer currentPage,
                                        Integer pageSize);
}
