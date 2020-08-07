package com.oao.id.feign;

import com.oao.common.constant.App;
import feign.Client;
import feign.Contract;
import feign.codec.Decoder;
import feign.codec.Encoder;
import feign.hystrix.HystrixFeign;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.cloud.openfeign.FeignClientsConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;

@ConditionalOnMissingBean(IdClient.class)
@Import(FeignClientsConfiguration.class)
//@EnableFeignClients(clients = IdClient.class)
public class IdClientAutoConfig {

    @Bean
    public IdClient userClient(Decoder decoder, Encoder encoder, Client client, Contract contract) {
        return HystrixFeign.builder().client(client)
                .encoder(encoder)
                .decoder(decoder)
                .contract(contract)
//                .requestInterceptor(new BasicAuthRequestInterceptor("user", "user"))
                .target(IdClient.class, "http://" + App.ID.ID, new IdClientFallbackFactory());
    }

//    @Bean
//    public IdClientFallbackFactory idClientFallbackFactory() {
//        return new IdClientFallbackFactory();
//    }
}
