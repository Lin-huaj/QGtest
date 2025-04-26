package com.dao;

import com.annotation.Column;
import com.pojo.User;
import com.utils.*;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

// UserDao.java
public class UserDao extends BaseDao<User> {
    public UserDao(ConnectionPool pool) {
        super(pool, User.class);
    }

    public User findByEmail(String email) throws SQLException {
        String sql = "SELECT * FROM user WHERE email = ?";
        List<Object> params = new ArrayList<>();
        params.add(email);
        List<User> users = executeQuery(sql, params);
        return users.isEmpty() ? null : users.get(0);
    }

    public List<User> findUsersByStudentId(String studentId) throws SQLException {
        String sql = "SELECT * FROM user WHERE credit_score >= ?";
        return executeQuery(sql, Collections.singletonList(studentId));
    }

    //注册和登录
    public List<User> selectByUsername(String nickname) throws SQLException {
        String sql = "SELECT * FROM user WHERE nickname = ?";
        return executeQuery(sql,Collections.singletonList(nickname));
    }

    public List<User> selectByUP(String nickname,String password) throws SQLException {
        String sql = "SELECT * FROM user WHERE nickname = ? and password = ?";
        List<Object> params = new ArrayList<>();
        params.add(nickname);
        params.add(password);

        return executeQuery(sql,params);
    }

    public int addUser(User user) throws SQLException, IllegalAccessException {
        return insert(user);
    }

    // 更新用户信用评分
    public int updateCreditScore(int userId, int newScore) throws SQLException {
        String sql = "UPDATE user SET credit_score = ? WHERE user_id = ?";
        List<Object> params = new ArrayList<>();
        params.add(newScore);
        params.add(userId);
        return executeUpdate(sql, params);
    }

    // 根据信用范围查询用户（用于信用管理）
    public List<User> findUsersByCreditRange(int min, int max) throws SQLException {
        String sql = "SELECT * FROM user WHERE credit_score BETWEEN ? AND ?";
        List<Object> params = new ArrayList<>();
        params.add(min);
        params.add(max);
        return executeQuery(sql, params);
    }

    public List<User> findByUsername(String username) throws SQLException {
        String sql = "SELECT * FROM user WHERE nickname = ?";
        return executeQuery(sql, Collections.singletonList(username));
    }

    // 新增方法：更新用户状态
    public int updateStatus(int userId, String status) throws SQLException {
        String sql = "UPDATE user SET status = ? WHERE user_id = ?";
        List<Object> params = new ArrayList<>();
        params.add(status);
        params.add(userId);
        return executeUpdate(sql, params);
    }

}