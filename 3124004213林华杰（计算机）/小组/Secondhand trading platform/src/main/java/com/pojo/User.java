package com.pojo;

import com.annotation.Column;
import com.annotation.Id;
import com.annotation.Table;

@Table("user")
public class User {
    @Id
    @Column(value = "user_id",isPrimaryKey=true,autoIncrement = true)
    private Integer userId     ;

    @Column("nickname")
    private String nickname    ;

    @Column("phone")
    private String phone       ;

    @Column("student_id")
    private String studentId  ;

    @Column("avatar")
    private String avatar      ;

    @Column("email")
    private String email        ;

    @Column("password")
    private String password     ;

    @Column("credit_score")
    private Integer creditScore    ;

    //getter and setter
    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Integer getCreditScore() {
        return creditScore;
    }

    public void setCreditScore(Integer creditScore) {
        this.creditScore = creditScore;
    }

    @Override
    public String toString() {
        return "User{" +
                "userId=" + userId +
                ", nickname='" + nickname + '\'' +
                ", phone='" + phone + '\'' +
                ", studentId=" + studentId +
                ", avatar='" + avatar + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", creditScore=" + creditScore +
                '}';
    }
}
