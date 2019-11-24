package com.zhk.db;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * @author 赵洪苛
 * @date 2019/11/21 17:29
 * @description 单例模式，数据库连接池单例
 */
public enum ConnectionPoolEnum {

    /**
     * 数据库连接池单例实例对象
     */
    INSTANCE;

    private ConnectionPool pool = new DefaultConnectionPool();

    /**
     * 获取数据库连接池单例实例对象
     * @return 数据库连接池单例实例对象
     */
    public static ConnectionPoolEnum getInstance() {
        return INSTANCE;
    }

    /**
     * @see ConnectionPool#close()
     */
    public void close() {
        pool.close();
    }

    /**
     * @see ConnectionPool#getConnection()
     * @return 连接
     */
    public Connection getConnection() {
        return pool.getConnection();
    }

    /**
     * @see ConnectionPool#putBack(Connection)
     * <p>用户千万不要将已经关闭的连接再放回连接池中</p>
     * @param connection 连接
     */
    public void putBack(Connection connection) {
        if (connection == null) {
            return;
        }

        try {
            if (connection.isClosed()) {
                return;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        pool.putBack(connection);
    }

    /**
     * @see ConnectionPool#addConnection(int)
     * @param num 新增数量
     */
    public void addConnection(int num) {
        pool.addConnection(num);
    }

    /**
     * @see ConnectionPool#removeConnection(int)
     * @param num 减少的数量
     */
    public void removeConnection(int num) {
        pool.removeConnection(num);
    }
}
