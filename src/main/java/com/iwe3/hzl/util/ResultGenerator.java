package com.iwe3.hzl.util;

import com.iwe3.hzl.api.CategoryVo;

import java.util.List;

/**
 * 响应结果生成工具
 */
public class ResultGenerator {

    private static final String DEFAULT_SUCCESS_MESSAGE = "SUCCESS";



    public static Result genSuccessResult(Object data) {
        return new Result()
                .setCode(ResultCode.SUCCESS)
                .setMessage(DEFAULT_SUCCESS_MESSAGE)
                .setData(data);
    }

    public static Result genFailResult(String message) {
        return new Result()
                .setCode(ResultCode.FAIL)
                .setMessage(message);
    }


    public static Result genSFailResult(String 有未使用优惠券) {
        return null;
    }

    public static Result genSuccessResult() {
        return null;
    }
}
