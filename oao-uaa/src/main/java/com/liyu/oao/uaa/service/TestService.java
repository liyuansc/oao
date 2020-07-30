package com.liyu.oao.uaa.service;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import org.springframework.stereotype.Service;

import java.time.Duration;

@Service
public class TestService {
    private final Cache<String, String> testCache;

    {
        testCache = Caffeine.newBuilder().expireAfterWrite(Duration.ofSeconds(4)).build();
    }
}
