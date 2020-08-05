package com.oao.user.service;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.time.Duration;

@Service
public class TestService {
    private Cache<String, String> testCache;

    {
        testCache = Caffeine.newBuilder().expireAfterWrite(Duration.ofSeconds(4)).build();
    }

    public Mono<String> test(String key) {
        return Mono.fromSupplier(() -> testCache.getIfPresent(key)).switchIfEmpty(
                Mono.just(String.valueOf(System.currentTimeMillis())).doOnNext(v -> testCache.put(key, v))
        );
    }

    public Mono<String> cache(String key, Mono<String> exe) {
        return Mono.fromSupplier(() -> testCache.getIfPresent(key))
                .switchIfEmpty(exe.doOnNext(o -> {
                    testCache.put(key, o);
                }));
    }

}
