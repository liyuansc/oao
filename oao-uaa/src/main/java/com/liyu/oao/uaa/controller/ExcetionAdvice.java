package com.liyu.oao.uaa.controller;

import com.liyu.oao.web.controller.DefaultExceptionAdvice;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.web.bind.annotation.RestControllerAdvice;


/**
 * Created by liyu on 2017/9/1
 * 统一捕获异常的处理类
 * TODO 需要补充大量通用异常
 */
@RestControllerAdvice
@Order(Ordered.HIGHEST_PRECEDENCE)
public class ExcetionAdvice extends DefaultExceptionAdvice {
    private Logger logger = LoggerFactory.getLogger(ExcetionAdvice.class);

}
