package com.ambientdigitalgroup.ambchat.utils;

public class User {

    private int user_id;
    private String user_name;



    private String full_name;
    private String status;
    private String email;
    private int gender;
    private String phone;

    //constructor
    public User(int user_id, String user_name, String full_name, String status, String email, int gender, String phone) {
        this.user_id = user_id;
        this.user_name = user_name;
        this.full_name = full_name;
        this.status = status;
        this.email = email;
        this.gender = gender;
        this.phone = phone;
    }

    //get ter
    public int getUser_id() {
        return user_id;
    }

    public String getUser_name() {
        return user_name;
    }

    public String getFull_name() {
        return full_name;
    }

    public String getStatus() {
        return status;
    }

    public String getEmail() {
        return email;
    }

    public int getGender() {
        return gender;
    }

    public String getPhone() {
        return phone;
    }

    //set ter
    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public void setFull_name(String full_name) {
        this.full_name = full_name;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setGender(int gender) {
        this.gender = gender;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
