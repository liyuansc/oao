package com.liyu.oao.user.cache.unit.impl;

import com.liyu.oao.redis.cache.unit.ICacheUnit;

import java.time.Duration;

public class FindUserByUserNameUnit implements ICacheUnit {
    public final static String NAME = "user:username";

    @Override
    public String name() {
        return NAME;
    }

    @Override
    public Duration expires() {
        return Duration.ofMinutes(10);
    }
}
