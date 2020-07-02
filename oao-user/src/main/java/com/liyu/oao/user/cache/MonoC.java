package com.liyu.oao.user.cache;

import reactor.core.publisher.Mono;

public class MonoC implements MonoCache {
    @Override
    public Mono able(String key, Mono m) {
        return null;
    }

    @Override
    public Mono put(String key, Mono m) {
        return null;
    }

    @Override
    public Mono evict(String key) {
        return null;
    }
}
