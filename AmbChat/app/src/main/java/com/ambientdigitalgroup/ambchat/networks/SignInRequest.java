package com.ambientdigitalgroup.ambchat.networks;

import com.ambientdigitalgroup.ambchat.utils.ProfileUser;

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
            ProfileUser profile = new ProfileUser();

            JSONObject json = null;
            json = new JSONObject(data);

            final String serverResponse = json.getString("message");
            profile.res = json.getInt("error");
            //get profile user
            JSONObject ob_user = json.getJSONObject("data");
            profile.username = ob_user.getString("user_name");
            profile.fullname = ob_user.getString("full_name");
            //   profile.gender=ob_user.getInt("gender");
            profile.email = ob_user.getString("email");
            profile.userid = ob_user.getInt("user_id");
            return profile;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
