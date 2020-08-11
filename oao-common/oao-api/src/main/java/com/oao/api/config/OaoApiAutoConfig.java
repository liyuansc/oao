package com.oao.api.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Import(OaoOkHttpClientAutoConfig.class)
public class OaoApiAutoConfig {
}
