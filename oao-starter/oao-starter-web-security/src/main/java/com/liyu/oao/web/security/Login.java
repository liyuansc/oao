package com.liyu.oao.web.security;

import java.lang.annotation.*;

@Target({ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
public @interface Login {
    boolean isFull() default false;
}
