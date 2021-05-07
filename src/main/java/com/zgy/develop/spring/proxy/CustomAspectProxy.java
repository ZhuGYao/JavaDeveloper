package com.zgy.develop.spring.proxy;

import java.lang.reflect.Method;

/**
 *
 * 切面代理抽象类
 * @author zgy
 * @data 2021/5/7 0:32
 */

public class CustomAspectProxy implements IProxy{

    @Override
    public Object doProxy(CustomProxyChain proxyChain) throws Throwable {
        return null;
    }

    /**
     * 开始增强
     */
    public void begin() {
    }

    /**
     * 切入点判断
     */
    public boolean intercept(Method method, Object[] params) throws Throwable {
        return true;
    }

    /**
     * 前置增强
     */
    public void before(Method method, Object[] params) throws Throwable {
    }

    /**
     * 后置增强
     */
    public void after(Method method, Object[] params) throws Throwable {
    }

    /**
     * 异常增强
     */
    public void error(Method method, Object[] params, Throwable e) {
    }

    /**
     * 最终增强
     */
    public void end() {
    }
}
