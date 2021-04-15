package com.zgy.develop.pool;

import java.sql.Connection;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 *
 * 自定义数据库连接池
 * @author zgy
 * @data 2021/4/15 16:55
 */

public class CustomDBPool implements IPool{

    /**
     * 空闲连接池
     */
    private LinkedBlockingQueue<Connection> freePool;

    /**
     * 活跃连接池
     */
    private LinkedBlockingQueue<Connection> busyPool;

    /**
     * 当前正在被使用的连接数
     */
    private AtomicInteger activeSize = new AtomicInteger(0);

    /**
     * 最大连接数
     */
    private final int maxSize;

    public CustomDBPool(int maxSize) {
        this.maxSize = maxSize;
        init();
    }


    @Override
    public void init() {
        freePool = new LinkedBlockingQueue<>();
        busyPool = new LinkedBlockingQueue<>();
    }

    @Override
    public Connection getConnection() {

        // 从idle池中取出一个连接
        Connection connection = freePool.poll();
        if (connection != null) {
            // 如果有连接，则放入busy池中
            busyPool.offer(connection);
            return connection;
        }

        // 如果idle池中连接未满maxSize，就新建一个连接
        if (activeSize.get() < maxSize) {
            // 如果增加后还没有超过最大连接数,创建新连接
            if (activeSize.incrementAndGet() <= maxSize) {
                connection = DBConnection.createConnection();
                // 放入busy池中
                busyPool.offer(connection);
                return connection;
            }
        }

        try {
            // 如果空闲池中连接数达到maxSize， 则阻塞等待归还连接,阻塞获取连接，如果8秒内有其他连接释放
            connection = freePool.poll(8000, TimeUnit.MILLISECONDS);
            if (connection == null) {
                throw new RuntimeException("等待连接超时");
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return connection;
    }

    @Override
    public void releaseConnection(Connection connection) {
        if (connection != null) {
            busyPool.remove(connection);
            freePool.offer(connection);
        }
    }

    @Override
    public void destroy() {

        try {
            while (freePool.peek() != null) {
                Connection conn = freePool.poll();
                conn.close();
            }
            while (busyPool.peek() != null) {
                Connection conn = busyPool.poll();
                conn.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
