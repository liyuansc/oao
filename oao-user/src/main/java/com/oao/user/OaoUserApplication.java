package com.oao.user;

import com.oao.common.constant.App;
import com.oao.common.constant.CurrentApp;
import com.oao.common.support.mlog.EnableMLog;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
@EnableMLog
public class OaoUserApplication {

    static {
        CurrentApp.setAppId(App.ID.USER);
    }

    public static void main(String[] args) {
        SpringApplication.run(OaoUserApplication.class, args);
    }

}
