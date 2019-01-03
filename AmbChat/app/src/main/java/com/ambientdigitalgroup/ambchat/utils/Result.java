package com.ambientdigitalgroup.ambchat.utils;

import com.google.gson.annotations.SerializedName;

public class Result {
    @SerializedName("error")
    private int error;
    @SerializedName("message")
    private String message;
    private Object data;
    @SerializedName("token")
    private String token;


    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Result(int error, String message, String token) {

        this.error = error;
        this.message = message;
        this.token = token;
    }

    public int getError() {
        return error;
    }

    public void setError(int error) {
        this.error = error;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

}
