package com.liyu.oao.uaa.controller;

import com.liyu.oao.common.constant.ResultCode;
import com.liyu.oao.common.model.Result;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;


/**
 * Created by liyu on 2017/9/1
 * 统一捕获异常的处理类
 * TODO 需要补充大量通用异常
 */
@RestControllerAdvice
@Order(Ordered.HIGHEST_PRECEDENCE)
public class ControllerAdvice {
    private Logger logger = LoggerFactory.getLogger(ControllerAdvice.class);

//    @Autowired
//    private ServerAccessDeniedHandler serverAccessDeniedHandler;
//    @Autowired
//    private ServerAuthenticationEntryPoint serverAuthenticationEntryPoint;
//
//    @ExceptionHandler(AccessDeniedException.class)
//    public Mono<Void> accessDenied(ServerWebExchange exchange, AccessDeniedException e) {
//        return serverAccessDeniedHandler.handle(exchange, e);
//    }
//
//    @ExceptionHandler(AuthenticationException.class)
//    public Mono<Void> auth(ServerWebExchange exchange, AuthenticationException e) {
//        return serverAuthenticationEntryPoint.commence(exchange, e);
//    }
//
    @ExceptionHandler(Exception.class)
    public Result exception(Exception e) {
        logger.error(e.getMessage(), e);
        return ResultCode.R1000.build();
    }
}
