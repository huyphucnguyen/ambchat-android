package com.ambientdigitalgroup.ambchat.activities;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import com.ambientdigitalgroup.ambchat.R;
import com.ambientdigitalgroup.ambchat.networks.NetworkCallback;
import com.ambientdigitalgroup.ambchat.networks.SeverRequest;
import com.ambientdigitalgroup.ambchat.networks.SignInRequest;
import com.ambientdigitalgroup.ambchat.networks.SignUpRequest;
import com.ambientdigitalgroup.ambchat.utils.ProfileUser;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class SignUpActivity extends AppCompatActivity {

    private EditText edtFullName, edtEmail, edtUserName, edtPassWord, edtRepeatPass;
    private RadioButton radMale, radFemail;
    private Button btnRegister;
    public static final MediaType MEDIA_TYPE = MediaType.parse("application/json");
    public static final String USERNAME=null ;
    public static final String PASSWORD = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signup);

        addControls();
        addEvents();
    }

    /*addControls get controls to process*/
    private void addControls() {
        edtEmail = findViewById(R.id.edtEmail);
        edtFullName = findViewById(R.id.edtFullName);
        edtUserName = findViewById(R.id.edtUserName);
        edtPassWord = findViewById(R.id.edtPassWord);
        edtRepeatPass = findViewById(R.id.edtRepeatPass);
        radMale = findViewById(R.id.radMale);
        radFemail = findViewById(R.id.radFemail);
        btnRegister = findViewById(R.id.btnRegister);
    }



    private void addEvents() {
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final String username = edtUserName.getText().toString();
                String fullname = edtFullName.getText().toString();
                String email = edtEmail.getText().toString();
                final String password = edtPassWord.getText().toString();
                String rePassword = edtRepeatPass.getText().toString();
                int gender = radMale.isChecked() ? 1 : 0;

                boolean checkData = isEmptyField(username,fullname,email,password,rePassword);
                if(checkData) return;
                //-------------------------------su ly dang ky------------------------//
                Map<String, String> parameter = new HashMap<>();
                parameter.put("username", username);
                parameter.put("fullname", fullname);
                parameter.put("email",email);
                parameter.put("password",password);
                parameter.put("gender",String.valueOf(gender));

                SignUpRequest request = new SignUpRequest(new SeverRequest.SeverRequestListener() {
                    @Override
                    public void completed(Object obj) {
                        if (obj != null) {

                            int error = (int) obj;
                            /*Toast.makeText(getBaseContext(),mes,Toast.LENGTH_SHORT).show();*/

                            if(error==0) {
                                Intent signin_activity = new Intent(SignUpActivity.this, SignInActivity.class);
                                signin_activity.putExtra(USERNAME,username);
                                signin_activity.putExtra(PASSWORD,password);
                                startActivity(signin_activity);
                            }
                            if(error==1){
                                showMessage("User invalid");
                            }
                            if(error==2){
                                showMessage("Password invalid");
                            }
                            if(error==3){
                                showMessage("User is exist");
                            }
                            if(error==4){
                                showMessage("Email is already registered by another account");
                            }
                        } else {
                            //ERROR
                            Toast.makeText(getBaseContext(),"Error Register!",Toast.LENGTH_SHORT).show();
                        }
                    }
                });
                //thuc hien sau ????
                request.execute(parameter);

            }
        });
    }
    /**isEmptyField
     * @param username is email entered
     * @param fullname is full name entered
     * @param email is user name entered
     * @param password is password entered
     * @param rePassword is repassword entered*/
    private boolean isEmptyField(String username,String fullname,String email,String password,String rePassword) {
        if(email.equals("")){
            Toast.makeText(this,"Email không được để trống",Toast.LENGTH_LONG).show();
            return true;
        }
        if(fullname.equals("")){
            Toast.makeText(this,"Full name không được để trống",Toast.LENGTH_LONG).show();
            return true;
        }

        if(password.equals("")){
            Toast.makeText(this,"Password không được để trống",Toast.LENGTH_LONG).show();
            return true;
        }

        if(username.equals("")){
            Toast.makeText(this,"UserName không được để trống",Toast.LENGTH_LONG).show();
            return true;
        }

        if(!rePassword.equals(password)){
            Toast.makeText(this,"Nhập lại password không khớp",Toast.LENGTH_LONG).show();
            return true;
        }

        return false;
    }

    //function show message
    private void showMessage(String mess){
        Toast.makeText(getBaseContext(),mess,Toast.LENGTH_SHORT).show();
    }

}
