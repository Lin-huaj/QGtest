package utils;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ConnectionPool {
        //连接数据库
        private static final String URL = "jdbc:mysql://localhost:3306/first_db";
        private static final String USER = "root";
        private static final String PASSWORD = "1234";
        //设置最大连接数
        private static final int POOL_SIZE = 10;

        //声明连接的列表容器
        private List<Connection> connections =new ArrayList<>();
        private List<Connection> availableConnections =new ArrayList<>();

        //将每个连接请求和连接池依次进行连接
        public ConnectionPool() throws SQLException {
            for (int i = 0; i < POOL_SIZE; i++) {
                Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
                connections.add(conn);
                availableConnections.add(conn);
            }
        }

        public synchronized Connection getConnection(){
            if(availableConnections.isEmpty()){
                try{
                    wait();
                }catch(InterruptedException e){
                    e.printStackTrace();
                }
            }
            Connection conn=availableConnections.remove(0);
            return conn;
        }

        public synchronized void releaseConnection(Connection conn){
            availableConnections.add(conn);
            notifyAll();
        }
        public void closeAllConnections(){
            for(Connection conn : connections){
                try {
                    conn.close();
                } catch (SQLException e){
                    e.printStackTrace();
                }
            }
        }
}
