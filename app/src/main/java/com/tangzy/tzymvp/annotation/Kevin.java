package com.tangzy.tzymvp.annotation;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * ${CLASS} class
 *
 * @author Administrator
 * @date 2019/8/21
 */
@Retention(RetentionPolicy.RUNTIME)
public @interface Kevin {
    String name() default "kevin";
}

