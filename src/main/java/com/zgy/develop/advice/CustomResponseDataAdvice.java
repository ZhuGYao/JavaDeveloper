package com.zgy.develop.advice;

import com.zgy.develop.annotation.custom.CosmoController;
import com.zgy.develop.annotation.custom.IgnoreCosmo;
import com.zgy.develop.common.utils.CommonResultUtils;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

/**
 * 自定义实现返回值封装
 * @author zgy
 * @data 2021/4/19 17:22
 */

@RestControllerAdvice
public class CustomResponseDataAdvice implements ResponseBodyAdvice<Object> {
    @Override
    public boolean supports(MethodParameter methodParameter, Class<? extends HttpMessageConverter<?>> aClass) {
        // 标注了@CosmoController，且类及方法上都没有标注@IgnoreCosmo的方法才进行包装
        return methodParameter.getDeclaringClass().isAnnotationPresent(CosmoController.class)
                && !methodParameter.getDeclaringClass().isAnnotationPresent(IgnoreCosmo.class)
                && !methodParameter.getMethod().isAnnotationPresent(IgnoreCosmo.class);
    }

    @Override
    public Object beforeBodyWrite(Object o, MethodParameter methodParameter, MediaType mediaType, Class<? extends HttpMessageConverter<?>> aClass, ServerHttpRequest serverHttpRequest, ServerHttpResponse serverHttpResponse) {

        // 避免嵌套包装
        if (o instanceof CommonResultUtils) {
            return o;
        }
        return CommonResultUtils.ok(o);
    }
}
