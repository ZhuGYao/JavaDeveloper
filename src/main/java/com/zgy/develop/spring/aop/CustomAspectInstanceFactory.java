package com.zgy.develop.spring.aop;

import net.sf.cglib.proxy.Enhancer;

/**
 *  创建代理
 * @author zgy
 * @data 2021/4/24 21:22
 */


public abstract class CustomAspectInstanceFactory {

    // 创建代理类
    public static <T> T getAspectInstance(T instance) {
        CglibInvocationHandler handler = new CglibInvocationHandler();
        // 字节码增强器
        Enhancer enhancer = new Enhancer();
        // 设置父类
        enhancer.setSuperclass(instance.getClass());
        // 回调
        enhancer.setCallback(handler);
        return (T) enhancer.create();
    }

    public ClassLoader getAspectClassLoader() {
        return this.getClass().getClassLoader();
    }
}
