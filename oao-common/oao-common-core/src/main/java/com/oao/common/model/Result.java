package com.oao.common.model;


import com.oao.common.constant.CurrentApp;
import com.oao.common.constant.ResultCode;
import com.oao.common.exception.ResultException;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created by liyu on 2018/1/19.
 */
@Data
public class Result<T> {
    private Integer code;
    private String msg;
    private T data;
    private String appId;

    {
        this.appId = CurrentApp.getAppId();
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
}
