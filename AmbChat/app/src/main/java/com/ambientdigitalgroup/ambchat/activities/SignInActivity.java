package com.ambientdigitalgroup.ambchat.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.ambientdigitalgroup.ambchat.R;
import com.ambientdigitalgroup.ambchat.networks.AsyncTaskLogin;
import com.ambientdigitalgroup.ambchat.networks.SeverRequest;
import com.ambientdigitalgroup.ambchat.networks.SignInRequest;
import com.ambientdigitalgroup.ambchat.utils.Extension;
import com.ambientdigitalgroup.ambchat.utils.ProfileUser;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;


public class SignInActivity extends AppCompatActivity {

    private EditText edtUserName, edtPassWord;
    private Button btnSigIn;
    private CheckBox ckbRememberpass;
    private TextView txtForgotPassword, txtRegisterAcount;
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

    public void addControls() {
        edtUserName = (EditText) findViewById(R.id.edtUserName);
        edtPassWord = (EditText) findViewById(R.id.edtPass);
        txtForgotPassword = (TextView) findViewById(R.id.txtForgotPassword);
        txtRegisterAcount = (TextView) findViewById(R.id.txtRegisterAcount);
        ckbRememberpass = (CheckBox) findViewById(R.id.ckbRemmemberPass);
        btnSigIn = (Button) findViewById(R.id.btnLogIn);
        String pass = edtPassWord.getText().toString();

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
            String sss = Context.APPWIDGET_SERVICE;
        }
        //Test encode SHA256

       /* try {
            String encodeSHA256=SHA256(pass);
            Toast.makeText(SignInActivity.this,encodeSHA256,Toast.LENGTH_LONG).show();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }*/
    }

    public void addEvent() {
        //EVENT LOGIN
        btnSigIn.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.CUPCAKE)
            @Override
            public void onClick(View view) {
                // new DoLogIn("nana","6B4F2790D01A6815EC2C7AC8D0AF0F6862A012EDEA70D28FD73997E33DC393A7").execute();
                //execut class dologin

                String userName = edtUserName.getText().toString().trim();
                String password = edtPassWord.getText().toString().trim();

                Map<String, String> parameter = new HashMap<>();
                parameter.put("username", userName);
                parameter.put("password", password);
                SignInRequest request = new SignInRequest(new SeverRequest.SeverRequestListener() {
                    @Override
                    public void completed(Object obj) {
                        if (obj != null) {
                            ProfileUser profileUser = (ProfileUser) obj;
                            if (profileUser.res == 0) {
                                Intent intHomeActivity = new Intent(SignInActivity.this, MainActivity.class);
                                startActivity(intHomeActivity);
                            } else {
                                Toast.makeText(getBaseContext(), "Login fail", Toast.LENGTH_SHORT).show();
                                return;
                            }
                        } else {
                            //ERROR
                        }
                    }
                });
                request.execute(parameter);
                //new AsyncTaskLogin(edtUserName.getText().toString().trim(),edtPassWord.getText().toString().trim()).execute();


//                Toast.makeText(getBaseContext(),String.valueOf(ProfileUser.username),Toast.LENGTH_SHORT).show();
//                if(ProfileUser.res==0){
//                    Intent intHomeActivity=new Intent(SignInActivity.this,MainActivity.class);
//                    startActivity(intHomeActivity);
//                }
//                else {
//                  Toast.makeText(getBaseContext(),"Login fail",Toast.LENGTH_SHORT).show();
//                  return;
//                }
            }
        });
    }

    public boolean checkInvalidData() {
        if (Extension.checkUserName(edtUserName.getText().toString()) == 0 ||
                Extension.checkPassWord(edtPassWord.getText().toString()) == 0) {
            return false;
        }
        return true;
    }

    public static String SHA256(String pass) throws NoSuchAlgorithmException {
        MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");
        messageDigest.update(pass.getBytes());
        byte[] digest = messageDigest.digest();
        return android.util.Base64.encodeToString(digest, android.util.Base64.DEFAULT);
    }

}
