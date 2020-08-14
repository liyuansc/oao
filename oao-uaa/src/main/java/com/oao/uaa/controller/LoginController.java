package com.oao.uaa.controller;

import com.oao.common.constant.ResultCode;
import com.oao.common.constant.Route;
import com.oao.common.exception.ResultException;
import com.oao.common.model.Result;
import com.oao.uaa.model.LoginReq;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.*;
import org.springframework.security.oauth2.provider.client.BaseClientDetails;
import org.springframework.security.oauth2.provider.token.AuthorizationServerTokenServices;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping(Route.A_UAA + "/auth")
@Slf4j
public class LoginController {
    @Autowired
    private ClientDetailsService clientDetailsService;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private AuthorizationServerTokenServices tokenService;

    @PostMapping(value = "/login")
    public Result<OAuth2AccessToken> login(@RequestBody @Validated LoginReq loginReq) {
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(loginReq.getUsername(), loginReq.getPassword());
        Authentication authentication;
        try {
            authentication = authenticationManager.authenticate(authenticationToken);
        } catch (AuthenticationException authenticationException) {
            if (authenticationException instanceof BadCredentialsException
                    || authenticationException instanceof UsernameNotFoundException)
                throw new ResultException(ResultCode.R2011.build());
                //TODO 补充异常处理
            else throw new ResultException(ResultCode.R2010.build());
        }
        SecurityContextHolder.getContext().setAuthentication(authentication);
        ClientDetails clientDetails = clientDetailsService.loadClientByClientId("web");
        if (clientDetails instanceof BaseClientDetails) {
            //失效时间
            BaseClientDetails baseClientDetails = ((BaseClientDetails) clientDetails);
            if (loginReq.isRemember()) {
                baseClientDetails.setAccessTokenValiditySeconds((int) TimeUnit.DAYS.toSeconds(14));
            } else {
                baseClientDetails.setAccessTokenValiditySeconds((int) TimeUnit.HOURS.toSeconds(12));
            }
        }
        TokenRequest tokenRequest = new TokenRequest(new HashMap<>(), clientDetails.getClientId(), clientDetails.getScope(), "customer");
        OAuth2Request oAuth2Request = tokenRequest.createOAuth2Request(clientDetails);
        OAuth2Authentication oAuth2Authentication = new OAuth2Authentication(oAuth2Request, authentication);
        OAuth2AccessToken oAuth2AccessToken = tokenService.createAccessToken(oAuth2Authentication);
        return Result.success(oAuth2AccessToken);
    }
}
