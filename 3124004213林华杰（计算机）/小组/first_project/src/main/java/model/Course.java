package model;

public class Course {
    private int id;
    private String name;
    private int credit;
    private String status; // 'OPEN' 或 'CLOSED'

    // 构造方法、getter 和 setter
    public Course() {}

    public Course(int id, String name, int credit) {
        this.id = id;
        this.name = name;
        this.credit = credit;
    }

    // Getters and Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public int getCredit() { return credit; }
    public void setCredit(int credit) { this.credit = credit; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
}