package com.zgy.develop.annotation.rbac;

import com.zgy.develop.rbac.enums.Logical;
import com.zgy.develop.rbac.enums.UserType;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author zgy
 * @data 2021/5/10 16:13
 */

@LoginRequired
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE, ElementType.METHOD})
public @interface PermissionRequired {

    /**
     * 角色，默认游客权限
     * @return
     */
    UserType[] userType() default {UserType.VISITOR};

    /**
     * 逻辑关系，比如 ADMIN&&TEACHER 或者 ADMIN||TEACHER
     *
     * @return
     */
    Logical logical() default Logical.OR;
}
