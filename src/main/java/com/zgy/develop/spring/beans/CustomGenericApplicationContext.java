package com.zgy.develop.spring.beans;

import com.zgy.develop.spring.annotation.CustomEnableAutoProxy;
import com.zgy.develop.spring.annotation.CustomSpringBootApplication;
import lombok.extern.slf4j.Slf4j;

import java.io.FileNotFoundException;
import java.util.Map;
import java.util.Properties;

/**
 * @author zgy
 * @data 2021/4/26 20:54
 */

@Slf4j
public class CustomGenericApplicationContext extends CustomActionApplicationContext implements CustomApplicationContext {

    /**
     * 加载容器，注册主键
     *
     * @param clazz
     */
    protected void loadApplicationContext(Class<?> clazz, Properties properties) throws FileNotFoundException {
        // 检验配置类
        if (!validateConfig(clazz)) {
            log.error("{}不是一个Spring配置类", clazz.getName());

            return;
        }

        // 注册组件
        this.scan(clazz, properties);

        // 注入组件
        super.autowired(this);

        if (clazz.isAnnotationPresent(CustomEnableAutoProxy.class)) {
            // 加载切面组件
            super.loadAspect();

            // 注入代理组件
            super.autowiredProxy();
        } else {
            System.out.println("AOP相关功能未开启");
        }
    }

    /**
     * 检验Spring配置类是否包括注解
     *
     * @param clazz
     * @return
     */
    private boolean validateConfig(Class<?> clazz) {
        return clazz.isAnnotationPresent(CustomSpringBootApplication.class);
    }


    @Override
    public Object getBean(String beanName) {
        return iocMap.get(beanName);
    }

    @Override
    public <T> T getBean(Class<T> requiredType) {
        return null;
    }

    @Override
    public boolean containsBean(String beanName) {
        return false;
    }

    @Override
    public boolean register(String name, Object bean) {
        return false;
    }

    @Override
    public boolean remove(String name) {
        return false;
    }

    @Override
    public boolean remove(Class<?> requiredType) {
        return false;
    }

    @Override
    public Map<String, Object> getIoc() {
        return null;
    }
}
