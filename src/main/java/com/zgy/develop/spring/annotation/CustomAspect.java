package com.zgy.develop.spring.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 切面注解
 * @author zgy
 * @data 2021/5/6 23:06
 */

@Target(value = ElementType.TYPE)
@Retention(value = RetentionPolicy.RUNTIME)
public @interface CustomAspect {

    /**
     * 指定包名
     */
    String packageName() default "";

    /**
     * 指定类名
     */
    String className() default "";
}
