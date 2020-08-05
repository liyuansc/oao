package com.oao.center;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@SpringBootApplication
@EnableEurekaServer
public class OaoCenterApplication {

    public static void main(String[] args) {
        SpringApplication.run(OaoCenterApplication.class, args);
    }

}
