package com.liyu.oao.user;

import com.alibaba.fastjson.parser.ParserConfig;
import com.liyu.oao.common.constant.CurrentApp;
import com.liyu.oao.common.model.App;
import com.liyu.oao.redis.cache.config.CacheAutoConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Import;

@SpringBootApplication
@EnableDiscoveryClient
@Import(CacheAutoConfig.class)
public class OaoUserApplication {

    static {
        CurrentApp.setAppId(App.ID.UAA);
    }

    public static void main(String[] args) {
        SpringApplication.run(OaoUserApplication.class, args);
    }

}
