package com.liyu.oao.redis.cache.unit;

import java.time.Duration;
import java.time.temporal.ChronoUnit;

/**
 * Created by liyu on 2018/11/28.
 */
public interface ICacheUnit {
    //缓存名称
    String name();

    //缓存的过期时间
    default Duration expires() {
        return Duration.of(10, ChronoUnit.MINUTES);
    }

    //是否动态设置过期时间,如果为是,则每次设置缓存的时候都会重新调用expires()获取过期时间
    default boolean dynamicExpires() {
        return false;
    }

    //是否事务
    default boolean transactional() {
        return false;
    }
}
