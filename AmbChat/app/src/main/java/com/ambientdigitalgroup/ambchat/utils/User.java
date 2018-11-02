package com.ambientdigitalgroup.ambchat.utils;

public class User {
    private int user_id;
    private String user_name;
    private String full_name;

    public User(int user_id, String user_name, String full_name) {
        this.user_id = user_id;
        this.user_name = user_name;
        this.full_name = full_name;
    }

    public int getUser_id() {
        return user_id;
    }

    public String getUser_name() {
        return user_name;
    }

    public String getFull_name() {
        return full_name;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public void setFull_name(String full_name) {
        this.full_name = full_name;
    }
}
