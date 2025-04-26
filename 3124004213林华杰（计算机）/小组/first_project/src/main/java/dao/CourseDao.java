package dao;

import model.Course;
import utils.DBUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CourseDao {
    public List<Course> getAllCourses() {
        String sql = "SELECT * FROM courses";
        ResultSet rs = DBUtil.executeQuery(sql);
        List<Course> courses = new ArrayList<>();
        try {
            while (rs.next()) {
                courses.add(new Course(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getInt("credits")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return courses;
    }

    public boolean addCourse(String name, int credits) {
        String sql = "INSERT INTO courses (name, credits, status) VALUES (?, ?, 1)";
        return DBUtil.executeUpdate(sql, name, credits) > 0;
    }

    public boolean deleteCourse(int courseId) {
        String sql = "DELETE FROM courses WHERE id = ?";
        return DBUtil.executeUpdate(sql, courseId) > 0;
    }

    public Course getCourseById(int courseId) {
        String sql = "SELECT * FROM courses WHERE id = ?";
        ResultSet rs = DBUtil.executeQuery(sql, courseId);
        try {
            if (rs.next()) {
                return new Course(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getInt("credits")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}