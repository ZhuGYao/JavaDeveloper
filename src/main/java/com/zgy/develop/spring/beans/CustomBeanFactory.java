package com.zgy.develop.spring.core;

/**
 * @author zgy
 * @data 2021/4/25 21:13
 */

public interface CustomBeanFactory {

    /**
     * 根据名称获取bean
     * @param beanName
     * @return
     */
    Object getBean(String beanName);

    /**
     * 根据Class获取bean
     * @param requiredType
     * @param <T>
     * @return
     */
    <T> T getBean(Class<T> requiredType);

    /**
     * 查看bean是否存在
     * @param beanName
     * @return
     */
    boolean containsBean(String beanName);
}
