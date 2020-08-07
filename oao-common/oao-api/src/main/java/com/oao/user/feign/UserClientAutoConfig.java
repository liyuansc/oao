package com.oao.user.feign;

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

@ConditionalOnMissingBean(UserClient.class)
@Import(FeignClientsConfiguration.class)
//@EnableFeignClients
//@EnableFeignClients(clients = {UserClient.class})
public class UserClientAutoConfig {

    @Bean
    public UserClient userClient(Decoder decoder, Encoder encoder, Client client, Contract contract) {
        return HystrixFeign.builder().client(client)
                .encoder(encoder)
                .decoder(decoder)
                .contract(contract)
//                .requestInterceptor(new BasicAuthRequestInterceptor("user", "user"))
                .target(UserClient.class, "http://" + App.ID.USER, new UserClientFallbackFactory());
    }

//    //
//    @Bean
//    public UserClientFallbackFactory userClientFallbackFactory() {
//        return new UserClientFallbackFactory();
//    }
}
