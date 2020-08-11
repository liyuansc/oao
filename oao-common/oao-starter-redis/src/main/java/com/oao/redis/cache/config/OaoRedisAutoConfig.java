package com.oao.redis.cache.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Import(OaoCacheAutoConfig.class)
public class OaoRedisAutoConfig {
}
