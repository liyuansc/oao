//package com.liyu.oao.user.configuration.datasource;
//
//import com.liyu.oao.user.constant.BeanName;
//import org.springframework.boot.context.properties.ConfigurationProperties;
//import org.springframework.boot.jdbc.DataSourceBuilder;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//
//import javax.sql.DataSource;
//
///**
// * Created by liyu on 2018/12/18.
// */
//@Configuration
//public class DatasourceConfiguration {
//    @Bean(name = BeanName.PRIMARY_DS)
//    @ConfigurationProperties(prefix = "datasource.primary")
//    public DataSource dataSource() {
//        DataSourceBuilder.create()
//        return DataSourceBuilder.create().build();
//    }
//}
