package com.ambientdigitalgroup.ambchat;

import android.os.AsyncTask;
import android.os.Build;
import android.support.annotation.RequiresApi;

import org.json.JSONObject;

import java.io.IOException;

import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;


@RequiresApi(api = Build.VERSION_CODES.CUPCAKE)
class AsyncTaskLogin extends AsyncTask<String, Void, String> {
    Response response;
    OkHttpClient client=new OkHttpClient.Builder().build();
    String user,password;

    public AsyncTaskLogin(String user, String password) {
        this.user = user;
        this.password = password;
    }

    @Override
    protected String doInBackground(String... strings) {

        RequestBody requestBody=new MultipartBody.Builder()
                .addFormDataPart("username",user)
                .addFormDataPart("password",password)
                .setType(MultipartBody.FORM)
                .build();
        Request request =new Request.Builder()
                .url(Sever.getUrl()+"sign_in.php")
                .post(requestBody)
                .addHeader("Content-Type", "application/json")
                .build();

        try {
            response=client.newCall(request).execute();
            return  response.body().string();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return  null;
    }

    @Override
    protected void onPostExecute(String s) {
        if (response.isSuccessful()){
            try {
                JSONObject json = new JSONObject(s);
                final String serverResponse = json.getString("message");
                ProfileUser.res=json.getInt("error");
                //get profile user
                JSONObject ob_user=json.getJSONObject("data");
                ProfileUser.username=ob_user.getString("user_name");
                ProfileUser.fullname=ob_user.getString("full_name");
                //   ProfileUser.gender=ob_user.getInt("gender");
                ProfileUser.email=ob_user.getString("email");
                ProfileUser.userid=ob_user.getInt("user_id");


            } catch (Exception e){
                e.printStackTrace();
            }
        }
        super.onPostExecute(s);
    }
}