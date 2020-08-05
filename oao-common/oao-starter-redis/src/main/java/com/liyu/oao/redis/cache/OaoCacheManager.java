package com.liyu.oao.redis.cache;

import com.liyu.oao.redis.cache.unit.ICacheUnit;
import org.springframework.cache.Cache;
import org.springframework.cache.transaction.TransactionAwareCacheDecorator;
import org.springframework.data.redis.cache.RedisCache;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.cache.RedisCacheWriter;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;
import java.util.Map;

/**
 * Created by liyu on 2018/11/28.
 */
public class OaoCacheManager extends RedisCacheManager {
    private Map<String, ICacheUnit> cacheNameConfigMap;
    private Map<String, RedisCacheConfiguration> initialCacheConfigurations;
    private Field cacheConfigField = ReflectionUtils.findField(RedisCache.class, "cacheConfig");

    OaoCacheManager(RedisCacheWriter cacheWriter, RedisCacheConfiguration defaultCacheConfiguration, Map<String, RedisCacheConfiguration> initialCacheConfigurations, Map<String, ICacheUnit> cacheNameConfigMap) {
        super(cacheWriter, defaultCacheConfiguration, initialCacheConfigurations);
        this.cacheNameConfigMap = cacheNameConfigMap;
        this.initialCacheConfigurations = initialCacheConfigurations;
    }


    @Override
    public Cache getCache(String name) {
        Cache cache = super.getCache(name);
        do {
            RedisCache redisCache = null;
            if (cache instanceof RedisCache) {
                redisCache = (RedisCache) cache;
            } else if (cache instanceof TransactionAwareCacheDecorator) {
                Cache targetCache = ((TransactionAwareCacheDecorator) cache).getTargetCache();
                if ((targetCache instanceof RedisCache)) {
                    redisCache = (RedisCache) targetCache;
                }
            }
            if (redisCache == null) break;
            if (this.cacheNameConfigMap == null) break;
            ICacheUnit cacheUnit = this.cacheNameConfigMap.get(name);
            if (cacheUnit == null) break;
            if (cacheUnit.dynamicExpires()) {
                RedisCacheConfiguration cacheConfiguration = this.initialCacheConfigurations.computeIfPresent(name, (k, v) -> v.entryTtl(cacheUnit.expires()));
                if (cacheConfiguration != null) {
                    ReflectionUtils.makeAccessible(cacheConfigField);
                    ReflectionUtils.setField(cacheConfigField, redisCache, cacheConfiguration);
                }
            }
        } while (false);
        return cache;
    }
}
