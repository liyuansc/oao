package com.oao.user.cache.unit.impl;

import com.oao.common.model.App;
import com.oao.redis.cache.unit.ICacheUnit;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.util.Random;

@Component
public class FindRoleByUserIdUnit implements ICacheUnit {
    public final static String NAME = App.ID.USER + ":role:userId";

    @Override
    public String name() {
        return NAME;
    }

    @Override
    public Duration expires() {
        return Duration.ofMinutes(5)
                .plus(Duration.ofSeconds((new Random().nextInt(120) - 60)));
    }

    @Override
    public boolean dynamicExpires() {
        return true;
    }
}
