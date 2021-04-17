package com.zgy.develop.common.enums;

/**
 * @author zgy
 * @data 2021/4/16 16:57
 */

public enum MySQLKeyword {
    SELECT(" SELECT "),
    WHERE(" WHERE "),
    OR(" OR "),
    AND(" AND "),
    BETWEEN(" BETWEEN "),
    INSERT(" INSERT "),
    UPDATE(" UPDATE "),
    DELETE(" DELETE "),
    INTO(" INTO "),
    VALUES(" VALUES ");

    public final String value;

    MySQLKeyword(String value) {
        this.value = value;
    }
}
