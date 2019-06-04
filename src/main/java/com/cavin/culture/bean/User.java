package com.cavin.culture.bean;

import java.util.Date;

public class User {

    private String userName;
    private String userPassword;
    private String email;
    private Date registerDate;

    public User() {
    }

    public User(String userName, String userPassword, String email, Date registerDate) {
        this.userName = userName;
        this.userPassword = userPassword;
        this.email = email;
        this.registerDate = registerDate;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserPassword() {
        return userPassword;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Date getRegisterDate() {
        return registerDate;
    }

    public void setRegisterDate(Date registerDate) {
        this.registerDate = registerDate;
    }

}
