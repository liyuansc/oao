package com.oao.gateway.security;

import com.oao.common.constant.ApiConstant;
import com.oao.common.model.OaoGrantedAuthority;
import com.oao.gateway.manage.OaoApiManage;
import com.oao.user.model.po.OaoApi;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.security.authorization.AuthorizationDecision;
import org.springframework.security.authorization.ReactiveAuthorizationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.server.authorization.AuthorizationContext;
import org.springframework.util.AntPathMatcher;
import reactor.core.publisher.Mono;

import java.util.Comparator;
import java.util.List;

public class OaoAuthorizationManager implements ReactiveAuthorizationManager<AuthorizationContext> {
    @Autowired
    private OaoApiManage oaoApiManage;

    private AntPathMatcher antPathMatcher = new AntPathMatcher();

    @Override
    public Mono<AuthorizationDecision> check(Mono<Authentication> authenticationMono, AuthorizationContext context) {
        ServerHttpRequest request = context.getExchange().getRequest();
        return authenticationMono
                .flatMap(authentication -> isGranted(request, authentication))
                .switchIfEmpty(Mono.defer(() -> isGranted(request, null)))
                .map(granted -> new AuthorizationDecision(granted));
    }

    /**
     * @param request
     * @param authentication
     * @return
     */
    public Mono<Boolean> isGranted(ServerHttpRequest request, Authentication authentication) {
        String uri = request.getURI().getPath();
        HttpMethod method = request.getMethod();
        Comparator<String> comparator = antPathMatcher.getPatternComparator(uri);
        return oaoApiManage.getApiMap().map(apiMap -> {
            boolean isAuthentication = false;
            if (authentication != null) isAuthentication = authentication.isAuthenticated();
            //角色资源
            OaoApi roleApi = findApi(apiMap.get(ApiConstant.ROLE), uri, method, comparator);
            if (roleApi != null) {
                if (!isAuthentication) return false;
                else return authentication
                        .getAuthorities()
                        .stream()
                        .map(authority -> OaoGrantedAuthority.parse(authority.getAuthority()))
                        .anyMatch(authority -> roleApi.getRoleIds().contains(authority.getId()));
            }
            //认证资源
            OaoApi authenticatedApi = findApi(apiMap.get(ApiConstant.AUTHENTICATED), uri, method, comparator);
            if (authenticatedApi != null) return isAuthentication;
            //匿名资源
            OaoApi anonymousApi = findApi(apiMap.get(ApiConstant.ANONYMOUS), uri, method, comparator);
            if (anonymousApi != null) return true;
            return false;
        });
    }

    private OaoApi findApi(List<OaoApi> apis, String currentUri, HttpMethod currentMethod, Comparator<String> comparator) {
        if (apis == null) return null;
        return apis.stream()
                //方法匹配
                .filter(api -> {
                    String apiMethodValue = api.getMethod();
                    return StringUtils.isBlank(apiMethodValue) || currentMethod.matches(apiMethodValue);
                })
                //uri匹配
                .filter(api -> antPathMatcher.match(api.getUri(), currentUri))
                //排序,选取匹配度最高
                .sorted((a1, a2) -> comparator.compare(a1.getUri(), a2.getUri()))
                .findFirst().orElse(null);
    }
}
