package com.ambientdigitalgroup.ambchat;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class SignUp extends AppCompatActivity implements NetworkCallback{
    private EditText edtFullName, edtEmail, edtUserName, edtPassWord, edtRepeatPass;
    private RadioButton radMale, radFemail;
    private Button btnRegister;
    public static final MediaType MEDIA_TYPE = MediaType.parse("application/json");
    public static final String URL_CONNECT = "https://ambchat.herokuapp.com/api/sign_up.php";

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

    @Override
    public void getResponse(String res) {
        try {
            final JSONObject root = new JSONObject(res);
            String message = root.getString("message");
            Toast.makeText(this,message,Toast.LENGTH_LONG).show();
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    public class NetworkTask extends AsyncTask<String,Void,String>{
        NetworkCallback networkCallback;
        //String json;
        OkHttpClient client = new OkHttpClient();
        String username,fullname,email,password;
        int gender;

        public NetworkTask(NetworkCallback networkCallback,String username,String fullname,String email,String password,int gender){
            //this.json = json;
            this.networkCallback = networkCallback;
            this.username = username;
            this.fullname = fullname;
            this.email = email;
            this.password = password;
            this.gender = gender;
        }

        public String  doPostRequest(String url) throws IOException{
            RequestBody body=new MultipartBody.Builder()
                    .addFormDataPart("username",username)
                    .addFormDataPart("fullname",fullname)
                    .addFormDataPart("email",email)
                    .addFormDataPart("password",password)
                    .addFormDataPart("gender",gender+"")
                    .setType(MultipartBody.FORM)
                    .build();
            //RequestBody body = RequestBody.create(MEDIA_TYPE,json);
            Request request = new Request.Builder()
                    .url(url)
                    .post(body)
                    .addHeader("Content-Type", "application/json")
                    .build();
            Response response = client.newCall(request).execute();
            return response.body().string();
        }
        @Override
        protected String doInBackground(String... strings) {
            try{
                String response = doPostRequest(strings[0]);
                return response;
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            networkCallback.getResponse(s);
        }
    }


    private void addEvents() {
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String username = edtUserName.getText().toString();
                String fullname = edtFullName.getText().toString();
                String email = edtEmail.getText().toString();
                String password = edtPassWord.getText().toString();
                String rePassword = edtRepeatPass.getText().toString();
                int gender = radMale.isChecked() ? 1 : 0;

                boolean checkData = isEmptyField(username,fullname,email,password,rePassword);
                if(checkData) return;

                NetworkTask networkTask = new NetworkTask(SignUp.this,username,fullname,email,password,gender);
                networkTask.execute(URL_CONNECT);
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

}
