package com.oao.common.support.mlog;

import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Import(MLogAutoConfig.class)
//@Deprecated
//已经使用spring.factory自定载入，不需要注解引入了
public @interface EnableMLog {

}
