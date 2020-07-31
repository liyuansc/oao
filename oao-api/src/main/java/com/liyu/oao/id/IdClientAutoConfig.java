package com.liyu.oao.id;

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConditionalOnMissingBean(IdClient.class)
@EnableFeignClients(clients = IdClient.class)
public class IdClientAutoConfig {

    @Bean
    public IdClientFallbackFactory idClientFallbackFactory() {
        return new IdClientFallbackFactory();
    }
}
