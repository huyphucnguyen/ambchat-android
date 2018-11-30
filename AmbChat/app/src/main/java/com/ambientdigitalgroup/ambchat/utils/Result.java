package com.ambientdigitalgroup.ambchat.utils;

import com.google.gson.annotations.SerializedName;

public class Result {
    @SerializedName("error")
    private int error;
    @SerializedName("message")
    private String message;
    Object data;

    public Result(int error, String message) {

        this.error = error;
        this.message = message;
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
