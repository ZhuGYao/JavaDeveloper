package com.zgy.develop.spring.core;

import com.zgy.develop.spring.annotation.CustomAutowired;
import com.zgy.develop.spring.annotation.CustomComponentScan;
import com.zgy.develop.spring.common.enums.CommonEnums;

import java.io.File;
import java.io.FileNotFoundException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.net.URL;
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
    protected void scan(Class clazz, Properties properties) throws FileNotFoundException {
        // 获取指定扫描包路径
        CustomComponentScan customComponentScan = (CustomComponentScan) clazz.getDeclaredAnnotation(CustomComponentScan.class);
        // 如果主动指定
        if (customComponentScan != null) {
            load(customComponentScan.value());
        } else {
            // TODO 自动获取路径
        }
    }

    /**
     * 递归得到所有的class
     * @param basePackage
     * @throws FileNotFoundException
     */
    private void load(String basePackage) throws FileNotFoundException {
        // 拼装包路径
        String path = CommonEnums.SEPARATE.value + basePackage.replaceAll(".", CommonEnums.SEPARATE.value);
        URL url = getClass().getResource(path);
        // 如果路径不存在，直接抛出异常
        if (url == null) {
            throw new FileNotFoundException("package " + path + " not exists");
        }
        File dir = new File(url.getFile());
        for (File file : dir.listFiles()) {
            // 递归遍历文件夹
            if (file.isDirectory()) {
                load(basePackage + "." + file.getName());
            }
            // 判断是否是class文件
            if (!file.getName().endsWith(".class")) {
                continue;
            }
            // 加入集合
            classNames.add(basePackage + "." + file.getName().replace(".class", ""));
        }
    }

    private void loadComponent(String classPath, boolean isWeb) {

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
