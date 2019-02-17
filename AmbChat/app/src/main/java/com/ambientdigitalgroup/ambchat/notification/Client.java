package com.ambientdigitalgroup.ambchat.notification;

import retrofit.GsonConverterFactory;
import retrofit.Retrofit;

public class Client {
    private static Retrofit retrofit=null;

    public static Retrofit getClient(String url) {
        if(retrofit==null){
            retrofit=new Retrofit.Builder()
                    .baseUrl(url)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }
}
