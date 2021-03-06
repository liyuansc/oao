package com.liyu.oao.user.config;

import com.baomidou.mybatisplus.core.incrementer.DefaultIdentifierGenerator;
import com.baomidou.mybatisplus.core.incrementer.IdentifierGenerator;
import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
import com.baomidou.mybatisplus.extension.plugins.pagination.optimize.JsqlParserCountOptimize;
import com.liyu.oao.user.constant.BeanName;
import com.liyu.oao.db.DateMetaObjectHandler;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import reactor.core.scheduler.Scheduler;
import reactor.core.scheduler.Schedulers;

import static com.liyu.oao.user.config.MybatisPlusConfig.MAPPER_LOCATION;

/**
 * Created by liyu on 2020/2/20
 */
@Configuration
@EnableTransactionManagement
@MapperScan(basePackages = MAPPER_LOCATION)
public class MybatisPlusConfig {
    public final static String MAPPER_LOCATION = "com.liyu.oao.**.dao";

    @Bean(BeanName.DB_SCHEDULER)
    public Scheduler scheduler() {
        return Schedulers.newElastic("db-scheduler");
    }


    @Bean
    public PaginationInterceptor paginationInterceptor() {
        PaginationInterceptor paginationInterceptor = new PaginationInterceptor();
        // 设置请求的页面大于最大页后操作， true调回到首页，false 继续请求  默认false
        // paginationInterceptor.setOverflow(false);
        // 设置最大单页限制数量，默认 500 条，-1 不受限制
        // paginationInterceptor.setLimit(500);
        // 开启 count 的 join 优化,只针对部分 left join
        paginationInterceptor.setCountSqlParser(new JsqlParserCountOptimize(true));
        return paginationInterceptor;
    }

    @Bean
    public DateMetaObjectHandler staffMetaObjectHandler() {
        return new DateMetaObjectHandler();
    }

    @Bean
    public IdentifierGenerator identifierGenerator() {
        return new DefaultIdentifierGenerator();
    }
}
