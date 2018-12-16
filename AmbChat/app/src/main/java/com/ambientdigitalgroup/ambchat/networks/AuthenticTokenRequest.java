package com.ambientdigitalgroup.ambchat.networks;

import com.ambientdigitalgroup.ambchat.utils.Extension;
import com.ambientdigitalgroup.ambchat.utils.ProfileUser;
import com.ambientdigitalgroup.ambchat.utils.Result;
import com.google.gson.Gson;

import org.json.JSONObject;

import java.util.Map;

import okhttp3.MultipartBody;
import okhttp3.Request;
import okhttp3.RequestBody;

public class AuthenticTokenRequest extends SeverRequest {
    public AuthenticTokenRequest(SeverRequestListener listener) {
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
                .url(URL + "sign_in.php")
                .post(requestBody)
                .addHeader("Content-Type", "application/json")
                .build();
        return request;
    }

    @Override
    protected Object process(String rawData) {
        try {
            String data = Extension.decodeJWTToString(rawData);
            Gson gson=new Gson();
            Result res=gson.fromJson(data,Result.class);

            JSONObject json = null;
            json = new JSONObject(data);
            JSONObject object= json.getJSONObject("data"); //Có thể bị Value null at data
            ProfileUser profile = gson.fromJson(String.valueOf(object), ProfileUser.class);

            res.setData(profile);
            res.setToken(rawData);

            return res;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
