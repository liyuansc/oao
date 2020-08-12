package com.oao.support.mlog;

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;

public class MLogAutoConfig {
    @Bean
    @ConditionalOnMissingBean
    public MLogAspect mLogAspect() {
        return new MLogAspect();
    }
}
