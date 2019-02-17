package com.ambientdigitalgroup.ambchat.fragments;

import com.ambientdigitalgroup.ambchat.notification.MyResponses;
import com.ambientdigitalgroup.ambchat.notification.Sender;

import retrofit.Call;
import retrofit.http.Body;
import retrofit.http.Header;
import retrofit.http.Headers;
import retrofit.http.POST;

public interface APIService {
    @Headers(
            {
                    "Conten-Type:application/json",
                    "Authorization:key=AAAAcEvRAgI:APA91bEYss_1nNoJjUWaOILYPuKUeyrcfCjXOf2gT4Z_Bi_k71CzMSNZNOwcNnabxLfqn71dN4lcBL4gFgIwYOvvXuWCFUFV5tBxnyWYj1XCEJoDOYqWhOHyvq3xdSR4CwglxM1mAORR"
            }
    )
    @POST("fcm/send")
    Call<MyResponses> setNotification(@Body Sender body);

}
