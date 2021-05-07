package com.zgy.develop.spring.proxy;

import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

/**
 * 链代理
 * @author zgy
 * @data 2021/5/7 0:22
 */

public class CustomProxyChain {

    // 目标类
    private final Class<?> targetClass;
    // 目标对象
    private final Object targetObject;
    // 目标方法
    private final Method targetMethod;
    // 方法代理
    private final MethodProxy methodProxy;
    // 方法参数
    private final Object[] methodParams;
    // 代理列表
    private List<IProxy> proxyList;
    // 代理索引
    private int proxyIndex = 0;

    public CustomProxyChain(Class<?> targetClass, Object targetObject, Method targetMethod, MethodProxy methodProxy, Object[] methodParams, List<IProxy> proxyList) {
        this.targetClass = targetClass;
        this.targetObject = targetObject;
        this.targetMethod = targetMethod;
        this.methodProxy = methodProxy;
        this.methodParams = methodParams;
        this.proxyList = proxyList;
    }

    public Object[] getMethodParams() {
        return this.methodParams;
    }

    public Class<?> getTargetClass() {
        return this.targetClass;
    }

    public Method getTargetMethod() {
        return this.targetMethod;
    }

    /**
     * 递归执行代理链
     */
    public Object doProxyChain() throws Throwable {
        Object methodResult;
        if (this.proxyIndex < this.proxyList.size()) {
            // 执行增强方法
            methodResult = this.proxyList.get(this.proxyIndex++).doProxy(this);
        } else {
            // 目标方法最后执行且只执行一次
            methodResult = this.methodProxy.invokeSuper(this.targetObject, this.methodParams);
        }
        return methodResult;
    }
}
