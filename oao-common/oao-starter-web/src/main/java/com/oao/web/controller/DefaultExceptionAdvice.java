package com.oao.web.controller;

import com.oao.common.constant.ResultCode;
import com.oao.common.exception.DatabaseException;
import com.oao.common.exception.ResultException;
import com.oao.common.model.Result;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.exceptions.PersistenceException;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.multipart.support.MissingServletRequestPartException;

import javax.validation.ConstraintViolationException;
import java.sql.SQLException;
import java.util.Map;
import java.util.stream.Collectors;


/**
 * Created by liyu on 2017/9/1
 * 统一捕获异常的处理类
 * TODO 需要补充大量通用异常
 */
@Slf4j
public class DefaultExceptionAdvice {

    @ExceptionHandler({
            HttpRequestMethodNotSupportedException.class,
            HttpMediaTypeNotSupportedException.class
    })
    public Result httpRequestMethodNotSupportedException(Exception e) {
        log.error(e.getMessage(), e);
        return ResultCode.R1102.build();
    }

    @ExceptionHandler(BindException.class)
    public Result valid(BindException e) {
        Map<String, String> body = e.getBindingResult().getAllErrors()
                .stream()
                .collect(Collectors.toMap(
                        error -> error instanceof FieldError ? (((FieldError) error).getField()) : error.getObjectName(),
                        error -> error.getDefaultMessage()));
        return ResultCode.R1103.build(body);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Result valid(MethodArgumentNotValidException e) {
        Map<String, String> body = e.getBindingResult().getAllErrors()
                .stream()
                .collect(Collectors.toMap(
                        error -> error instanceof FieldError ? (((FieldError) error).getField()) : error.getObjectName(),
                        error -> error.getDefaultMessage()));
        return ResultCode.R1103.build(body);
    }

    @ExceptionHandler({
            MissingServletRequestPartException.class,
            HttpMessageNotReadableException.class,
            MissingServletRequestParameterException.class,
            ConstraintViolationException.class,
            MethodArgumentTypeMismatchException.class,
            ServletRequestBindingException.class
    })
    public Result databind(Exception e) {
        log.error(e.getMessage(), e);
        return ResultCode.R1103.build();
    }

    @ExceptionHandler({
            DatabaseException.class,
            SQLException.class,
            PersistenceException.class,
//            DataAccessException.class
    })
    public Result database(Exception e) {
        log.error(e.getMessage(), e);
        return ResultCode.R1001.build();
    }

    @ExceptionHandler({
            ResultException.class,
    })
    public Result result(ResultException e) {
        return e.getResult();
    }

    @ExceptionHandler(Exception.class)
    public Result exception(Exception e) {
        log.error(e.getMessage(), e);
        return ResultCode.R1000.build();
    }
}
