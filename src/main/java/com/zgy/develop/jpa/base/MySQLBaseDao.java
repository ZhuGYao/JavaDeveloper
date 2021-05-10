package com.zgy.develop.jpa.base;

import com.zgy.develop.annotation.db.Column;
import com.zgy.develop.annotation.db.Table;
import com.zgy.develop.common.enums.MarksEnum;
import com.zgy.develop.common.enums.MySQLKeywordEnum;
import com.zgy.develop.pool.CustomDBPool;
import com.zgy.develop.pool.DBConnection;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 基础MySQL Dao类
 *
 * @author zgy
 * @data 2021/4/15 14:46
 */

@Slf4j
public class MySQLBaseDao<T> implements IBaseDao<T> {

    private CustomDBPool customDBPool = new CustomDBPool(10);
    //泛型参数的Class对象
    private Class<T> beanClass;

    public MySQLBaseDao() {
        // 得到泛型Class类对象
        beanClass = (Class) ((ParameterizedType) this.getClass().getGenericSuperclass()).getActualTypeArguments()[0];
    }

    @Override
    public Integer insert(T bean) {

        String tableName = beanClass.getName();
        // 查看是否自定义表名
        Table table = beanClass.getAnnotation(Table.class);
        if (table != null) {
            tableName = table.value();
        }

        Field[] fields = beanClass.getDeclaredFields();

        StringBuilder sql = new StringBuilder();

        sql.append(MySQLKeywordEnum.INSERT.value)
                .append(MySQLKeywordEnum.INTO.value)
                .append(tableName)
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

        return executeInsert(sql.toString());
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
    public T selectOne(CustomExample customExample) {

        CustomExample.CustomCriteria criteria = customExample.getCriteria();
        String tableName = customExample.getTableName();
        List<CustomExample.CustomCriterion> criterions = criteria.getCriteria();

        StringBuilder sql = new StringBuilder();

        sql.append(MySQLKeywordEnum.SELECT.value)
                .append(MarksEnum.ALL_PROPERTY.value)
                .append(MySQLKeywordEnum.FROM.value)
                .append(tableName)
                .append(MySQLKeywordEnum.WHERE.value);

        for (CustomExample.CustomCriterion criterion : criterions) {
            String condition = criterion.getCondition();
            Object value = criterion.getValue();
            sql.append(condition)
                    .append(value)
                    .append(MySQLKeywordEnum.AND.value);
        }

        sql.append(MySQLKeywordEnum.PLACE_IDENTITY.value);
        sql.append(MarksEnum.SEMICOLON.value);

        List list = executeQuery(sql.toString());
        return (T) list.get(0);
    }

    @Override
    public List<T> selectAll() {
        return null;
    }

    /**
     * 公用更新插入
     * @param sql
     * @return
     */
    private int executeInsert(String sql) {
        log.info("executeInsert:{}", sql);
        Connection connection = customDBPool.getConnection();
        int count = DBConnection.executeInsert(connection, sql);
        customDBPool.releaseConnection(connection);
        return count;
    }

    /**
     * 公用查询
     * @param sql
     * @return
     */
    private List executeQuery(String sql) {
        log.info("executeQuery:{}", sql);
        Connection connection = customDBPool.getConnection();
        List list = DBConnection.executeQuery(connection, sql, beanClass);
        customDBPool.releaseConnection(connection);
        return list;
    }
}
