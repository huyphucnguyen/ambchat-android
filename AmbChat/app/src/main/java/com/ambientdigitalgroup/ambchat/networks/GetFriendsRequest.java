package com.ambientdigitalgroup.ambchat.networks;

import com.ambientdigitalgroup.ambchat.utils.ProfileUser;
import com.ambientdigitalgroup.ambchat.utils.Result;
import com.ambientdigitalgroup.ambchat.utils.User;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.AbstractQueue;
import java.util.ArrayList;
import java.util.Map;

import okhttp3.MultipartBody;
import okhttp3.Request;
import okhttp3.RequestBody;

public class GetFriendsRequest extends SeverRequest {
    public GetFriendsRequest(SeverRequestListener listener) {
        super(listener);
    }

    @Override
    protected Request prepare(Map<String, String> parameter) {
        Request request =new Request.Builder()
                .url(URL+"getlistfriend.php")
                .get()
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
                            ob.getInt("User_ID"),
                            ob.getString("User_Name"),
                            ob.getString("Full_Name"),
                            ob.getString("status")
                    ));

                }
                res.setData(arrUser);
                return  res;
            } catch (Exception e){
                e.printStackTrace();
            }
        return null;
    }
}
