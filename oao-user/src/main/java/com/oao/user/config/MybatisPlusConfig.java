package com.oao.user.config;

import com.oao.common.constant.CodeConstant;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;

/**
 * Created by liyu on 2020/2/20
 */
@Configuration
@MapperScan(basePackages = CodeConstant.BASE_PACKAGE + ".**.dao")
public class MybatisPlusConfig {
}
