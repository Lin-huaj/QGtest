package dao;

import model.Student;
import utils.DBUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class StudentDao {
    public List<Student> getAllStudents() {
        String sql = "SELECT s.*, u.username FROM students s JOIN users u ON s.user_id = u.id";
        ResultSet rs = DBUtil.executeQuery(sql);
        List<Student> students = new ArrayList<>();
        try {
            while (rs.next()) {
                students.add(new Student(
                        rs.getInt("id"),
                        rs.getInt("user_id"),
                        rs.getString("name"),
                        rs.getString("phone")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return students;
    }

    public boolean updateStudentPhone(int studentId, String phone) {
        String sql = "UPDATE students SET phone = ? WHERE id = ?";
        return DBUtil.executeUpdate(sql, phone, studentId) > 0;
    }

    public Student getStudentByUserId(int userId) {
        String sql = "SELECT * FROM students WHERE user_id = ?";
        ResultSet rs = DBUtil.executeQuery(sql, userId);
        try {
            if (rs.next()) {
                return new Student(
                        rs.getInt("id"),
                        rs.getInt("user_id"),
                        rs.getString("name"),
                        rs.getString("phone")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
    public Student getStudentById(int studentId) {
        String sql = "SELECT * FROM students WHERE id = ?";
        ResultSet rs = DBUtil.executeQuery(sql, studentId);
        try {
            if (rs.next()) {
                return new Student(
                        rs.getInt("id"),
                        rs.getInt("user_id"),
                        rs.getString("name"),
                        rs.getString("phone")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}