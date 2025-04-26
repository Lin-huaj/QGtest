package service;

import dao.CourseDao;
import dao.StudentDao;
import dao.StudentCourseDao;
import model.Course;
import model.Student;
import model.StudentCourse;

import java.util.List;

public class StudentService {
    private StudentDao studentDAO;
    private StudentCourseDao studentCourseDAO;
    private CourseDao courseDAO;

    public StudentService() {
        this.studentDAO = new StudentDao();
        this.studentCourseDAO = new StudentCourseDao();
    }

    public List<StudentCourse> getSelectedCourses(int studentId) {
        return studentCourseDAO.getSelectedCourses(studentId);
    }

    public boolean selectCourse(int studentId, int courseId) {
        return studentCourseDAO.selectCourse(studentId, courseId);
    }

    public boolean dropCourse(int studentId, int courseId) {
        return studentCourseDAO.dropCourse(studentId, courseId);
    }

    public Student getStudentByUserId(int userId) {
        return studentDAO.getStudentByUserId(userId);
    }

    public boolean updateStudentPhone(int studentId, String phone) {
        return studentDAO.updateStudentPhone(studentId, phone);
    }

    public Course getCourseById(int courseId) {
        return courseDAO.getCourseById(courseId);
    }

}