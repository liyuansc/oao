package com.liyu.oao.user.feign;

import com.liyu.oao.common.model.App;
import feign.Client;
import feign.Contract;
import feign.codec.Decoder;
import feign.codec.Encoder;
import feign.hystrix.HystrixFeign;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.cloud.openfeign.FeignClientsConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;

@ConditionalOnMissingBean(IUserClient.class)

@Import(FeignClientsConfiguration.class)
//@EnableFeignClients

//@EnableFeignClients(clients = {UserClient.class})
public class UserClientAutoConfig {

    @Bean
    public IUserClient userClient(Decoder decoder, Encoder encoder, Client client, Contract contract) {
        return HystrixFeign.builder().client(client)
                .encoder(encoder)
                .decoder(decoder)
                .contract(contract)

//                .requestInterceptor(new BasicAuthRequestInterceptor("user", "user"))
                .target(IUserClient.class, "http://" + App.ID.USER, new UserClientFallbackFactory());
    }

//    //
//    @Bean
//    public UserClientFallbackFactory userClientFallbackFactory() {
//        return new UserClientFallbackFactory();
//    }
}
