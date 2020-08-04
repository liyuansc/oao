package com.liyu.oao.gateway.security;

import com.liyu.oao.common.constant.OaoSecurityConstant;
import com.liyu.oao.security.OaoUserDetails;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.OAuth2Request;
import org.springframework.security.web.server.WebFilterExchange;
import org.springframework.security.web.server.authentication.ServerAuthenticationSuccessHandler;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.stream.Collectors;

/**
 * 认证成功处理类
 *
 * @author zlt
 * @date 2019/10/7
 * <p>
 * Blog: https://zlt2000.gitee.io
 * Github: https://github.com/zlt2000
 */
public class OaoAuthenticationSuccessHandler implements ServerAuthenticationSuccessHandler {
    @Override
    public Mono<Void> onAuthenticationSuccess(WebFilterExchange webFilterExchange, Authentication authentication) {
        Object principal = authentication.getPrincipal();
        ServerHttpRequest request = webFilterExchange.getExchange().getRequest();
        ServerHttpRequest.Builder rb = request.mutate();
        if (principal instanceof String) {
            rb.header(OaoSecurityConstant.HttpHeader.I_USERNAME, (String) principal);
        } else if (principal instanceof UserDetails) {
            UserDetails userDetails = (UserDetails) principal;
            rb.header(OaoSecurityConstant.HttpHeader.I_USERNAME, userDetails.getUsername());
            rb.header(OaoSecurityConstant.HttpHeader.I_AUTHORITIES, userDetails.getAuthorities().stream().map(aa -> aa.getAuthority()).collect(Collectors.joining(",")));
            if (userDetails instanceof OaoUserDetails) {
                OaoUserDetails oaoUserDetails = (OaoUserDetails) userDetails;
                rb.header(OaoSecurityConstant.HttpHeader.I_USER_ID, oaoUserDetails.getId());
            }
        }
        if (authentication instanceof OAuth2Authentication) {
            OAuth2Request oAuth2Request = ((OAuth2Authentication) authentication).getOAuth2Request();
            String clientId = oAuth2Request.getClientId();
            rb.header(OaoSecurityConstant.HttpHeader.I_CLIENT_ID, clientId);
        }
        String as = authentication.getAuthorities().stream().map(a -> a.getAuthority()).collect(Collectors.joining(","));
        rb.header(OaoSecurityConstant.HttpHeader.I_AUTHORITIES, as);
        ServerWebExchange exchange = webFilterExchange.getExchange();
        return webFilterExchange.getChain().filter(exchange.mutate().request(rb.build()).build());
    }
}
