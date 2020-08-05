package com.oao.gateway.security;

import com.oao.security.OaoBearerTokenAuthenticationToken;
import com.oao.common.constant.OaoSecurityConstant;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.server.authentication.ServerAuthenticationConverter;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

public class OaoTokenAuthenticationConverter implements ServerAuthenticationConverter {



    @Override
    public Mono<Authentication> convert(ServerWebExchange exchange) {
        ServerHttpRequest request = exchange.getRequest();
        String accessToken = request.getQueryParams().getFirst(OaoSecurityConstant.QueryParam.ACCESS_TOKEN);
        if (accessToken == null) {
            accessToken = request.getHeaders().getFirst(OaoSecurityConstant.HttpHeader.ACCESS_TOKEN);
        }
        return Mono.justOrEmpty(accessToken).map(token -> new OaoBearerTokenAuthenticationToken(token));
    }
}
