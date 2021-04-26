package com.zgy.develop.spring.ioc;

import java.util.Map;

/**
 * @author zgy
 * @data 2021/4/26 19:49
 */

public interface CustomApplicationContext extends CustomBeanFactory {

    /**
     * 注册一个bean
     * @param name
     * @param bean
     * @return
     */
    boolean register(String name, Object bean);

    /**
     * 根据name删除一个Bean
     * @param name
     * @return
     */
    boolean remove(String name);

    /**
     * 根据类型删除一个bean
     * @param requiredType
     * @return
     */
    boolean remove(Class<?> requiredType);

    /**
     * 获取IOC容器
     * @return
     */
    Map<String,Object> getIoc();
}
