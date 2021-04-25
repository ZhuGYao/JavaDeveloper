package com.zgy.develop.ioc;

import java.util.*;

/**
 * @author zgy
 * @data 2021/4/25 10:25
 */

public class CustomBeanFactory implements BeanFactory{


    // 配置信息
    private Properties properties;
    // 容器
    private Map<String, Object> ioc = new HashMap<String, Object>();
    // class文件集合
    private List<String> classNames = new ArrayList<String>();
    // 指定扫描包
    private String basePackage;

    public void assemble() {

    }

    public void init() {

    }

    public void scan() {

    }

    public void load() {

    }

    @Override
    public Object getBean(String beanName) {
        return null;
    }

    @Override
    public <T> T getBean(Class<T> requiredType) {
        return null;
    }

    @Override
    public boolean isSingleton(String name) {
        return false;
    }

    @Override
    public boolean isPrototype(String name) {
        return false;
    }

    @Override
    public boolean containsBean(String beanName) {
        return ioc.containsKey(beanName);
    }
}
