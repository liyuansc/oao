package com.liyu.oao.uaa.config;

import com.liyu.oao.uaa.constant.BeanName;
import okhttp3.OkHttpClient;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.OkHttp3ClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

@Configuration
public class LbClientConfig {
    @Bean(BeanName.LB_REST_TEMPLATE)
    public RestTemplate okRestTemplate(RestTemplateBuilder builder, OkHttpClient okHttpClient) {
        OkHttp3ClientHttpRequestFactory factory = new OkHttp3ClientHttpRequestFactory(okHttpClient);
        return builder.requestFactory(() -> factory).build();
    }
}
