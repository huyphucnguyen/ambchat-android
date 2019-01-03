package com.ambientdigitalgroup.ambchat.networks;

import com.ambientdigitalgroup.ambchat.utils.ProfileUser;
import com.ambientdigitalgroup.ambchat.utils.Result;
import com.google.gson.Gson;

import org.json.JSONObject;

import java.util.Map;

import okhttp3.MultipartBody;
import okhttp3.Request;
import okhttp3.RequestBody;

public class CheckUserOnlineRequest extends SeverRequest {
    public CheckUserOnlineRequest(SeverRequestListener listener) {
        super(listener);
    }

    @Override
    protected Request prepare(Map<String, String> parameter) {
        String token = parameter.get("TOKEN");

        RequestBody requestBody = new MultipartBody.Builder()
                .addFormDataPart("TOKEN", token)
                .setType(MultipartBody.FORM)
                .build();
        Request request = new Request.Builder()
                .url(URL + "user_online.php")
                .post(requestBody)
                .addHeader("Content-Type", "application/json")
                .build();
        return  request;
    }

    @Override
    protected Object process(String data) {
        try {

            Gson gson=new Gson();
            Result res=gson.fromJson(data,Result.class);
            res.setData(null);
            return res;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
