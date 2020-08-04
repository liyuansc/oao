package com.liyu.oao.user.feign;

import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
//@Inherited
@Import(UserClientAutoConfig.class)
public @interface EnableUserClient {
}
