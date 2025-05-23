package model;

public class User {
    private int id;
    private String username;
    private String password;
    private int role;

    public User() {}

    public User(int id,String username,  int role) {
        this.id = id;
        this.username = username;
        this.role = role;
    }


    //setter and getter
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public int getRole() { return role; }
    public void setRole(int role) { this.role = role; }

}
