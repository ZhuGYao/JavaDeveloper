package com.zgy.develop.ioc;

import com.zgy.develop.annotation.ioc.Autowired;
import com.zgy.develop.annotation.ioc.Mapper;
import com.zgy.develop.annotation.ioc.Service;

import java.io.File;
import java.io.FileNotFoundException;
import java.lang.reflect.Field;
import java.net.URL;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 自定义BeanFactory
 * @author zgy
 * @data 2021/4/25 10:25
 */

public class CustomBeanFactory implements BeanFactory{

    // 容器
    private Map<String, Object> ioc = new ConcurrentHashMap<>();

    private Map<Class, Object> iocForClass = new ConcurrentHashMap<>();
    // class文件集合
    private List<String> classNames = new ArrayList<>();

    public CustomBeanFactory(String basePackage) {
        try {
            scan(basePackage);
            init();
            assemble();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 循环注入
     * @throws IllegalAccessException
     */
    public void assemble() throws IllegalAccessException {

        for (Object value : ioc.values()) {

            Class<?> classValue = value.getClass();
            // 判断是否是需要自动注入对象
            if (!classValue.isAnnotationPresent(Service.class) && !classValue.isAnnotationPresent(Mapper.class)) {
                continue;
            }

            // 循环遍历类中属性
            Field[] fields = classValue.getDeclaredFields();
            for (Field field : fields) {
                // 防止修饰符private异常
                field.setAccessible(true);
                // 如果需要自动注入
                if (field.isAnnotationPresent(Autowired.class)) {
                    String beanName = lowerFirst(field.getType().getSimpleName().replace(".class", ""));
                    if (!containsBean(beanName)) {
                        continue;
                    }
                    // 自动注入
                    field.set(value, ioc.get(beanName));
                }
            }
        }

    }

    /**
     * 初始化IOC容器
     * @throws ClassNotFoundException
     * @throws IllegalAccessException
     * @throws InstantiationException
     */
    public void init() throws ClassNotFoundException, IllegalAccessException, InstantiationException {
        for (String className : classNames) {
            Class clazz = Class.forName(className);

            // 判断是否是mapper
            if (clazz.isAnnotationPresent(Mapper.class)) {

                Mapper repository = (Mapper) clazz.getAnnotation(Mapper.class);
                // 获取名称
                String beanName = repository.name();
                if ("".equals(beanName)) {
                    beanName = lowerFirst(clazz.getSimpleName().replace(".class", ""));
                }
                // 存入IOC
                ioc.put(beanName, clazz.newInstance());
                iocForClass.put(clazz, clazz.newInstance());

            } else if (clazz.isAnnotationPresent(Service.class)) {

                Service service = (Service) clazz.getAnnotation(Service.class);
                Class<?>[] interfaces = service.getClass().getInterfaces();
                String beanName = service.name();
                if ("".equals(beanName)) {
                    beanName = lowerFirst(clazz.getSimpleName().replace(".class", ""));
                }
                ioc.put(beanName, clazz.newInstance());
                iocForClass.put(clazz, clazz.newInstance());
            }
        }
    }

    /**
     * 扫描包路径下的文件
     * @param basePackage
     * @throws FileNotFoundException
     */
    public void scan(String basePackage)  throws FileNotFoundException {
        String path = "/" + basePackage.replaceAll("\\.", "/");
        URL url = getClass().getResource(path);

        if (url == null) throw new FileNotFoundException("package " + path + " not exists");

        File dir = new File(url.getFile());

        for (File file : dir.listFiles()) {

            if (file.isDirectory()) {
                scan(basePackage + "." + file.getName());
            }

            if (!file.getName().endsWith(".class")) {
                continue;
            }
            classNames.add(basePackage + "." + file.getName().replace(".class", ""));

        }

    }

    @Override
    public Object getBean(String beanName) {
        return ioc.get(beanName);
    }

    @Override
    public <T> T getBean(Class<T> requiredType) {
        String beanName = lowerFirst(requiredType.getSimpleName());
        return (T) ioc.get(beanName);
    }

    @Override
    public boolean containsBean(String beanName) {
        return ioc.containsKey(beanName);
    }

    private String lowerFirst(String str) {
        char[] chars = str.toCharArray();
        chars[0] += 32;
        return new String(chars);
    }
}
