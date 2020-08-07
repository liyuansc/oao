package com.oao.gateway.security;

import com.oao.common.constant.ApiConstant;
import com.oao.common.model.OaoGrantedAuthority;
import com.oao.gateway.manage.OaoApiManage;
import com.oao.gateway.properties.SecurityProperties;
import com.oao.user.model.po.OaoApi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.security.authorization.AuthorizationDecision;
import org.springframework.security.authorization.ReactiveAuthorizationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.server.authorization.AuthorizationContext;
import org.springframework.util.AntPathMatcher;
import reactor.core.publisher.Mono;

import java.util.Comparator;

public class OaoAuthorizationManager implements ReactiveAuthorizationManager<AuthorizationContext> {
    @Autowired
    private SecurityProperties securityProperties;
    @Autowired
    private OaoApiManage oaoApiManage;

    private AntPathMatcher antPathMatcher = new AntPathMatcher();

    @Override
    public Mono<AuthorizationDecision> check(Mono<Authentication> authenticationMono, AuthorizationContext context) {
        ServerHttpRequest request = context.getExchange().getRequest();
        String currentUri = request.getURI().getPath();
        String currentMethod = request.getMethodValue();

        Comparator<String> uriComparable = antPathMatcher.getPatternComparator(currentUri);
        return oaoApiManage.getAll().flatMap(allApi -> {
            OaoApi matchApi = allApi.stream()
                    .filter(api -> antPathMatcher.match(api.getUri(), currentUri))
                    .sorted((a1, a2) -> uriComparable.compare(a1.getUri(), a2.getUri()))
                    .findFirst().orElse(null);
            //不识别api,拒绝
            if (matchApi == null) {
                return Mono.just(false);
            }
            Integer apiType = matchApi.getType();
            //其余情况需要登录
            return authenticationMono.map(authentication -> {
                //匿名资源
                if (apiType == ApiConstant.ANONYMOUS) return true;
                else if (!authentication.isAuthenticated()) return false;
                    //登录即可的api
                else if (apiType == ApiConstant.AUTHENTICATED) return true;
                    //角色api
                else if (apiType == ApiConstant.ROLE) return authentication
                        .getAuthorities()
                        .stream()
                        .map(authority -> OaoGrantedAuthority.parse(authority.getAuthority()))
                        .anyMatch(authority -> matchApi.getRoleIds().contains(authority.getId()));
                    //不识别的api类型
                else return false;
                //无认证且匿名资源
            }).defaultIfEmpty(apiType == ApiConstant.ANONYMOUS);
        }).map(granted -> new AuthorizationDecision(granted));
    }

}
