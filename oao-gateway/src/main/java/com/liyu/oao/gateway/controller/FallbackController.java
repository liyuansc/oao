package com.liyu.oao.gateway.controller;

import com.liyu.oao.common.constant.ResultCode;
import com.liyu.oao.common.model.Result;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gateway.route.Route;
import org.springframework.cloud.gateway.support.ServerWebExchangeUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ServerWebExchange;


/**
 * Created by liyu on 2018/11/21.
 */
@RestController
@RequestMapping("fallback")
public class FallbackController {
    private Logger log = LoggerFactory.getLogger(FallbackController.class);

    @Value("${hystrixLog:false}")
    private boolean hystrixLog;

    @RequestMapping
    public Result<Void> fallback(ServerWebExchange exchange) {
        Route route = exchange.getAttribute(ServerWebExchangeUtils.GATEWAY_ROUTE_ATTR);
        Result result = ResultCode.R3000.build();
        if (route == null) {
            return result;
        }
        String msg = "服务繁忙中，请稍候再试: " + route.getId();
        Throwable e = exchange.getAttribute(ServerWebExchangeUtils.HYSTRIX_EXECUTION_EXCEPTION_ATTR);
        if (hystrixLog && e != null) {
            //TODO 测试删除
            log.error(msg, e);
        }
        return result.withMsg(msg);
    }
}
