package com.liyu.oao.webflux.security;

import com.liyu.oao.common.constant.ResultCode;
import com.liyu.oao.common.model.Result;
import com.liyu.oao.webflux.util.WebfluxResponseUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.server.authorization.ServerAccessDeniedHandler;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

public class OaoServerAccessDeniedHandler implements ServerAccessDeniedHandler {
    private static final Logger logger = LoggerFactory.getLogger(OaoServerAuthenticationEntryPoint.class);

    @Override
    public Mono<Void> handle(ServerWebExchange exchange, AccessDeniedException e) {
        ServerHttpRequest request = exchange.getRequest();
        logger.error("Responding with unauthorized error. Message:{}, url:{}", e.getMessage(), request.getURI());
        Result result = Result.failed().withCode(ResultCode.R2000.code()).withMsg(e.getMessage());
        return WebfluxResponseUtils.writeJSON(exchange, result);
    }
}
