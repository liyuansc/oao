package com.liyu.oao.user.controller;

import com.liyu.oao.common.constant.Route;
import com.liyu.oao.common.model.Result;
import com.liyu.oao.common.util.DateUtils;
import com.liyu.oao.security.JwtTokenManage;
import com.liyu.oao.security.OaoUserDetails;
import com.liyu.oao.user.model.vo.LoginReq;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Date;

@RestController
@RequestMapping(Route.USER + "/auth")
public class LoginController {
    private static final Logger logger = LoggerFactory.getLogger(LoginController.class);
    @Autowired
    private JwtTokenManage jwtTokenManage;


    @Autowired
    private ReactiveAuthenticationManager authenticationManager;

    @PostMapping(value = "/login")
    public Mono<Result> login(@RequestBody @Validated LoginReq loginReq) {
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(loginReq.getUsername(), loginReq.getPassword());
        return authenticationManager
                .authenticate(authenticationToken)
                .flatMap(authentication -> {
                    OaoUserDetails principal = (OaoUserDetails) authentication.getPrincipal();
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                    Duration duration = Duration.of(12, ChronoUnit.DAYS);
                    Date expiration = DateUtils.toDate(LocalDateTime.now().plus(duration));
                    String tokenValue = jwtTokenManage.createToken(principal.getUsername(), expiration);
                    DefaultOAuth2AccessToken accessToken = new DefaultOAuth2AccessToken(tokenValue);
                    accessToken.setExpiration(expiration);
//                    accessToken.setTokenType();
                    return Mono.just(Result.success(accessToken));
                });
    }
}
