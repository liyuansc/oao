/*
 * Copyright 1999-2018 Alibaba Group Holding Ltd.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.oao.gateway.security;

import com.oao.common.constant.ResultCode;
import com.oao.common.model.Result;
import com.oao.webflux.util.WebfluxResponseUtils;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.server.ServerAuthenticationEntryPoint;
import org.springframework.security.web.server.authorization.ServerAccessDeniedHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;


/**
 * jwt auth fail point
 *
 * @author wfnuser
 */
@Component
@Slf4j
public class OaoServerAuthenticationEntryPoint implements ServerAuthenticationEntryPoint {
    @Autowired
    private ServerAccessDeniedHandler accessDeniedHandler;

    @Override
    public Mono<Void> commence(ServerWebExchange exchange, AuthenticationException e) {
        return Mono.defer(() -> {
//            //兼容处理，具体看ExceptionTranslationWebFilter.class
            if (e instanceof AuthenticationCredentialsNotFoundException && e.getCause() instanceof AccessDeniedException) {
                return accessDeniedHandler.handle(exchange, (AccessDeniedException) e.getCause());
            }
            log.debug("Responding with unauthorized error. Message:{}, url:{}", e.getMessage(), exchange.getRequest().getURI());
            Result result = ResultCode.R2000.build();
            return WebfluxResponseUtils.writeJSON(exchange, result);
        });
    }
}
