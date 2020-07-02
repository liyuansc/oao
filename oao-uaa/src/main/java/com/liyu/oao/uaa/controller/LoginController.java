package com.liyu.oao.uaa.controller;

import com.liyu.oao.common.constant.Route;
import com.liyu.oao.common.model.Result;
import com.liyu.oao.common.util.DateUtils;
import com.liyu.oao.security.JwtTokenManage;
import com.liyu.oao.security.OaoUserDetails;
import com.liyu.oao.uaa.model.vo.LoginReq;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.*;
import org.springframework.security.oauth2.provider.token.AuthorizationServerTokenServices;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.HashMap;

@RestController
@RequestMapping(Route.UAA + "/auth")
public class LoginController {
    private static final Logger logger = LoggerFactory.getLogger(LoginController.class);
    @Autowired
    private JwtTokenManage jwtTokenManage;
    @Autowired
    private ClientDetailsService clientDetailsService;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private AuthorizationServerTokenServices authorizationServerTokenServices;

    @RequestMapping(value = "/login")
    public Result login(@RequestBody @Validated LoginReq loginReq) {
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(loginReq.getUsername(), loginReq.getPassword());
        Authentication authentication = authenticationManager.authenticate(authenticationToken);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        OaoUserDetails principal = (OaoUserDetails) authentication.getPrincipal();
        Duration duration = Duration.of(12, ChronoUnit.DAYS);
        Date expiration = DateUtils.toDate(LocalDateTime.now().plus(duration));
        ClientDetails clientDetails = clientDetailsService.loadClientByClientId("web");
        TokenRequest tokenRequest = new TokenRequest(new HashMap<>(), clientDetails.getClientId(), clientDetails.getScope(), "customer");
        OAuth2Request oAuth2Request = tokenRequest.createOAuth2Request(clientDetails);
        OAuth2Authentication oAuth2Authentication = new OAuth2Authentication(oAuth2Request, authentication);
        OAuth2AccessToken oAuth2AccessToken = authorizationServerTokenServices.createAccessToken(oAuth2Authentication);

//        String tokenValue = jwtTokenManage.createToken(principal.getUsername(), expiration);
//        DefaultOAuth2AccessToken accessToken = new DefaultOAuth2AccessToken(tokenValue);
//        accessToken.setExpiration(expiration);
//                    accessToken.setTokenType();
        return Result.success(oAuth2AccessToken);
    }
}