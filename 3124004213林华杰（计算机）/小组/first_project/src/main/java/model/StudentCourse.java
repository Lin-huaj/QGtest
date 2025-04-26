package model;

public class StudentCourse {
    private int id;
    private int studentId;
    private int courseId;
    private int status; // 'SELECTED' 或 'DROPPED'

    // 构造方法、getter 和 setter
    public StudentCourse() {}

    public StudentCourse(int id,int studentId, int courseId, int status) {
        this.id = id;
        this.studentId = studentId;
        this.courseId = courseId;
        this.status = status;
    }

    // Getters and Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public int getStudentId() { return studentId; }
    public void setStudentId(int studentId) { this.studentId = studentId; }

    public int getCourseId() { return courseId; }
    public void setCourseId(int courseId) { this.courseId = courseId; }

    public int getStatus() { return status; }
    public void setStatus(int status) { this.status = status; }
}