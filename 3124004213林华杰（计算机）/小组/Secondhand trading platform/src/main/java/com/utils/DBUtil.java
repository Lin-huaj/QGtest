//package com.utils;
//
//import com.alibaba.druid.pool.DruidDataSource;
//import javax.sql.DataSource;
//import java.sql.Connection;
//import java.sql.ResultSet;
//import java.sql.SQLException;
//import java.sql.Statement;
//
//// DbUtil.java
//public class DBUtil {
//    private static DataSource dataSource;
//
//    static {
//        // 使用Druid连接池示例
//        DruidDataSource ds = new DruidDataSource();
//        ds.setUrl("jdbc:mysql://localhost:3306/qgmarket?useSSL=false&characterEncoding=utf8");
//        ds.setUsername("root");
//        ds.setPassword("123456");
//        ds.setInitialSize(5);
//        ds.setMaxActive(10);
//        dataSource = ds;
//    }
//
//    public static Connection getConnection() throws SQLException {
//        return dataSource.getConnection();
//    }
//
//    public static void close(Connection conn, Statement stmt, ResultSet rs) {
//        try {
//            if (rs != null) rs.close();
//            if (stmt != null) stmt.close();
//            if (conn != null) conn.close();
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//    }
//}