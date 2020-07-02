package com.liyu.oao.gateway.security;

import com.liyu.oao.security.JwtTokenManage;
import com.liyu.oao.security.OaoBearerTokenAuthenticationToken;
import io.jsonwebtoken.ExpiredJwtException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.core.Authentication;
import reactor.core.publisher.Mono;

public class OaoTokenAuthenticationManager implements ReactiveAuthenticationManager {
    @Autowired
    private JwtTokenManage jwtTokenManage;

    @Override
    public Mono<Authentication> authenticate(Authentication authentication) {
        return Mono.justOrEmpty(authentication)
                .filter(a -> a instanceof OaoBearerTokenAuthenticationToken)
                .cast(OaoBearerTokenAuthenticationToken.class)
                .map(a -> jwtTokenManage.getAuthentication(a.getToken()))
                .onErrorResume(e -> {
                    if (e instanceof ExpiredJwtException) {
                        return Mono.error(new BadCredentialsException("access token expired"));
                    } else {
                        return Mono.error(new BadCredentialsException("invalid access token"));
                    }
                });
    }
}
