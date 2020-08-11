package com.oao.swagger.config;

import com.oao.common.constant.CodeConstant;
import com.oao.common.constant.CurrentApp;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

@ConditionalOnMissingBean(Docket.class)
public class OaoSwaggerAutoConfig {

    @Bean
    public Docket createRestApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                //apiInfo指定测试文档基本信息，这部分将在页面展示
                .apiInfo(apiInfo())
                .select()
                //apis() 控制哪些接口暴露给swagger，
                // RequestHandlerSelectors.any() 所有都暴露
                // RequestHandlerSelectors.basePackage("com.info.*")  指定包位置
                .apis(RequestHandlerSelectors.basePackage(CodeConstant.BASE_PACKAGE))
                .paths(PathSelectors.any())
                .build();
    }

    //基本信息，页面展示
    private ApiInfo apiInfo() {
        String name = CurrentApp.getAppId();

        return new ApiInfoBuilder()
                .title(name)
                .description(name)
                //联系人实体类
                .contact(
                        new Contact(name, null, null)
                )
                //版本号
//                .version("1.0.0-SNAPSHOT")
                .build();
    }
}
