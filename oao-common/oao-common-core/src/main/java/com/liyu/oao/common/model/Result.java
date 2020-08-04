package com.liyu.oao.common.model;


import com.liyu.oao.common.constant.CurrentApp;
import com.liyu.oao.common.constant.ResultCode;
import com.liyu.oao.common.exception.ResultException;

/**
 * Created by liyu on 2018/1/19.
 */
public class Result<T> {
    private Integer code;
    private String msg;
    private T data;
    private String appId;

    {
        this.appId = CurrentApp.getAppId();
    }

    public Result() {
    }

    public Result(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public Result(Integer code, String msg, T data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    public static <T> Result<T> success() {
        return success(null);
    }

    public static <T> Result<T> success(T data) {
        return ResultCode.R200.build(data);
    }

    public static <T> Result<T> failed() {
        return failed(null);
    }

    public static <T> Result<T> failed(T data) {
        return ResultCode.R1000.build(data);
    }

    public Result<T> check() {
        if (!ResultCode.R200.code().equals(this.code)) {
            throw new ResultException(this);
        }
        return this;
    }


    public Result<T> withCode(Integer code) {
        this.code = code;
        return this;
    }

    public Result<T> withMsg(String msg) {
        this.msg = msg;
        return this;
    }

    public Result<T> withData(T data) {
        this.data = data;
        return this;
    }


    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }
}
