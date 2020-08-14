package com.oao.user.cache.unit.impl;

import com.oao.common.constant.App;
import com.oao.redis.cache.unit.ICacheUnit;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.util.Random;

@Component
public class FindUserByIdUnit implements ICacheUnit {
    public final static String NAME = App.ID.USER + ":user:id";

    @Override
    public String name() {
        return NAME;
    }

    @Override
    public Duration expires() {
        return Duration.ofMinutes(10)
                .plus(Duration.ofSeconds((new Random().nextInt(120) - 60)));
    }

    @Override
    public boolean dynamicExpires() {
        return true;
    }
}
