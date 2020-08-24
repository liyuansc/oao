package com.oao.gateway.security;

import com.alibaba.fastjson.JSON;
import com.oao.common.constant.OaoSecurityConstant;
import com.oao.common.model.OaoGrantedAuthority;
import com.oao.common.util.UrlCoder;
import com.oao.security.OaoUserDetails;
import com.oao.user.model.OaoLoginUser;
import com.oao.user.model.po.OaoRole;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.OAuth2Request;
import org.springframework.security.web.server.WebFilterExchange;
import org.springframework.security.web.server.authentication.ServerAuthenticationSuccessHandler;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.List;
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
        OaoLoginUser user = new OaoLoginUser();

        if (principal instanceof String) {
            user.setUsername((String) principal);
        } else if (principal instanceof UserDetails) {
            UserDetails userDetails = (UserDetails) principal;
            user.setUsername(userDetails.getUsername());
            if (userDetails instanceof OaoUserDetails) {
                user.setId(((OaoUserDetails) userDetails).getId());
            }
        }
        List<OaoRole> roles = authentication.getAuthorities()
                .stream()
                .map(a -> a.getAuthority())
                .map(authority -> OaoGrantedAuthority.parse(authority))
                .map(authority -> {
                    OaoRole role = new OaoRole();
                    role.setId(authority.getId());
                    role.setCode(authority.getCode());
                    return role;
                })
                .collect(Collectors.toList());
        user.setRoles(roles);
        rb.header(OaoSecurityConstant.HttpHeader.I_USER, UrlCoder.encode(JSON.toJSONString(user)));
        if (authentication instanceof OAuth2Authentication) {
            OAuth2Request oAuth2Request = ((OAuth2Authentication) authentication).getOAuth2Request();
            String clientId = oAuth2Request.getClientId();
            user.setClientId(clientId);
        }
        ServerWebExchange exchange = webFilterExchange.getExchange();
        return webFilterExchange.getChain().filter(exchange.mutate().request(rb.build()).build());
    }
}
