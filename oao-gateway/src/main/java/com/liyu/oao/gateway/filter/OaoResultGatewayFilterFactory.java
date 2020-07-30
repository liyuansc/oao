package com.liyu.oao.gateway.filter;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.liyu.oao.common.model.Result;
import org.reactivestreams.Publisher;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.core.io.buffer.DataBufferUtils;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.http.server.reactive.ServerHttpResponseDecorator;
import org.springframework.stereotype.Component;
import org.springframework.util.StreamUtils;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.io.IOException;

//@Component
public class OaoResultGatewayFilterFactory implements GlobalFilter, Ordered {


//    public FormalGatewayFilterFactory() {
//        super(FormalGatewayFilterFactory.Config.class);
//    }

    @Override
    public int getOrder() {
        return -2;
    }

//
//    @Override
//    public GatewayFilter apply(FormalGatewayFilterFactory.Config config) {
//
//        return new MyGatewayFilter();
//    }


    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        return new MyGatewayFilter().filter(exchange, chain);
    }

    public class MyGatewayFilter implements GatewayFilter {

        @Override
        public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
            //预扣减id等于空的话,直接返回空对象
            //判断返回体是否是json格式
            ServerHttpResponseDecorator responseDecorator = new ServerHttpResponseDecorator(exchange.getResponse()) {
                @Override
                public Mono<Void> writeWith(Publisher<? extends DataBuffer> body) {
                    ServerHttpResponse response = this.getDelegate();
                    return DataBufferUtils
                            .join(Flux.from(body))
                            .map(dataBuffer -> dataBuffer.asInputStream())
                            .flatMap(inputStream -> {
                                byte[] bytes;
                                try {
                                    bytes = StreamUtils.copyToByteArray(inputStream);
                                } catch (IOException e) {
                                    return Mono.error(new RuntimeException(e.getMessage(), e));
                                }
                                String json = new String(bytes);
                                Result result = JSON.parseObject(json, new TypeReference<Result>() {
                                });
                                System.out.println(json);
                                return super.writeWith(Flux.just(response.bufferFactory().wrap(bytes)));
//                                            return super.writeWith(body);
                            });
                }

                @Override
                public Mono<Void> writeAndFlushWith(Publisher<? extends Publisher<? extends DataBuffer>> body) {
                    return this.writeWith(Flux.from(body).flatMapSequential(p -> p));
                }
            };
            return chain.filter(exchange.mutate().response(responseDecorator).build());


            //body.flatMap(dataBuffer -> rebuildFluxBody(exchange, dataBuffer));

//            Mono.defer(() -> {

//           });
//           return chain.filter(exchange);


//            return null;
        }
    }
}
