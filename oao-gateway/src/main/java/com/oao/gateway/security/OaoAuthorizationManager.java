package com.oao.gateway.security;

import com.oao.common.model.OaoGrantedAuthority;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.security.authorization.AuthorizationDecision;
import org.springframework.security.authorization.ReactiveAuthorizationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.server.authorization.AuthorizationContext;
import org.springframework.util.AntPathMatcher;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.stream.Collectors;

public class OaoAuthorizationManager implements ReactiveAuthorizationManager<AuthorizationContext> {
    private AntPathMatcher antPathMatcher = new AntPathMatcher();

    @Override
    public Mono<AuthorizationDecision> check(Mono<Authentication> authenticationMono, AuthorizationContext context) {
        ServerHttpRequest request = context.getExchange().getRequest();
        String currentUri = request.getURI().getPath();
        String currentMethod = request.getMethodValue();
//        Comparator<String> currentUriComparator = antPathMatcher.getPatternComparator(currentUri);
        return authenticationMono.flatMap(authentication -> {
            Mono<Boolean> m = Mono.empty();
            if (authentication.isAuthenticated()) {
                List<OaoGrantedAuthority> authorities = authentication.getAuthorities().stream()
                        .map(GrantedAuthority::getAuthority)
                        .map(authority -> OaoGrantedAuthority.parse(authority))
                        .collect(Collectors.toList());
                m = m.then(this.checkRole(currentUri, authorities)
                        .flatMap(ok -> {
                            if (ok) {
                                return Mono.just(true);
                            } else {
                                return checkAuthenticated(currentUri);
                            }
                        }));
            } else {
                return Mono.empty();
            }
            return m;
        }).switchIfEmpty(checkGuest(currentUri)).flatMap(ok -> Mono.just(new AuthorizationDecision(ok)));


        //匹配角色资源

        //匹配登录资源

        //匹配匿名资源
//        List<String> permitUris = new ArrayList<>();
//        List<String> authenticatedUris = new ArrayList<>();

    }

    public Mono<Boolean> checkRole(String currentUri, List<OaoGrantedAuthority> authorities) {
        //校验当前角色是否可以通过
        return Mono.just(false);
    }

    public Mono<Boolean> checkAuthenticated(String currentUri) {
        return Mono.just(true);
    }

    public Mono<Boolean> checkGuest(String currentUri) {
        return Mono.just(true);
    }
}
