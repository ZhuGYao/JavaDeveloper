package com.zgy.develop.spring.proxy;

import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.Method;

/**
 *
 * 切面代理抽象类
 * @author zgy
 * @data 2021/5/7 0:32
 */

@Slf4j
public class CustomAspectProxy implements IProxy{

    @Override
    public Object doProxy(CustomProxyChain proxyChain) throws Throwable {

        Object result = null;
        Method method = proxyChain.getTargetMethod();
        Object[] params = proxyChain.getMethodParams();

        // 开始
        begin();
        try {
            // 判断
            if (intercept(method, params)) {
                // 前置方法
                before(method, params);
                // 继续调用
                result = proxyChain.doProxyChain();
                // 后置执行
                after(method, params);
            } else {
                result = proxyChain.doProxyChain();
            }
        } catch (Exception e) {
            log.error("proxy failure", e);
            error(method, params, e);
            throw e;
        } finally {
            // 结束
            end();
        }

        return result;
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
