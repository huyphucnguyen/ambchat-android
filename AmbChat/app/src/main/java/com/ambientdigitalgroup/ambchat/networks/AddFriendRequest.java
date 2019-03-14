package com.ambientdigitalgroup.ambchat.networks;

import com.ambientdigitalgroup.ambchat.utils.ProfileUsers;
import com.ambientdigitalgroup.ambchat.utils.User;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Map;

import okhttp3.MultipartBody;
import okhttp3.Request;
import okhttp3.RequestBody;

public class AddFriendRequest extends  SeverRequest {
    public AddFriendRequest(SeverRequestListener listener) {
        super(listener);
    }

    @Override
    protected Request prepare(Map<String, String> parameter) {
        String from_id = parameter.get("from_id");
        String friend_id = parameter.get("friend_id");
        RequestBody requestBody = new MultipartBody.Builder()
                .addFormDataPart("user_id", userid)
                .addFormDataPart("friend_id")
                .setType(MultipartBody.FORM)
                .build();

        Request request =new Request.Builder()
                .url(URL+"add_friend.php")
                .post(requestBody)
                .build();

        return  request;
    }

    @Override
    protected Object process(String data) {
        try {

            JSONObject json=new JSONObject(data);

            }


        } catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }
}
