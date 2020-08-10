package com.oao.gateway.security;

import com.oao.common.constant.ResultCode;
import com.oao.common.model.Result;
import com.oao.webflux.util.WebfluxResponseUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.AuthorizationServiceException;
import org.springframework.security.web.server.authorization.ServerAccessDeniedHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Component
public class OaoServerAccessDeniedHandler implements ServerAccessDeniedHandler {
    private static final Logger logger = LoggerFactory.getLogger(OaoServerAuthenticationEntryPoint.class);

    @Override
    public Mono<Void> handle(ServerWebExchange exchange, AccessDeniedException e) {
        ServerHttpRequest request = exchange.getRequest();
        logger.debug("AccessDenied. Message:{}, url:{}", e.getMessage(), request.getURI());
        Result result;
        if (e instanceof AuthorizationServiceException) {
            //授权过程中出现异常
            result = ResultCode.R2002.build();
        } else {
            //无权限拒绝访问
            result = ResultCode.R2001.build();
        }
        return WebfluxResponseUtils.writeJSON(exchange, result);
    }
}
