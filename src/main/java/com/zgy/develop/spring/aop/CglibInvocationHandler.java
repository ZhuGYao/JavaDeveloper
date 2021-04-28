package com.zgy.develop.spring.aop;

import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

/**
 * @author zgy
 * @data 2021/4/25 20:22
 */


public class CglibInvocationHandler implements MethodInterceptor
{
    @Override
    public Object intercept(Object obj, Method method, Object[] args, MethodProxy proxy) throws Throwable
    {
        before(method,args);
        try
        {
            Object result = proxy.invokeSuper(obj, args);
            return result;
        }catch (Exception e)
        {
            e.printStackTrace();
            afterThrowing(method,args);
        }finally
        {
            after(method,args);
        }
        return null;
    }

    /**
     * 异常处理
     * @param method
     * @param args
     */
    private void afterThrowing(Method method, Object[] args)
    {
        int[] arr = new int[1000];

        int num = 10000;

        int arrIndex = num / 32;
        int bitIndex = num % 32;

        arr[arrIndex] = arr[arrIndex] | 1 << bitIndex;

    }

    /**
     * 后置处理
     */
    private void after(Method method,Object[] args)
    {

    }

    /**
     * 前置处理
     */
    private void before(Method method,Object[] args)
    {

    }
}
