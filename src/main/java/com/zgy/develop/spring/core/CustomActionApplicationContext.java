package com.zgy.develop.spring.ioc;

import com.zgy.develop.spring.annotation.CustomAutowired;

import java.lang.reflect.Field;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author zgy
 * @data 2021/4/26 20:44
 */

public class CustomActionApplicationContext extends CustomAbstractBeanFactory {

    // 获取IOC容器的功能
    private CustomBeanFactory beanFactory;
    // 代理对象容器
    private Map<String, Object> proxyMap = new ConcurrentHashMap<>();

    @Override
    protected void scan(Class clazz, Properties properties) {

    }

    @Override
    protected void autowired(CustomBeanFactory beanFactory) {

    }

    @Override
    protected void loadAspect() {

    }

    protected void autowiredProxy() {
        for (Object o : iocMap.values()) {
            Field[] fields = o.getClass().getDeclaredFields();
            for (Field f : fields) {
                if (f.isAnnotationPresent(CustomAutowired.class)) {
                    autowiredProxy(f, o);
                }
            }
        }
    }

    private void autowiredProxy(Field f, Object bean) {
        Class<?> clazz = f.getType();
        System.out.println(this.proxyMap);

        String name = null;
        for (String key : iocMap.keySet()) {
            Object o = iocMap.get(key);
            if (o.getClass() == clazz || o.getClass().getSuperclass() == clazz) {
                name = key;
            } else {
                for (Class c : o.getClass().getInterfaces()) {
                    if (c == clazz) {
                        name = key;
                        break;
                    }
                }
            }
        }
        if (name != null) {
            try {
                if (this.proxyMap.containsKey(name)) {
                    f.setAccessible(true);
                    f.set(bean, this.proxyMap.get(name));
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
