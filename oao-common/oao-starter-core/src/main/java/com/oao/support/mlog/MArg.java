package com.oao.support.mlog;

import java.lang.annotation.Documented;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Created by liyu on 2018/10/23.
 */
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
public @interface MArg {
    String key() default "";

    String value();
}
