package service;

import dao.StudentDao;
import dao.StudentCourseDao;
import dao.CourseDao;
import model.Student;
import model.Course;
import model.StudentCourse;

import java.util.List;

public class AdminService {
    private StudentDao studentDAO;
    private CourseDao courseDAO;
    private StudentCourseDao studentCourseDAO;

    public AdminService() {
        this.studentDAO = new StudentDao();
        this.courseDAO = new CourseDao();
        this.studentCourseDAO = new StudentCourseDao();
    }

    public List<Student> getAllStudents() {
        return studentDAO.getAllStudents();
    }

    public boolean updateStudentPhone(int studentId, String phone) {
        return studentDAO.updateStudentPhone(studentId, phone);
    }

    public List<StudentCourse> getStudentsByCourse(int courseId) {
        return studentCourseDAO.getStudentsByCourse(courseId);
    }

    public List<StudentCourse> getCoursesByStudent(int studentId) {
        return studentCourseDAO.getSelectedCourses(studentId);
    }

    public List<Course> getAllCourses() {
        return courseDAO.getAllCourses();
    }

    public Course getCourseById(int courseId) {
        return courseDAO.getCourseById(courseId);
    }

    public boolean addCourse(String name, int credits) {
        return courseDAO.addCourse(name, credits);
    }

    public boolean deleteCourse(int courseId) {
        return courseDAO.deleteCourse(courseId);
    }

    public Student getStudentById(int Id) {
        return studentDAO.getStudentById(Id);
    }
}