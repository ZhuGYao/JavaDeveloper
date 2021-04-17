package com.zgy.develop.common.enums;

/**
 * @author zgy
 * @data 2021/4/16 17:02
 */

public enum Marks {

    SPACE(" "),
    COMMA(" , "),
    LEFT_BRACKET(" ( "),
    RIGHT_BRACKET(" ) "),
    QUESTION(" ? "),
    SEMICOLON(" ; "),
    QUOTATION("'");

    public final String value;

    Marks(String value) {
        this.value = value;
    }
}
