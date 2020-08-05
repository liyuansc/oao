package com.oao.common.constant;

import com.oao.common.model.Result;

/**
 * 前0000-0999位=建议为通用错误码，业务不要与其重复
 * <p>
 * Created by liyu on 2020/2/23
 */
public enum ResultCode {
    R200(200, "成功"),
    //1000-2000 常规错误

    R1000(1000, "系统繁忙，请稍候再试"),//未知异常
    R1001(1001, "系统繁忙，请稍候再试"),//数据库异常


    R1101(1001, "服务访问失败"),
    R1102(1002, "不支持当前访问方式"),
    R1103(1003, "参数校验不通过"),


    //2000-3000 security
    R2000(2000, "认证失败"),
    R2001(2001, "无权访问"),

    //3000-4000 gateway
    R3000(3000, "服务访问失败"),

    ;

    // 成员变量
    private Integer code;
    private String msg;

    ResultCode(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public Integer code() {
        return code;
    }

    public String msg() {
        return msg;
    }

    public <T> Result<T> build() {
        return new Result<>(this.code, this.msg);
    }

    public <T> Result<T> build(T data) {
        return new Result<>(this.code, this.msg, data);
    }

}
