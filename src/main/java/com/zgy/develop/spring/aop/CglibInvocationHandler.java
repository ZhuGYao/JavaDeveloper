package com.zgy.develop.spring.aop;

import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

/**
 * @author zgy
 * @data 2021/4/25 20:22
 */


public class CglibInvocationHandler implements MethodInterceptor {
    @Override
    public Object intercept(Object obj, Method method, Object[] args, MethodProxy proxy) throws Throwable {
        before(method, args);
        try {
            Object result = proxy.invokeSuper(obj, args);
            return result;
        } catch (Exception e) {
            e.printStackTrace();
            afterThrowing(method, args);
        } finally {
            after(method, args);
        }
        return null;
    }

    /**
     * 异常处理
     *
     * @param method
     * @param args
     */
    private void afterThrowing(Method method, Object[] args) {

    }

    /**
     * 后置处理
     * @param method
     * @param args
     */
    private void after(Method method, Object[] args) {

    }

    /**
     * 前置处理
     * @param method
     * @param args
     */
    private void before(Method method, Object[] args) {

    }
}
