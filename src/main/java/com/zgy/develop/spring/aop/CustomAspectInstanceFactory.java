package com.zgy.develop.spring.aop;

import net.sf.cglib.proxy.Enhancer;

/**
 * @author zgy
 * @data 2021/4/24 21:22
 */


public abstract class MyAspectInstanceFactory
{
    public static <T> T getAspectInstance(T instance)
    {
        CglibInvocationHandler handler = new CglibInvocationHandler();
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(instance.getClass());
        enhancer.setCallback(handler);
        return (T) enhancer.create();
    }

    public ClassLoader getAspectClassLoader()
    {
        return this.getClass().getClassLoader();
    }
}
