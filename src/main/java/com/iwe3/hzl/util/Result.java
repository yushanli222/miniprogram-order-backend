package com.iwe3.hzl.util;

import com.alibaba.fastjson2.JSON;

import java.io.Serializable;

/**
 * 统一API响应结果封装
 */
public class Result implements Serializable{

    private int code; //状态码  200   404  500
    private String message; // 响应的提示信息
    private Object data; // data就是返回的结果

    public Result setCode(ResultCode resultCode) {
        this.code = resultCode.code;
        return this;
    }

    public int getCode() {
        return code;
    }

    public Result setCode(int code) {
        this.code = code;
        return this;
    }

    public String getMessage() {
        return message;
    }

    public Result setMessage(String message) {
        this.message = message;
        return this;
    }

    public Object getData() {
        return data;
    }

    public Result setData(Object data) {
        this.data = data;
        return this;
    }

    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }
}
