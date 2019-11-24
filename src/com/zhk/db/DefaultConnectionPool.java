package com.zhk.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.LinkedList;

/**
 * @author 赵洪苛
 * @date 2019/11/21 11:40
 * @description 默认数据库连接池
 */
public class DefaultConnectionPool implements ConnectionPool {

    /**
     * 最大数据库连接数量
     */
    private static final int MAX_CONNECTION_COUNT = 10;

    /**
     * 默认数据库连接数量
     */
    private static final int DEFAULT_CONNECTION_COUNT = 3;

    /**
     * 最小数据库连接数量
     */
    private static final int MIN_CONNECTION_COUNT = 1;

    /**
     * 连接池
     */
    private final LinkedList<Connection> pool = new LinkedList<>();

    private String SQLDriver = "com.mysql.cj.jdbc.Driver";

    private String url = "jdbc:mysql://localhost/final_work?user=root&password=123456&useUnicode=true&characterEncoding=utf-8&serverTimezone=GMT";

    private int connectionCount = 0;

    public DefaultConnectionPool() {
        this(DEFAULT_CONNECTION_COUNT);
    }

    public DefaultConnectionPool(int num) {
        if (num > MAX_CONNECTION_COUNT) {
            throw new IllegalArgumentException("数据库连接池连接数量最大不能超过：" + MAX_CONNECTION_COUNT);
        } else if (num < MIN_CONNECTION_COUNT) {
            throw new IllegalArgumentException("数据库连接池连接数量最小不能小于：" + MIN_CONNECTION_COUNT);
        }

        initConnection(num);
    }

    private void initConnection(int num) {
        for (int i = 0; i < num; ++i) {
            try {
                Class.forName(SQLDriver);
                Connection connection = DriverManager.getConnection(url);
                if (null != connection) {
                    pool.add(connection);
                    ++connectionCount;
                }
            } catch (SQLException | ClassNotFoundException e) {

            }
        }
    }

    @Override
    public Connection getConnection() {
        if (pool.size() <= 0) {
            connectionCount = 0;
            initConnection(DEFAULT_CONNECTION_COUNT);
        }
        Connection connection;
        synchronized (pool) {
            connection = pool.removeFirst();
            --connectionCount;
        }
        return connection;
    }

    @Override
    public void putBack(Connection connection) {
        synchronized (pool) {
            pool.addLast(connection);
            ++connectionCount;
        }
    }

    @Override
    public void addConnection(int num) {
        if (num + connectionCount > MAX_CONNECTION_COUNT) {
            throw new IllegalArgumentException("数据库连接池连接数量最大不能超过：" + MAX_CONNECTION_COUNT);
        }
        synchronized (pool) {
            initConnection(num);
        }
    }

    @Override
    public void removeConnection(int num) {
        if (connectionCount - num < MIN_CONNECTION_COUNT) {
            throw new IllegalArgumentException("数据库连接池最小数量不能小于：" + MIN_CONNECTION_COUNT);
        }
        synchronized (pool) {
            try {
                for (int i = 0; i < num; ++i) {
                    pool.removeFirst().close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void close() {
        if (pool.size() == 0 || connectionCount == 0) {
            return;
        }
        synchronized (pool) {
            pool.forEach(connection -> {
                try {
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            });
            pool.clear();
        }
    }
}
