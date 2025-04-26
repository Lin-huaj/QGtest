package com.service;

import com.pojo.User;
import com.dao.UserDao;
import com.utils.ConnectionPool;

import java.sql.SQLException;
import java.util.Collections;
import java.util.List;


public class UserService {
    private ConnectionPool pool = ConnectionPool.getInstance();  // 正确获取单例
    private UserDao userDao = new UserDao(pool);

    public User login(String username, String password)throws SQLException {
        List<User> user= userDao.selectByUP(username,password);
        return user.isEmpty()?null:user.get(0);
    }

    // 更新信用评分
    public boolean updateUserCredit(int userId, int newScore) {
        try {
            int rows = userDao.updateCreditScore(userId, newScore);
            return rows > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // 获取高信用用户（示例）
    public List<User> getHighCreditUsers() {
        try {
            return userDao.findUsersByCreditRange(80, 100);
        } catch (SQLException e) {
            e.printStackTrace();
            return Collections.emptyList();
        }
    }

    // 检查用户名是否存在
    public List<User> findByUsername(String username) throws SQLException {
        return userDao.findByUsername(username);
    }

    // 添加用户
    public int addUser(User user) throws SQLException, IllegalAccessException {
        System.out.println("UserService success");
        return userDao.insert(user);
    }
}
