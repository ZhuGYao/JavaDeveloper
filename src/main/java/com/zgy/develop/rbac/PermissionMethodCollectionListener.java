package com.zgy.develop.rbac;

import com.zgy.develop.annotation.rbac.PermissionRequired;
import com.zgy.develop.common.enums.YesOrNoEnum;
import com.zgy.develop.rbac.dao.PermissionDao;
import com.zgy.develop.rbac.pojo.Permission;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.lang.reflect.Method;
import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * 自动扫描，添加到权限表
 * @author zgy
 * @data 2021/5/11 22:33
 */

@Component
public class PermissionMethodCollectionListener implements ApplicationListener<ContextRefreshedEvent> {

    @Autowired
    private ApplicationContext applicationContext;

    @Autowired
    private PermissionDao permissionDao;

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {

        // 得到t_permission已有的所有权限方法
        Set<String> permissionsFromDB = new HashSet<>();
        List<Permission> permissions = permissionDao.selectAll();
        if (permissions.size() > 0) {
            permissions.forEach(permission -> permissionsFromDB.add(permission.getMethod()));
        }

        // 遍历所有Controller
        Map<String, Object> beanMap = applicationContext.getBeansWithAnnotation(Controller.class);
        Collection<Object> beans = beanMap.values();
        for (Object bean : beans) {
            Class<?> controllerClazz = bean.getClass();

            // 如果Controller上有PermissionRequired注解，那么所有接口都要收集(isApiMethod)，否则只收集打了@PermissionRequired的接口(hasPermissionAnnotation)
            Predicate<Method> filter = AnnotationUtils.findAnnotation(controllerClazz, PermissionRequired.class) != null
                    ? this::isApiMethod
                    : this::hasPermissionAnnotation;

            // 过滤出Controller中需要权限验证的method
            Set<String> permissionMethodsWithinController = getPermissionMethodsWithinController(
                    controllerClazz.getName(),
                    controllerClazz.getMethods(),
                    filter
            );

            for (String permissionMethodInMemory : permissionMethodsWithinController) {
                // 如果是新增的权限方法
                if (!permissionsFromDB.contains(permissionMethodInMemory)) {
                    Permission permission = new Permission();
                    permission.setMethod(permissionMethodInMemory);
                    permission.setCreateTime(new Date());
                    permission.setUpdateTime(new Date());
                    permission.setDeleted(YesOrNoEnum.NO.value);
                    permissionDao.insert(permission);
                }
            }
        }

    }

    /**
     * 遍历拼装，获得所有的权限方法
     * @param controllerName
     * @param methods
     * @param filter
     * @return
     */
    private Set<String> getPermissionMethodsWithinController(String controllerName, Method[] methods, Predicate<Method> filter) {
        return Arrays.stream(methods)
                .filter(filter)
                .map(method -> {
                    StringBuilder sb = new StringBuilder();
                    String methodName = method.getName();
                    return sb.append(controllerName).append("#").append(methodName).toString();
                })
                .collect(Collectors.toSet());
    }

    /**
     * 是否包含权限注解
     * @param method
     * @return
     */
    private boolean hasPermissionAnnotation(Method method) {
        return isApiMethod(method) && AnnotationUtils.findAnnotation(method, PermissionRequired.class) != null;
    }

    /**
     * 是否是api方法
     * @param method
     * @return
     */
    private boolean isApiMethod(Method method) {
        return AnnotationUtils.findAnnotation(method, RequestMapping.class) != null;
    }
}
