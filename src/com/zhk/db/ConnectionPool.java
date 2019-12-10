package com.zhk.db;

import java.sql.Connection;

/**
 * @author 赵洪苛
 * @date 2019/11/21 11:37
 * @description 数据库连接池方法接口，数据库连接池应实现此接口
 */
public interface ConnectionPool {

    /**
     * 从数据库连接池里取出connection
     * @return connection
     */
    Connection getConnection();

    /**
     * 把connection放回数据库连接池中
     * @param connection 数据库连接
     */
    void putBack(Connection connection);

    /**
     * 新增数据库连接
     * @param num 数据库连接数量
     */
    void addConnection(int num);

    /**
     * 删除数据库连接
     * @param num 数据里连接数量
     */
    void removeConnection(int num);

    /**
     * 关闭所有数据库连接
     */
    void close();

}
