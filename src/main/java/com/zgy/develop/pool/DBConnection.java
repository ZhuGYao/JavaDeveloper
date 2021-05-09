package com.zgy.develop.pool;

import java.sql.*;

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
}
