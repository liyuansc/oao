package com.liyu.oao.api.annotation;

import java.lang.annotation.*;

@Target({ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Documented
//@Inherited
public @interface Login {
    boolean isFull() default false;
}