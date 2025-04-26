package com.pojo;

import com.annotation.Column;
import com.annotation.Id;
import com.annotation.Table;

@Table("admin")
public class Admin {
    @Id
    @Column(value="admin_id",isPrimaryKey=true,autoIncrement = true)
    private Integer adminId ;

    @Column("username")
    private String username  ;

    @Column("password")
    private String password  ;

    //getter and setter

    public Integer getAdminId() {
        return adminId;
    }

    public void setAdminId(Integer adminId) {
        this.adminId = adminId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "Admin{" +
                "adminId=" + adminId +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
