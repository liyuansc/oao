package com.oao.gateway;

import com.oao.common.constant.CurrentApp;
import com.oao.common.constant.App;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class OaoGatewayApplication {

    static {
        CurrentApp.setAppId(App.ID.GATEWAY);
    }

    public static void main(String[] args) {
        SpringApplication.run(OaoGatewayApplication.class, args);
    }

}
