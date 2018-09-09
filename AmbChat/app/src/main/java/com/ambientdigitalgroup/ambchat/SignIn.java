package com.ambientdigitalgroup.ambchat;

import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;


public class SignIn extends AppCompatActivity{

    private EditText edtUserName,edtPassWord;
    private Button btnSigIn;
    private CheckBox ckbRememberpass;
    private TextView txtForgotPassword,txtRegisterAcount;
    public static final String urlSignIn = "https://ambchat.herokuapp.com/api/sign_in.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signin);
        addControls();
       // if(CheckInvalidData()==true){
            addEvent();
       // }
      //  else{
          //  Toast.makeText(getBaseContext(),"User name or password Invalid",Toast.LENGTH_SHORT).show();
         //   return;
      //  }

    }

    public void addControls(){
       edtUserName=(EditText) findViewById(R.id.edtUserName);
       edtPassWord=(EditText) findViewById(R.id.edtPass);
       txtForgotPassword=(TextView) findViewById(R.id.txtForgotPassword);
       txtRegisterAcount=(TextView) findViewById(R.id.txtRegisterAcount);
       ckbRememberpass=(CheckBox) findViewById(R.id.ckbRemmemberPass);
       btnSigIn=(Button) findViewById(R.id.btnLogIn);
    }

    public  void addEvent(){
        //EVENT LOGIN
        btnSigIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // new DoLogIn("nana","6B4F2790D01A6815EC2C7AC8D0AF0F6862A012EDEA70D28FD73997E33DC393A7").execute();
                //execut class dologin
                new DoLogIn(edtUserName.getText().toString().trim(),edtPassWord.getText().toString().trim()).execute();
            }
        });
    }

    public boolean CheckInvalidData(){
        if(Extension.checkUserName(edtUserName.getText().toString())==0 ||
           Extension.checkPassWord(edtPassWord.getText().toString())==0)
        {
            return false;
        }
        return true;
    }

    //show meesage
    public  void ShowMessage(final Context context, final String msg) {
        if (context != null && msg != null) {
            new Handler(Looper.getMainLooper()).post(new Runnable() {

                @Override
                public void run() {
                    Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    //Ham xu ly da tien trinh
    class DoLogIn extends AsyncTask<String,Void,String>
    {
        Response response;
        OkHttpClient client=new OkHttpClient.Builder().build();
        String user,password;

        public DoLogIn(String user, String password) {
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
                    .url(urlSignIn)
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
                        ShowMessage(getBaseContext(),serverResponse);
                    } catch (Exception e){
                        e.printStackTrace();
                    }
                }
            super.onPostExecute(s);
        }
    }
}
