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
}
