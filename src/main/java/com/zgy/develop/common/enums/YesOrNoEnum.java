package com.zgy.develop.common.enums;

/**
 * @Desc: 是否 枚举
 */
public enum YesOrNoEnum {
    NO(0, "否"),
    YES(1, "是");

    public final Integer value;
    public final String type;

    YesOrNoEnum(Integer value, String type) {
        this.type = type;
        this.value = value;
    }
}
