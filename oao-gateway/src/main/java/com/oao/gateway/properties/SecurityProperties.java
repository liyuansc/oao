package com.oao.gateway.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * @author zlt
 * @date 2019/1/4
 */
@ConfigurationProperties(prefix = "com.oao.gateway.security")
@Configuration
@Data
public class SecurityProperties {
}
