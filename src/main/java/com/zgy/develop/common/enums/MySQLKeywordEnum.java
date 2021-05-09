package com.zgy.develop.common.enums;

/**
 * @author zgy
 * @data 2021/4/16 16:57
 */

public enum MySQLKeywordEnum {
    SELECT(" SELECT "),
    FROM(" from "),
    WHERE(" WHERE "),
    OR(" OR "),
    AND(" AND "),
    BETWEEN(" BETWEEN "),
    INSERT(" INSERT "),
    UPDATE(" UPDATE "),
    DELETE(" DELETE "),
    INTO(" INTO "),
    VALUES(" VALUES "),
    PLACE_IDENTITY(" 1 = 1 ");

    public final String value;

    MySQLKeywordEnum(String value) {
        this.value = value;
    }
}
