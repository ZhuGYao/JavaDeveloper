package com.zgy.develop.spring.common.enums;

/**
 * @author zgy
 * @data 2021/4/26 23:10
 */

public enum  CommonEnums {

    SEPARATE("/", "分隔符");

    public final String value;
    public final String type;

    CommonEnums(String value, String type) {
        this.type = type;
        this.value = value;
    }
}
