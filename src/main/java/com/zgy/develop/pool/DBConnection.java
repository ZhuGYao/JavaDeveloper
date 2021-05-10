package com.zgy.develop.pool;

import com.zgy.develop.annotation.db.Column;

import java.lang.reflect.Field;
import java.sql.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author zgy
 * @data 2021/4/15 14:51
 */

public class DBConnection {

    private static String driver ="com.mysql.cj.jdbc.Driver";
    private static String url ="jdbc:mysql://localhost:3306/rbac?useUnicode=true&characterEncoding=UTF-8&zeroDateTimeBehavior=convertToNull&autoReconnect=true&failOverReadOnly=false&socketTimeout=300000&connectTimeout=30000";
    private static String user = "root";
    private static String password ="root";

    /**
     * 创建数据库连接
     * @return
     */
    public static Connection createConnection() {
        Connection connection = null;
        try {
            Class.forName(driver);
            connection = DriverManager.getConnection(url, user, password);
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
        return connection;
    }

    /**
     * 执行插入操作
     *
     * @param connection
     * @param insertSql
     */
    public static int executeInsert(Connection connection, String insertSql) {
        Statement statement = null;
        int count = 0;
        try {
            statement = connection.createStatement();
            count = statement.executeUpdate(insertSql);
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (statement != null) {
                try {
                    statement.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }

        }
        return count;
    }

    /**
     * 查询数据
     * @param connection
     * @param sql
     * @return
     */
    public static List executeQuery(Connection connection, String sql, Class clazz) {
        Statement statement = null;
        List list = new ArrayList();
        try {
            statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);
            list = getListResult(resultSet, clazz);
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } finally {
            if (statement != null) {
                try {
                    statement.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }

        }
        return list;
    }

    private static  <T> List<T> getListResult(ResultSet resultSet, Class beanClass) throws IllegalAccessException, InstantiationException, SQLException {

        List<T> list = new ArrayList<>();
        if (resultSet != null) {
            Field[] fields = beanClass.getDeclaredFields();
            while (resultSet.next()) {
                T t = (T) beanClass.newInstance();
                for (Field field : fields) {
                    field.setAccessible(true);
                    Column annotation = field.getAnnotation(Column.class);
                    String dbFiledName = field.getName();
                    if (annotation != null && !"".equals(annotation.value())) {
                        dbFiledName = annotation.value();
                    }
                    if (field.getType().getSimpleName().equalsIgnoreCase(String.class.getSimpleName())) {
                        field.set(t, resultSet.getString(dbFiledName));
                    } else if (field.getType().getSimpleName().equals(int.class.getSimpleName()) ||
                            field.getType().getSimpleName().equals(Integer.class.getSimpleName())) {
                        field.set(t, resultSet.getInt(dbFiledName));
                    } else if (field.getType().getSimpleName().equalsIgnoreCase(long.class.getSimpleName())) {
                        field.set(t, resultSet.getLong(dbFiledName));
                    } else if (field.getType().getSimpleName().equalsIgnoreCase(Date.class.getSimpleName())) {
                        field.set(t, resultSet.getDate(dbFiledName));
                    }
                }
                list.add(t);
            }
        }

        return list;
    }
}
