package com.oao.gateway.manage;

import com.github.benmanes.caffeine.cache.AsyncLoadingCache;
import com.github.benmanes.caffeine.cache.CacheLoader;
import com.github.benmanes.caffeine.cache.Caffeine;
import com.oao.user.feign.UserClient;
import com.oao.user.model.po.OaoApi;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.util.List;

@Component
public class OaoApiManage {
    @Autowired
    private UserClient userClient;
    private final static String cacheKey = "all";

    private AsyncLoadingCache<String, List<OaoApi>> cache = Caffeine
            .newBuilder()
            //2分钟后过期
            .expireAfterWrite(Duration.ofMinutes(2))
            //60秒后自动刷新
            .refreshAfterWrite(Duration.ofSeconds(60))
            .buildAsync(new CacheLoader<String, List<OaoApi>>() {
                @Nullable
                @Override
                public List<OaoApi> load(@NonNull String key) {
                    return userClient.findAllApi().check().getData();
                }
            });

    public Mono<List<OaoApi>> getAll() {
        return Mono.fromFuture(cache.get(cacheKey));
    }
}