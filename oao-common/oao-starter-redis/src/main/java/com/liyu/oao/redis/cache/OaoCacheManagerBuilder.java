package com.liyu.oao.redis.cache;

import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.support.config.FastJsonConfig;
import com.alibaba.fastjson.support.spring.FastJsonRedisSerializer;
import com.liyu.oao.redis.cache.unit.ICacheUnit;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheWriter;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import java.time.Duration;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Created by liyu on 2018/11/28.
 */
public class OaoCacheManagerBuilder {
    private RedisConnectionFactory connectionFactory;
    private Duration defaultExpires;
    private Map<String, ICacheUnit> unitMap;
    private boolean disableKeyPrefix;

    public OaoCacheManagerBuilder() {
    }

    private OaoCacheManagerBuilder(RedisConnectionFactory connectionFactory, Duration defaultExpires, Map<String, ICacheUnit> unitMap, boolean disableKeyPrefix) {
        this.connectionFactory = connectionFactory;
        this.defaultExpires = defaultExpires;
        this.unitMap = unitMap;
        this.disableKeyPrefix = disableKeyPrefix;
    }

    public OaoCacheManagerBuilder connectionFactory(RedisConnectionFactory connectionFactory) {
        return new OaoCacheManagerBuilder(connectionFactory, this.defaultExpires, this.unitMap, this.disableKeyPrefix);
    }

    public OaoCacheManagerBuilder defaultExpires(Duration defaultExpires) {
        return new OaoCacheManagerBuilder(this.connectionFactory, defaultExpires, this.unitMap, this.disableKeyPrefix);
    }

    public OaoCacheManagerBuilder expires(Map<String, ICacheUnit> expires) {
        return new OaoCacheManagerBuilder(this.connectionFactory, this.defaultExpires, expires, this.disableKeyPrefix);
    }


    public OaoCacheManagerBuilder disableKeyPrefix() {
        return new OaoCacheManagerBuilder(this.connectionFactory, this.defaultExpires, unitMap, false);
    }

    public OaoCacheManager build() {
        //value序列化
        FastJsonRedisSerializer<Object> valueSerializer = new FastJsonRedisSerializer<>(Object.class);
        FastJsonConfig fastJsonConfig = new FastJsonConfig();
        fastJsonConfig.setSerializerFeatures(SerializerFeature.WriteClassName);
        valueSerializer.setFastJsonConfig(fastJsonConfig);

        RedisCacheWriter redisCacheWriter = RedisCacheWriter.nonLockingRedisCacheWriter(this.connectionFactory);
        RedisCacheConfiguration defaultCacheConfig = RedisCacheConfiguration.defaultCacheConfig()
                .entryTtl(this.defaultExpires)
                .serializeKeysWith(RedisSerializationContext.SerializationPair.fromSerializer(new StringRedisSerializer()))
                .serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(valueSerializer))
                .computePrefixWith(s -> s.concat(":"));
        if (this.disableKeyPrefix) {
            defaultCacheConfig = defaultCacheConfig.disableKeyPrefix();
        }
        Map<String, RedisCacheConfiguration> configMap = null;
        if (this.unitMap != null) {
            RedisCacheConfiguration finalDefaultCacheConfig = defaultCacheConfig;
            configMap = this.unitMap.entrySet().stream().collect(Collectors.toMap(e -> e.getKey(), e -> finalDefaultCacheConfig.entryTtl(e.getValue().expires())));
        }
        //初始化RedisCacheManager
        return new OaoCacheManager(redisCacheWriter, defaultCacheConfig, configMap, this.unitMap);
    }
}
