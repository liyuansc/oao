package com.oao.api.annotation;

import java.lang.annotation.*;

@Target({ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Documented
//@Inherited
public @interface OaoLogin {
    boolean isFull() default false;
}
