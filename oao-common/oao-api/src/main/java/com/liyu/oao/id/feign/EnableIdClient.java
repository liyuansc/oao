package com.liyu.oao.id.feign;

import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
//@Inherited
@Import(IdClientAutoConfig.class)
public @interface EnableIdClient {
}
