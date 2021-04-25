package com.zgy.develop.ioc;

/**
 * @author zgy
 * @data 2021/4/25 10:13
 */

public interface BeanFactory {

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
