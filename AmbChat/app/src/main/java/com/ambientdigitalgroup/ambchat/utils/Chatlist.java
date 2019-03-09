package com.ambientdigitalgroup.ambchat.utils;

public class Chatlist {
    public String id;
    public  String last_mess;

    public Chatlist(String id,String last_mess) {
        this.id = id;
        this.last_mess=last_mess;
    }

    public Chatlist() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLast_mess() {
        return last_mess;
    }

    public void setLast_mess(String id) {
        this.last_mess = last_mess;
    }
}
