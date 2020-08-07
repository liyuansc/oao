package com.oao.id;

import com.oao.common.constant.CurrentApp;
import com.oao.common.constant.App;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class OaoIdApplication {

    static {
        CurrentApp.setAppId(App.ID.ID);
    }

    public static void main(String[] args) {
        SpringApplication.run(OaoIdApplication.class, args);
    }

}
