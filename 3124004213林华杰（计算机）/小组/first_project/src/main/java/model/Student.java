package model;

public class Student extends User {
    private int studentId;
    private int user_id;
    private String name;
    private String phone;

    // 构造方法、getter 和 setter
    public Student() {}

    public Student(int studentId, int user_id,String name, String phone) {
        this.studentId = studentId;
        this.user_id = user_id;
        this.name = name;
        this.phone = phone;
    }

    // Getters and Setters
    public int getStudentId() { return studentId; }
    public void setStudentId(int studentId) { this.studentId = studentId; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }
}