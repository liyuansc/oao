package com.oao.user;

import com.oao.common.constant.CurrentApp;
import com.oao.common.model.App;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class OaoUserApplication {

    static {
        CurrentApp.setAppId(App.ID.UAA);
    }

    public static void main(String[] args) {
        SpringApplication.run(OaoUserApplication.class, args);
    }

}
