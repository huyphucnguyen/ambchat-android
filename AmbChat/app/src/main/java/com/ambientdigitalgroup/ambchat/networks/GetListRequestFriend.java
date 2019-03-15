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

public class GetListRequestFriend extends SeverRequest {
    public GetListRequestFriend(SeverRequestListener listener) {
        super(listener);
    }

    @Override
    protected Request prepare(Map<String, String> parameter) {

        String userid = parameter.get("user_id");
        RequestBody requestBody = new MultipartBody.Builder()
                .addFormDataPart("user_id", userid)
                .setType(MultipartBody.FORM)
                .build();

        Request request =new Request.Builder()
                .url(URL+"getRequestListFriends.php")
                .post(requestBody)
                .build();


       return  request;
    }


    @Override
    protected Object process(String data) {

            try {

                JSONObject json=new JSONObject(data);
                ArrayList<User> arrUser=new ArrayList<User>();
                JSONArray array_user=json.getJSONArray("data") ;
                for(int i=0;i<array_user.length();i++){
                    JSONObject ob=array_user.getJSONObject(i);
                    if(ob.getInt("user_id")!= ProfileUsers.getInstance().getUser_id()){
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
                }

                return  arrUser;
            } catch (Exception e){
                e.printStackTrace();
            }
        return null;
    }
}
