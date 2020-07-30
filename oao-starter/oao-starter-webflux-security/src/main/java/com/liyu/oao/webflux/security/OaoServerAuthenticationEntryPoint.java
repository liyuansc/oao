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
package com.liyu.oao.webflux.security;

import com.liyu.oao.common.constant.ResultCode;
import com.liyu.oao.common.model.Result;
import com.liyu.oao.webflux.util.WebfluxResponseUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.server.ServerAuthenticationEntryPoint;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

/**
 * jwt auth fail point
 *
 * @author wfnuser
 */
public class OaoServerAuthenticationEntryPoint implements ServerAuthenticationEntryPoint {
    private static final Logger logger = LoggerFactory.getLogger(OaoServerAuthenticationEntryPoint.class);

    @Override
    public Mono<Void> commence(ServerWebExchange exchange, AuthenticationException e) {
        logger.error("Responding with unauthorized error. Message:{}, url:{}", e.getMessage(), exchange.getRequest().getURI());
        Result result = ResultCode.R2000.build().withMsg(e.getMessage());
        return WebfluxResponseUtils.writeJSON(exchange, result);
    }
}
