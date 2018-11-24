package com.ambientdigitalgroup.ambchat.networks;

import com.ambientdigitalgroup.ambchat.utils.ProfileUser;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Map;

import okhttp3.MultipartBody;
import okhttp3.Request;
import okhttp3.RequestBody;

/**
 * Created by nguyenhuyphuc on 11/2/18.
 */

public class SignInRequest extends SeverRequest {
    public SignInRequest(SeverRequestListener listener) {
        super(listener);
    }

    @Override
    protected Request prepare(Map<String, String> parameter) {
        String user = parameter.get("username");
        String password = parameter.get("password");

        RequestBody requestBody = new MultipartBody.Builder()
                .addFormDataPart("username", user)
                .addFormDataPart("password", password)
                .setType(MultipartBody.FORM)
                .build();
        Request request = new Request.Builder()
                .url(URL + "sign_in.php")
                .post(requestBody)
                .addHeader("Content-Type", "application/json")
                .build();
        return request;
    }


    @Override
    protected Object process(String data) {
        try {
            JSONObject json = null;
            json = new JSONObject(data);
            JSONObject object= json.getJSONObject("data"); //Có thể bị Value null at data
     /*       String json = "{\"user_name\":\"nana\",\"full_name\":\"BuiNa\",\"picture\":\"john@gmail.com\"}";*/
            Gson gson=new Gson();
            ProfileUser profile = gson.fromJson(String.valueOf(object), ProfileUser.class);
            return profile;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
