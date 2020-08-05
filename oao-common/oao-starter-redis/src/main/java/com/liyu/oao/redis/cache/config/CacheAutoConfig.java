package com.liyu.oao.redis.cache.config;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.parser.ParserConfig;
import com.liyu.oao.common.constant.CodeConstant;
import com.liyu.oao.redis.cache.OaoCacheManagerBuilder;
import com.liyu.oao.redis.cache.unit.ICacheUnit;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CachingConfigurer;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.interceptor.CacheErrorHandler;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.connection.RedisConnectionFactory;

import java.time.Duration;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Created by liyu on 2018/8/9.
 */
@EnableCaching
public class CacheAutoConfig {
    @Autowired(required = false)
    private List<ICacheUnit> cacheUnits = Collections.emptyList();

    private Logger logger = LoggerFactory.getLogger(CacheAutoConfig.class);

    {
        ParserConfig.getGlobalInstance().addAccept(CodeConstant.BASE_PACKAGE);
    }

    @Bean
    @ConditionalOnMissingBean
    public CacheManager cacheManager(RedisConnectionFactory connectionFactory) {
        OaoCacheManagerBuilder builder = new OaoCacheManagerBuilder();
        return builder
                .connectionFactory(connectionFactory)
                .defaultExpires(Duration.ofMinutes(10))
                .expires(cacheUnits.stream().collect(Collectors.toMap(o -> o.name(), o -> o))).build();
    }

    @Bean
    @ConditionalOnMissingBean
    public CachingConfigurer CachingConfigurer() {
        return new CachingConfigurerSupport() {
            @Override
            public KeyGenerator keyGenerator() {
                return (target, method, params) -> Stream.of(
                        target.getClass().getSimpleName(),
                        method.getName(),
                        Arrays.stream(method.getParameterTypes()).map(types -> types.getSimpleName()).collect(Collectors.joining(",")),
                        Arrays.stream(params).map(param -> JSON.toJSONString(param)).collect(Collectors.joining(",")))
                        .collect(Collectors.joining(":"));
            }

            @Override
            public CacheErrorHandler errorHandler() {
                return new CacheErrorHandler() {
                    @Override
                    public void handleCacheGetError(RuntimeException exception, Cache cache, Object key) {
                        logger.error("handleCacheGetError:" + key, exception);
                    }

                    @Override
                    public void handleCachePutError(RuntimeException exception, Cache cache, Object key, Object value) {
                        logger.error("handleCachePutError:" + key, exception);
                    }

                    @Override
                    public void handleCacheEvictError(RuntimeException exception, Cache cache, Object key) {
                        logger.error("handleCacheEvictError:" + key, exception);
                    }

                    @Override
                    public void handleCacheClearError(RuntimeException exception, Cache cache) {
                        logger.error("handleCacheClearError:" + cache.getName(), exception);
                    }
                };
            }

        };
    }
}
