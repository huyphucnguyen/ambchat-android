package com.ambientdigitalgroup.ambchat;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
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
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;


public class SignInActivity extends AppCompatActivity{

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
       // }-
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
       String pass= edtPassWord.getText().toString();
        //Test encode SHA256

       /* try {
            String encodeSHA256=SHA256(pass);
            Toast.makeText(SignInActivity.this,encodeSHA256,Toast.LENGTH_LONG).show();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }*/
    }

    public  void addEvent(){
        //EVENT LOGIN
        btnSigIn.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.CUPCAKE)
            @Override
            public void onClick(View view) {
                // new DoLogIn("nana","6B4F2790D01A6815EC2C7AC8D0AF0F6862A012EDEA70D28FD73997E33DC393A7").execute();
                //execut class dologin
                new AsyncTaskLogin(edtUserName.getText().toString().trim(),edtPassWord.getText().toString().trim()).execute();


                Toast.makeText(getBaseContext(),String.valueOf(ProfileUser.username),Toast.LENGTH_SHORT).show();
                if(ProfileUser.res==0){
                    Intent intHomeActivity=new Intent(SignInActivity.this,MainActivity.class);
                    startActivity(intHomeActivity);
                }
                else {
                  Toast.makeText(getBaseContext(),"Login fail",Toast.LENGTH_SHORT).show();
                  return;
                }
            }
        });
    }

    public boolean checkInvalidData(){
        if(Extension.checkUserName(edtUserName.getText().toString())==0 ||
           Extension.checkPassWord(edtPassWord.getText().toString())==0)
        {
            return false;
        }
        return true;
    }

    public static String SHA256(String pass) throws NoSuchAlgorithmException {
        MessageDigest messageDigest=MessageDigest.getInstance("SHA-256");
        messageDigest.update(pass.getBytes());
        byte[] digest = messageDigest.digest();
        return android.util.Base64.encodeToString(digest, android.util.Base64.DEFAULT);
    }

}
