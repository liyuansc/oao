package com.liyu.oao.user.config;

import com.liyu.oao.user.constant.BeanName;
import io.netty.channel.ChannelOption;
import io.netty.handler.ssl.SslContextBuilder;
import io.netty.handler.ssl.util.InsecureTrustManagerFactory;
import io.netty.handler.timeout.ReadTimeoutHandler;
import io.netty.handler.timeout.WriteTimeoutHandler;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.netty.http.client.HttpClient;
import reactor.netty.resources.ConnectionProvider;
import reactor.netty.resources.LoopResources;

import java.util.concurrent.TimeUnit;

@Configuration
public class LbClientConfig {

    @Bean(BeanName.LB_CLIENT_BUILDER_NAME)
    @LoadBalanced
    public WebClient.Builder lbClientBuilder() {
        String name = "httpClient";
        ConnectionProvider connectionProvider = ConnectionProvider.create(name, 50);
        LoopResources loopResources = LoopResources.create(name, 4, true);
        HttpClient httpClient = HttpClient.create(connectionProvider)
                .secure(t -> t.sslContext(SslContextBuilder.forClient().trustManager(InsecureTrustManagerFactory.INSTANCE)))
                .tcpConfiguration(tcpClient -> tcpClient
                        .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, (int) TimeUnit.SECONDS.toMillis(10))
                        .option(ChannelOption.TCP_NODELAY, true)
                        .doOnConnected(connection -> {
                            connection.addHandler(new ReadTimeoutHandler(20, TimeUnit.SECONDS));
                            connection.addHandler(new WriteTimeoutHandler(20, TimeUnit.SECONDS));
                        })
                        .runOn(loopResources)
                );
        return WebClient.builder().clientConnector(new ReactorClientHttpConnector(httpClient));
    }
}
