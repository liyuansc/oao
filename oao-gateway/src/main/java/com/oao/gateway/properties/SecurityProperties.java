package com.oao.gateway.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.List;

/**
 * @author zlt
 * @date 2019/1/4
 */
@ConfigurationProperties(prefix = "com.oao.gateway.security")
@Configuration
public class SecurityProperties {
    private List<String> permitUris;

    public List<String> getPermitUris() {
        return permitUris;
    }

    public void setPermitUris(List<String> permitUris) {
        this.permitUris = permitUris;
    }
}
