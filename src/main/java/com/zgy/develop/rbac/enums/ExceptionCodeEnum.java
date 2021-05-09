package com.zgy.develop.rbac.enums;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * 错误代码枚举类
 * @author zgy
 * @data 2021/5/9 18:25
 */

public enum ExceptionCodeEnum {

    ERROR(-1, "网络错误"),
    SUCCESS(200, "成功"),
    NEED_LOGIN(10001, "需要登录"),
    PERMISSION_DENY(10002, "权限不足");

    private final Integer code;
    private final String desc;

    ExceptionCodeEnum(Integer code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    // 缓存
    private static final Map<Integer, ExceptionCodeEnum> cache = new HashMap<>();

    // 缓存数据为map,方便获取
    static {
        for (ExceptionCodeEnum exceptionCodeEnum : ExceptionCodeEnum.values()) {
            cache.put(exceptionCodeEnum.code, exceptionCodeEnum);
        }
    }

    public static String getDesc(Integer code) {
        return cache.get(code).desc;
    }

    public Integer getCode() {
        return code;
    }

    public String getDesc() {
        return desc;
    }
}
