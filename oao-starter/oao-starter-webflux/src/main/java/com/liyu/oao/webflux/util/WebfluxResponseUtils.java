package com.liyu.oao.webflux.util;

import com.alibaba.fastjson.JSON;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.nio.charset.StandardCharsets;

public class WebfluxResponseUtils {
    public static Mono<Void> writeJSON(ServerWebExchange exchange, Object body) {
        ServerHttpResponse response = exchange.getResponse();
        response.setStatusCode(HttpStatus.OK);
        response.getHeaders().setContentType(MediaType.APPLICATION_JSON);
        byte[] bytes = JSON.toJSONString(body).getBytes(StandardCharsets.UTF_8);
        DataBuffer buffer = response.bufferFactory().wrap(bytes);
        return response.writeWith(Mono.just(buffer));
    }
}
