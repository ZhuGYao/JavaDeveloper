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
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

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

    private String tableName;

    public MySQLBaseDao() {
        // 得到泛型Class类对象
        beanClass = (Class) ((ParameterizedType) this.getClass().getGenericSuperclass()).getActualTypeArguments()[0];
        tableName = beanClass.getName();
        // 查看是否自定义表名
        Table table = beanClass.getAnnotation(Table.class);
        if (table != null) {
            tableName = table.value();
        }
    }

    @Override
    public Integer insert(T bean) {

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
                // 日期类型特殊处理
                if (fields[i].getType().getName().equals(Date.class.getName())) {
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
                    o = sdf.format(o);
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
    public T selectByPrimaryKey(Long id) {
        // TODO 暂时默认主键为ID
        StringBuilder sql = getCommonSelect();
        sql.append("id = ")
                .append(id)
                .append(MarksEnum.SEMICOLON.value);
        List list = executeQuery(sql.toString());

        return list != null && list.size() > 0 ? (T) list.get(0) : null;
    }

    @Override
    public T selectOne(CustomExample customExample) {

        List list = selectByCustomExample(customExample);

        return list != null && list.size() > 0 ? (T) list.get(0) : null;
    }

    @Override
    public List<T> selectList(CustomExample customExample) {
        return selectByCustomExample(customExample);
    }

    @Override
    public List<T> selectAll() {
        StringBuilder sql = getCommonSelect();
        sql.append(MySQLKeywordEnum.PLACE_IDENTITY.value)
                .append(MarksEnum.SEMICOLON.value);
        List list = executeQuery(sql.toString());
        return list;
    }

    /**
     * 公用根据条件查询
     * @param customExample
     * @return
     */
    private List<T> selectByCustomExample(CustomExample customExample) {
        CustomExample.CustomCriteria criteria = customExample.getCriteria();
        List<CustomExample.CustomCriterion> criterions = criteria.getCriteria();

        // 获取公共查取头部
        StringBuilder sql = getCommonSelect();

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
        return list;
    }

    /**
     * 公共查询头 TODO 可选查询字段
     * @return
     */
    private StringBuilder getCommonSelect() {
        StringBuilder sql = new StringBuilder();
        sql.append(MySQLKeywordEnum.SELECT.value)
                .append(MarksEnum.ALL_PROPERTY.value)
                .append(MySQLKeywordEnum.FROM.value)
                .append(tableName)
                .append(MySQLKeywordEnum.WHERE.value);
        return sql;
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
