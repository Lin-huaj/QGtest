package dao;

import model.StudentCourse;
import utils.DBUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class StudentCourseDao {
    public boolean selectCourse(int studentId, int courseId) {
        String sql = "INSERT INTO student_courses (student_id, course_id, status) VALUES (?, ?, 1)";
        return DBUtil.executeUpdate(sql, studentId, courseId) > 0;
    }

    public boolean dropCourse(int studentId, int courseId) {
        String sql = "UPDATE student_courses SET status = 0 WHERE student_id = ? AND course_id = ?";
        return DBUtil.executeUpdate(sql, studentId, courseId) > 0;
    }

    public List<StudentCourse> getSelectedCourses(int studentId) {
        String sql = "SELECT * FROM student_courses WHERE student_id = ? AND status = 1";
        ResultSet rs = DBUtil.executeQuery(sql, studentId);
        List<StudentCourse> studentCourses = new ArrayList<>();
        try {
            while (rs.next()) {
                studentCourses.add(new StudentCourse(
                        rs.getInt("id"),
                        rs.getInt("student_id"),
                        rs.getInt("course_id"),
                        rs.getInt("status")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return studentCourses;
    }

    public List<StudentCourse> getStudentsByCourse(int courseId) {
        String sql = "SELECT * FROM student_courses WHERE course_id = ? AND status = 1";
        ResultSet rs = DBUtil.executeQuery(sql, courseId);
        List<StudentCourse> studentCourses = new ArrayList<>();
        try {
            while (rs.next()) {
                studentCourses.add(new StudentCourse(
                        rs.getInt("id"),
                        rs.getInt("student_id"),
                        rs.getInt("course_id"),
                        rs.getInt("status")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return studentCourses;
    }
}