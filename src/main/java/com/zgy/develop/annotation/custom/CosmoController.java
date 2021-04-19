package com.zgy.develop.annotation.custom;

import org.springframework.web.bind.annotation.RestController;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 自定义全局拦截包装返回值
 * @author zgy
 * @data 2021/4/19 17:16
 */

@RestController
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface CosmoController {
}
