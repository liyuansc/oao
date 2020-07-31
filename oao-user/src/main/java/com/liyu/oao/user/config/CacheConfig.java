package com.liyu.oao.user.config;

import com.liyu.oao.redis.cache.config.DefaultCacheConfig;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import(DefaultCacheConfig.class)
public class CacheConfig {
}
