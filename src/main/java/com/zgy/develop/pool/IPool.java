package com.zgy.develop.pool;

import java.sql.Connection;

/**
 * @author zgy
 * @data 2021/4/15 16:27
 */

public interface IPool {

    /**
     * 连接池初始化
     */
    void init();

    /**
     * 获取一个连接
     * @return
     */
    Connection getConnection();

    /**
     * 释放一个连接
     * @param connection
     */
    void releaseConnection(Connection connection);

    /**
     * 销毁连接池
     */
    void destroy();

}
