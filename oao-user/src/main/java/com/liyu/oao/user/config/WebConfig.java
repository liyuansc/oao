package com.liyu.oao.user.config;

import com.liyu.oao.user.controller.client.UserClientImpl;
import com.liyu.oao.web.config.DefaultWebMvcConfig;
import com.liyu.oao.web.support.LoginArgumentResolver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;

import java.util.List;

@Configuration
public class WebConfig extends DefaultWebMvcConfig {
    @Autowired
    private UserClientImpl userClient;

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
        super.addArgumentResolvers(resolvers);
        //因为是本地服务，所以userClient使用本地的实现类
        resolvers.stream().filter(r -> r instanceof LoginArgumentResolver).map(r -> (LoginArgumentResolver) r).forEach(loginArgumentResolver -> {
            loginArgumentResolver.setUserClient(userClient);
        });

    }
}
