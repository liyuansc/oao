package com.oao.user.cache.unit.impl;

import com.oao.common.constant.App;
import com.oao.redis.cache.unit.ICacheUnit;
import org.springframework.stereotype.Component;

import java.time.Duration;

@Component
public class FindApiUnit implements ICacheUnit {
    public final static String NAME = App.ID.USER + ":api";

    @Override
    public String name() {
        return NAME;
    }

    @Override
    public Duration expires() {
        return Duration.ofMinutes(3);
    }

    @Override
    public boolean dynamicExpires() {
        return false;
    }
}
