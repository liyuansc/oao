package com.oao.user.controller;

import com.oao.web.controller.DefaultExceptionAdvice;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RestControllerAdvice;


/**
 * Created by liyu on 2017/9/1
 * 统一捕获异常的处理类
 * TODO 需要补充大量通用异常
 */
@RestControllerAdvice
public class ExceptionAdvice extends DefaultExceptionAdvice {
}
