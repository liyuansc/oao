package com.oao.web.config;

import com.alibaba.fastjson.support.config.FastJsonConfig;
import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter;
import com.oao.user.support.OaoLoginUserSupport;
import com.oao.web.support.LoginArgumentResolver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.http.HttpMessageConverters;
import org.springframework.context.annotation.Bean;
import org.springframework.http.MediaType;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.ArrayList;
import java.util.List;

public class OaoWebMvcAutoConfig implements WebMvcConfigurer {
    @Autowired
    private LoginArgumentResolver loginArgumentResolver;

    @Bean
    @ConditionalOnMissingBean
    public OaoLoginUserSupport loginUserSupport() {
        return new OaoLoginUserSupport();
    }

    @Bean
    @ConditionalOnMissingBean
    public LoginArgumentResolver loginArgumentResolver() {
        return new LoginArgumentResolver();
    }

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
        resolvers.add(loginArgumentResolver);
    }

    @Bean
    @ConditionalOnMissingBean
    public HttpMessageConverters fastJsonHttpMessageConverters() {
        FastJsonHttpMessageConverter converter = new FastJsonHttpMessageConverter();
        FastJsonConfig fastJsonConfig = new FastJsonConfig();
//        fastJsonConfig.setSerializerFeatures();
        List<MediaType> fastMediaTypes = new ArrayList<>();
        fastMediaTypes.add(MediaType.APPLICATION_JSON_UTF8);
        converter.setSupportedMediaTypes(fastMediaTypes);
        converter.setFastJsonConfig(fastJsonConfig);
        return new HttpMessageConverters(converter);
    }
}
