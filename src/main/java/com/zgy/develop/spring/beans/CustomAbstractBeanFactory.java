package com.zgy.develop.spring.beans;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author zgy
 * @data 2021/4/26 20:22
 */

public abstract class CustomAbstractBeanFactory {

    // IOC容器
    protected Map<String, Object> iocMap = new ConcurrentHashMap<>();

    // class文件集合
    protected List<String> classNames = new ArrayList<>();

    /**
     * 扫描并加载组件
     *
     * @param clazz
     */
    protected abstract void scan(Class clazz, Properties properties) throws FileNotFoundException;

    /**
     * 装配组件
     * @param beanFactory
     */
    protected abstract void autowired(CustomBeanFactory beanFactory) throws Exception;

    /**
     * 注入属性
     */
    protected abstract  void inject() throws Exception;

    /**
     * 加载切面类型的组件
     */
    protected abstract void loadAspect();
}
