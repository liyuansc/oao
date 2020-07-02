package com.liyu.oao.uaa;

import com.liyu.oao.common.constant.CurrentApp;
import com.liyu.oao.common.model.App;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class OaoUaaApplication {

    {
        CurrentApp.setAppId(App.Id.UAA);
    }

    public static void main(String[] args) {
        SpringApplication.run(OaoUaaApplication.class, args);
    }

}