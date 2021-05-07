package com.zgy.develop.spring.proxy;

/**
 *
 * 代理接口
 * @author zgy
 * @data 2021/5/7 0:21
 */

public interface IProxy {


    /**
     * 执行链式代理,执行顺序取决于添加到链上的先后顺序
     * @param proxyChain
     * @return
     * @throws Throwable
     */
    Object doProxy(CustomProxyChain proxyChain) throws Throwable;
}
