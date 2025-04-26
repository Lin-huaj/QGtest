package com.web;

import com.dao.UserDao;
import com.pojo.User;
import com.utils.ConnectionPool;

import java.sql.SQLException;

public class TestInsert {
    public static void main(String[] args) throws SQLException, IllegalAccessException {
        User user = new User();
        user.setNickname("test");
        user.setPassword("123");
        user.setEmail("test@example.com");
        user.setPhone("123456789");
        user.setStudentId("2023001"); // 需与数据库字段类型匹配
        user.setCreditScore(100);     // 同步修改为 Integer
        int result = new UserDao(ConnectionPool.getInstance()).insert(user);
        System.out.println("插入结果: " + result);
    }
}
