package com.ambientdigitalgroup.ambchat.networks;

import android.widget.Toast;

import com.ambientdigitalgroup.ambchat.utils.ProfileUser;
import com.google.gson.Gson;

import org.json.JSONObject;

import java.io.IOException;
import java.util.Map;

import okhttp3.MultipartBody;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class SignUpRequest extends SeverRequest {
    public SignUpRequest(SeverRequestListener listener) {
        super(listener);
    }

    @Override
    protected Request prepare(Map<String, String> parameter) {

        String user = parameter.get("username");
        String fullname = parameter.get("fullname");
        String email = parameter.get("email");
        String password = parameter.get("password");
        String gender = parameter.get("gender");



        RequestBody requestBody = new MultipartBody.Builder()
                .addFormDataPart("username", user)
                .addFormDataPart("fullname",fullname)
                .addFormDataPart("email",email)
                .addFormDataPart("password",password)
                .addFormDataPart("gender", gender)
                .setType(MultipartBody.FORM)
                .build();
        Request request = new Request.Builder()
                .url(URL + "sign_up.php")
                .post(requestBody)
                .addHeader("Content-Type", "application/json")
                .build();
        return request;

    }

    @Override
    protected Object process(String data) {
        try {
            final JSONObject json = new JSONObject(data);
            int error = json.getInt("error");
            return error;
        } catch (Exception e) {
            e.printStackTrace();

        }


        return null;
    }
}
