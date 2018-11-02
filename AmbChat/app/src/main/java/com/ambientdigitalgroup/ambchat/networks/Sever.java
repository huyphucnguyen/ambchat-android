package com.ambientdigitalgroup.ambchat;

public class Sever {
    private static String url="https://ambchat.herokuapp.com/api/";

    public static String getUrl() {
        return url;
    }

    public static void setUrl(String url) {
        Sever.url = url;
    }
}
