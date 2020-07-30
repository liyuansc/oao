package com.liyu.oao.gateway.security;

import com.liyu.oao.security.OaoBearerTokenAuthenticationToken;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.common.exceptions.InvalidTokenException;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.TokenStore;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Scheduler;
import reactor.core.scheduler.Schedulers;

public class OaoOauth2AuthenticationManager implements ReactiveAuthenticationManager {
    private TokenStore tokenStore;

    public OaoOauth2AuthenticationManager(TokenStore tokenStore) {
        this.tokenStore = tokenStore;
    }

    private final static Scheduler scheduler = Schedulers.newElastic("oauth2-authenticate-scheduler");

    @Override
    public Mono<Authentication> authenticate(Authentication authentication) {
        return Mono.justOrEmpty(authentication)
                .filter(a -> a instanceof OaoBearerTokenAuthenticationToken)
                .cast(OaoBearerTokenAuthenticationToken.class)
                .publishOn(scheduler)
                .flatMap(a -> {
                    String tokenValue = a.getToken();
                    try {
                        OAuth2AccessToken accessToken = tokenStore.readAccessToken(tokenValue);
                        if (accessToken == null) {
                            return Mono.error(new InvalidTokenException("Invalid access token: " + tokenValue));
                        } else if (accessToken.isExpired()) {
                            tokenStore.removeAccessToken(accessToken);
                            return Mono.error(new InvalidTokenException("Access token expired: " + tokenValue));
                        }
                        OAuth2Authentication result = tokenStore.readAuthentication(accessToken);
                        if (result == null) {
                            return Mono.error(new InvalidTokenException("Invalid access token: " + tokenValue));
                        }
                        return Mono.just((Authentication) result);
                    } catch (Exception e) {
                        return Mono.error(new InvalidTokenException("Invalid access token: " + tokenValue));
                    }
                }).onErrorResume(e -> Mono.error(new BadCredentialsException(e.getMessage())));
    }
}
