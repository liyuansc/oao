package com.liyu.oao.user.client;


import com.liyu.oao.common.constant.Route;
import com.liyu.oao.user.constant.BeanName;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cloud.client.circuitbreaker.ReactiveCircuitBreakerFactory;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Component
public class UserClient {
    private Log log = LogFactory.getLog(UserClient.class);

    private WebClient webClient;

    @Autowired
    private ReactiveCircuitBreakerFactory circuitBreakerFactory;


    @Autowired
    public UserClient(@Qualifier(BeanName.LB_CLIENT_BUILDER_NAME) WebClient.Builder builder) {
        this.webClient = builder.baseUrl("http://oao-user").build();
    }


    public Mono<String> test() {
        return webClient.get().uri(Route.USER + "/test1").retrieve()
                .bodyToMono(new ParameterizedTypeReference<String>() {
                })
                //熔断
                .transform(it -> circuitBreakerFactory.create("slow_mono").run(it, t -> {
                    t.printStackTrace();
                    return Mono.just("fallback");
                }));
    }
}
