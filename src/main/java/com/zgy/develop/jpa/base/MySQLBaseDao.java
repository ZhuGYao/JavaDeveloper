package com.zgy.develop.jpa.base;

import com.zgy.develop.annotation.db.Column;
import com.zgy.develop.annotation.db.Table;
import com.zgy.develop.common.enums.MarksEnum;
import com.zgy.develop.common.enums.MySQLKeywordEnum;
import com.zgy.develop.pool.CustomDBPool;
import com.zgy.develop.pool.DBConnection;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 基础MySQL Dao类
 * @author zgy
 * @data 2021/4/15 14:46
 */

public class MySQLBaseDao<T> implements IBaseDao<T>{

    private CustomDBPool customDBPool = new CustomDBPool(10);
    //泛型参数的Class对象
    private Class<T> beanClass;

    public MySQLBaseDao() {
        // 得到泛型Class类对象
        beanClass = (Class) ((ParameterizedType) this.getClass().getGenericSuperclass()).getActualTypeArguments()[0];
    }

    @Override
    public Integer insert(T bean) {

        String table_name = beanClass.getName();
        // 查看是否自定义表名
        Table table = beanClass.getAnnotation(Table.class);
        if (table != null) {
            table_name = table.value();
        }

        Field[] fields = beanClass.getDeclaredFields();

        StringBuilder sql = new StringBuilder();

        sql.append(MySQLKeywordEnum.INSERT.value)
                .append(MySQLKeywordEnum.INTO.value)
                .append(table_name)
                .append(MarksEnum.LEFT_BRACKET.value);

        List<String> columnList = new ArrayList<>();
        List<String> valueList = new ArrayList<>();
        for (int i = 0; i < fields.length; i++) {
            String column = fields[i].getName();
            // 查看是否自定义列名
            Column annotation = fields[i].getAnnotation(Column.class);
            if (annotation != null) {
                column = annotation.value();
            }
            columnList.add(column);
            try {
                fields[i].setAccessible(true);
                Object o = fields[i].get(bean);
                if (o == null) {
                    valueList.add(null);
                    continue;
                }
                valueList.add(MarksEnum.QUOTATION.value + o.toString() + MarksEnum.QUOTATION.value);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }


        sql.append(String.join(MarksEnum.COMMA.value, columnList))
                .append(MarksEnum.RIGHT_BRACKET.value)
                .append(MySQLKeywordEnum.VALUES.value)
                .append(MarksEnum.LEFT_BRACKET.value)
                .append(String.join(MarksEnum.COMMA.value, valueList))
                .append(MarksEnum.RIGHT_BRACKET.value)
                .append(MarksEnum.SEMICOLON.value);

        System.out.println(sql.toString());
        Connection connection = customDBPool.getConnection();
        DBConnection.executeInsert(connection, sql.toString());
        customDBPool.releaseConnection(connection);
        return 1;
    }

    @Override
    public Integer delete(T bean) {
        return 0;
    }

    @Override
    public Integer updatePrimaryKey(T bean) {
        return 0;
    }

    @Override
    public Integer updatePrimaryKeySelective(T bean) {
        return null;
    }

    @Override
    public T selectByPrimaryKey(Integer id) {
        return null;
    }

    @Override
    public T selectOne(Map<String, Object> mapCondition) {
        return null;
    }

    @Override
    public List<T> selectAll() {
        return null;
    }
}
