package dao;

import model.User;
import utils.DBUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDao {
    public boolean register(String username, String password, int role) {
        String sql = "INSERT INTO user (username, password, role) VALUES (?, ?, ?)";
        return DBUtil.executeUpdate(sql, username, password, role) > 0;
    }

    public User login(String username, String password) {
        String sql = "SELECT * FROM user WHERE username = ? AND password = ?";
        ResultSet rs = DBUtil.executeQuery(sql, username, password);
        try {
            if (rs.next()) {
                return new User(rs.getInt("id"), rs.getString("username"), rs.getInt("role"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
