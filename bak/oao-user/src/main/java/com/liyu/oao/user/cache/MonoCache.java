package com.liyu.oao.user.cache;

import reactor.core.publisher.Mono;

public interface MonoCache<T> {
    Mono<T> able(String key, Mono<T> m);

    Mono<T> put(String key, Mono<T> m);

    Mono<T> evict(String key);
}
