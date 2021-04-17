package com.zgy.develop.common.enums;

/**
 * @author zgy
 * @data 2021/4/16 17:02
 */

public enum MarksEnum {

    SPACE(" "),
    COMMA(" , "),
    LEFT_BRACKET(" ( "),
    RIGHT_BRACKET(" ) "),
    QUESTION(" ? "),
    SEMICOLON(" ; "),
    QUOTATION("'");

    public final String value;

    MarksEnum(String value) {
        this.value = value;
    }
}
