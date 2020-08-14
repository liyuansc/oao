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
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class    OaoApiManage {
    @Autowired
    private UserClient userClient;

    private AsyncLoadingCache<Integer, MultiValueMap<Integer, OaoApi>> cache = Caffeine
            .newBuilder()
            //2分钟后过期
            .expireAfterWrite(Duration.ofMinutes(2))
            //30秒后自动刷新
            .refreshAfterWrite(Duration.ofSeconds(30))
            //只支持key=0,单例缓存
            .maximumSize(1)
            .buildAsync(new CacheLoader<Integer, MultiValueMap<Integer, OaoApi>>() {
                @Nullable
                @Override
                public MultiValueMap<Integer, OaoApi> load(@NonNull Integer key) {
                    List<OaoApi> all = new ArrayList<>();
                    Map<Integer, List<OaoApi>> m = all.stream().collect(Collectors.groupingBy(OaoApi::getType));
                    LinkedMultiValueMap<Integer, OaoApi> map = new LinkedMultiValueMap<>(m);
                    return new LinkedMultiValueMap<>(userClient.findAllApi().check().getData().stream().collect(Collectors.groupingBy(OaoApi::getType)));
                }
            });

    //type-OaoApi
    public Mono<MultiValueMap<Integer, OaoApi>> getApiMap() {
        return Mono.fromFuture(cache.get(0));
    }
}
