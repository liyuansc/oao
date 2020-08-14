package com.oao.common.support.mlog;

import java.lang.annotation.*;

/**
 * Created by liyu on 2018/10/23.
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
//@Inherited
public @interface MLog {
    String title() default "";

    MArg[] value() default {};

    boolean debug() default true;

    boolean afterThrow() default false;
}
