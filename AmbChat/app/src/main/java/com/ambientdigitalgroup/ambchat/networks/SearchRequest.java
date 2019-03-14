package com.ambientdigitalgroup.ambchat.networks;

import com.ambientdigitalgroup.ambchat.utils.Extension;
import com.ambientdigitalgroup.ambchat.utils.Result;
import com.ambientdigitalgroup.ambchat.utils.User;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Map;

import okhttp3.MultipartBody;
import okhttp3.Request;
import okhttp3.RequestBody;

public class SearchRequest extends SeverRequest {
    public SearchRequest(SeverRequestListener listener) {
        super(listener);
    }

    @Override
    protected Request prepare(Map<String, String> parameter) {
        String keysearch = parameter.get("keysearch");


        RequestBody requestBody = new MultipartBody.Builder()
                .addFormDataPart("keysearch", keysearch)
                .setType(MultipartBody.FORM)
                .build();
        Request request = new Request.Builder()
                .url(URL + "search_friends.php")
                .post(requestBody)
                .addHeader("Content-Type", "application/json")
                .build();
        return request;
    }

    @Override
    protected Object process(String data) {

        try {
            Gson gson=new Gson();
            Result res=gson.fromJson(data,Result.class);
            JSONObject json = null;
            json = new JSONObject(data);
            ArrayList<User> arrUser=new ArrayList<User>();
            JSONArray array_user= json.getJSONArray("data");
            for(int i=0;i<array_user.length();i++){
                JSONObject ob=array_user.getJSONObject(i);
                    arrUser.add(new User(
                            ob.getInt("user_id"),
                            null,
                            ob.getString("full_name"),
                            null,
                            null,
                            -1,
                            null

                    ));
            }
            res.setData(arrUser);
            return  res.getData();
        } catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }
}
