package com.zgy.develop.jpa.common;

import com.zgy.develop.annotation.db.Column;
import com.zgy.develop.annotation.db.Table;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.util.List;

/**
 * 基础MySQL Dao类
 * @author zgy
 * @data 2021/4/15 14:46
 */

public class MySQLBaseDao<T> implements IBaseDao<T>{

    //泛型参数的Class对象
    private Class<T> beanClass;

    public MySQLBaseDao() {
        // 得到泛型Class类对象
        beanClass = (Class) ((ParameterizedType) this.getClass().getGenericSuperclass()).getActualTypeArguments()[0];
    }

    @Override
    public Integer insert(T bean) {

        String table_name = beanClass.getAnnotation(Table.class).value();
        Field[] fields = beanClass.getDeclaredFields();

        String sql = "insert into "
                + table_name + "(";
        for (int i = 0; i < fields.length; i++) {
            String column = fields[i].getAnnotation(Column.class).value();
            sql += column;
            if (i < fields.length - 1) {
                sql += ",";
            }
        }
        sql += ") values(";
        for (int i = 0; i < fields.length; i++) {
            try {
                fields[i].setAccessible(true);
                Object o = fields[i].get(bean);
                sql += o.toString();
                if (i < fields.length - 1) {
                    sql += ",";
                }
            } catch (Exception e) {

            }

        }
        sql += ")";

        System.out.println(sql);
        return 1;
    }

    @Override
    public Integer delete(T bean) {
        return 0;
    }

    @Override
    public Integer update(T bean) {
        return 0;
    }

    @Override
    public T selectOne(Integer id) {
        return null;
    }

    @Override
    public List<T> selectAll() {
        return null;
    }
}
