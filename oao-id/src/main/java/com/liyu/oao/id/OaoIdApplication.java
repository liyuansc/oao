package com.liyu.oao.id;

import com.liyu.oao.common.constant.CurrentApp;
import com.liyu.oao.common.model.App;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class OaoIdApplication {

    static {
        CurrentApp.setAppId(App.Id.ID);
    }

    public static void main(String[] args) {
        SpringApplication.run(OaoIdApplication.class, args);
    }

}
