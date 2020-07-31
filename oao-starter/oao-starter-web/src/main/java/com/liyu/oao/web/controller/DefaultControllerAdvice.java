package com.liyu.oao.web.controller;

import com.liyu.oao.common.constant.ResultCode;
import com.liyu.oao.common.model.Result;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;


/**
 * Created by liyu on 2017/9/1
 * 统一捕获异常的处理类
 * TODO 需要补充大量通用异常
 */
@RestControllerAdvice
public class DefaultControllerAdvice {
    private Logger logger = LoggerFactory.getLogger(DefaultControllerAdvice.class);

    @ExceptionHandler(Exception.class)
    public Result exception(Exception e) {
        logger.error(e.getMessage(), e);
        return ResultCode.R1000.build();
    }
}