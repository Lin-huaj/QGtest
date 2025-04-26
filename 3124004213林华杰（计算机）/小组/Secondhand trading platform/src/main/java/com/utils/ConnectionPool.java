package com.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.LinkedList;

public class ConnectionPool {
    //设置最大连接数
    private static final int INIT_POOL_SIZE = 10;
    //连接mysql
    private static final String URL = "jdbc:mysql://localhost:3306/sh_db?useSSL=false&allowPublicKeyRetrieval=true&characterEncoding=utf8";
    private static final String USER = "root";
    private static final String PASSWORD = "1234";

    private final LinkedList<Connection> pool = new LinkedList<>();

    // 单例模式
    private static class Holder {
        private static final ConnectionPool INSTANCE = new ConnectionPool();
    }

    //初始化连接池
    private ConnectionPool() {
        initPool();
    }

    //获取实例
    public static ConnectionPool getInstance() {
        return Holder.INSTANCE;
    }

    //将请求依次加入连接池
    private void initPool() {
        try {
            for (int i = 0; i < INIT_POOL_SIZE; i++) {
                Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
                pool.add(conn);
            }
        } catch (SQLException e) {
            throw new RuntimeException("数据库连接池初始化失败", e);
        }
    }

    //获取连接
    public synchronized Connection getConnection() {
        while (pool.isEmpty()) {
            try {
                wait();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
        return pool.removeFirst();
    }

    //释放连接
    public synchronized void releaseConnection(Connection conn) {
        if (conn != null) {
            pool.addLast(conn);
            notifyAll();
        }
    }
}