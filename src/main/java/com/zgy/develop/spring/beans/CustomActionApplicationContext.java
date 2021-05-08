package com.zgy.develop.spring.beans;

import com.zgy.develop.spring.annotation.*;
import com.zgy.develop.spring.common.enums.CommonEnums;
import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.io.FileNotFoundException;
import java.lang.reflect.Field;
import java.net.URL;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author zgy
 * @data 2021/4/26 20:44
 */

@Slf4j
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
     *
     * @param basePackage
     * @throws FileNotFoundException
     */
    private void load(String basePackage) throws FileNotFoundException {
        // 拼装包路径
        String path = CommonEnums.SEPARATE.value + basePackage.replaceAll(CommonEnums.SPOT.value, CommonEnums.SEPARATE.value);
        URL url = getClass().getResource(path);
        // 如果路径不存在，直接抛出异常
        if (url == null) {
            throw new FileNotFoundException("package " + path + " not exists");
        }
        File dir = new File(url.getFile());
        for (File file : dir.listFiles()) {
            // 递归遍历文件夹
            if (file.isDirectory()) {
                load(basePackage + CommonEnums.SPOT.value + file.getName());
            }
            // 判断是否是class文件
            if (!file.getName().endsWith(CommonEnums.CLASS_SUFFIX.value)) {
                continue;
            }
            // 加入集合
            classNames.add(basePackage + CommonEnums.SPOT.value + file.getName().replace(CommonEnums.CLASS_SUFFIX.value, ""));
        }
    }

    @Override
    protected void autowired(CustomBeanFactory beanFactory) throws Exception {
        for (String className : classNames) {
            Class clazz = Class.forName(className);

            String beanName = null;
            // 判断是否需要ioc容器管理
            if (clazz.isAnnotationPresent(CustomService.class)) {
                CustomService service = (CustomService) clazz.getAnnotation(CustomService.class);
                beanName = service.name();
            } else if (clazz.isAnnotationPresent(CustomMapper.class)) {
                CustomMapper mapper = (CustomMapper) clazz.getAnnotation(CustomMapper.class);
                beanName = mapper.name();
            }

            // 没有特别指定BeanName
            if (beanName == null) {
                // 根据类名自动生成
                beanName = lowerFirst(clazz.getSimpleName().replace(".class", ""));
            }
            // 已有重复的BeanName，抛出异常
            if (iocMap.containsKey(beanName)) {
                throw new Exception("bean " + beanName + " already exists");
            }
            // 放进IOC容器
            iocMap.put(beanName, clazz.newInstance());
        }
    }

    @Override
    protected void loadAspect() {

        for (String key : iocMap.keySet()) {
            Object bean = iocMap.get(key);
            if (bean.getClass().isAnnotationPresent(CustomAspect.class)) {
                // 创建代理类
                GenerationProxy(key, bean);
            }
        }
    }

    /**
     * 生产代理类
     * @param key
     * @param bean
     */
    private void GenerationProxy(String key, Object bean) {
        // 获取代理
//        Object proxy = CustomAspectInstanceFactory.getAspectInstance(bean);
        Object proxy = "1";
        Field[] fields = bean.getClass().getDeclaredFields();
        try {
            // 对象的属性赋给代理对象
            for (Field f : fields) {
                f.setAccessible(true);
                f.set(proxy, f.get(bean));
            }
        } catch (Exception e) {
            log.error("获取代理失败:{}",e);
        }
        proxyMap.put(key, proxy);
    }

    /**
     * 注入
     */
    protected void autowiredProxy() {
        for (Object bean : iocMap.values()) {
            Field[] fields = bean.getClass().getDeclaredFields();
            for (Field f : fields) {
                // 是否需要自动注入
                if (f.isAnnotationPresent(CustomAutowired.class)) {
                    autowiredProxy(f, bean);
                }
            }
        }
    }

    /**
     * 注入对象
     * @param f
     * @param bean
     */
    private void autowiredProxy(Field f, Object bean) {
        Class<?> clazz = f.getType();

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

    /**
     * 将类名首字符变为小写
     * @param str 类名
     * @return
     */
    private String lowerFirst(String str) {
        char[] chars = str.toCharArray();
        chars[0] += 32;
        return new String(chars);
    }
}
