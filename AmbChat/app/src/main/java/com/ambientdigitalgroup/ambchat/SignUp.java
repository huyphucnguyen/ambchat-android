package com.ambientdigitalgroup.ambchat;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class SignUp extends AppCompatActivity {
    private EditText edtFullName,edtEmail,edtUserName,edtPassWord,edtRepeatPass;
    private RadioButton radMale,radFemail;
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

    /*addEvents add events on app*/
    private void addEvents() {
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Kiểm tra trường trống
                boolean emptyField = isEmptyField(edtEmail,edtFullName,edtUserName,edtPassWord,edtRepeatPass);
                if(emptyField) return;

                //Khởi tạo OkHttpClient
                final OkHttpClient client = new OkHttpClient();
                JSONObject postdata = new JSONObject();
                try{
                    postdata.put("username",edtUserName);
                    postdata.put("fullname",edtFullName);
                    postdata.put("password",edtPassWord);
                    postdata.put("email",edtEmail);
                    int gender = radMale.isChecked()?1:0;
                    postdata.put("gender",gender);

                }catch (JSONException e){
                    e.printStackTrace();
                }

                RequestBody body = RequestBody.create(MEDIA_TYPE,postdata.toString());

                final Request request = new Request.Builder()
                        .url(URL_CONNECT)
                        .post(body)
                        .addHeader("Content-Type", "application/json")
                        .build();
                client.newCall(request).enqueue(new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {
                        String mMessage = e.getMessage().toString();
                        Log.w("failure Response",mMessage);
                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        String mMessage = response.body().string();
                        if(response.isSuccessful()){
                            try{
                                JSONObject json = new JSONObject(mMessage);
                                final String serverResponse = json.getString("message");
                                Toast.makeText(SignUp.this,serverResponse,Toast.LENGTH_LONG).show();
                            }catch (Exception e){
                                e.printStackTrace();
                            }
                        }
                    }
                });
            }
        });
    }
    /*isEmptyField
     * @param edtEmail is email entered
     * @param edtFullName is full name entered
     * @param edtUserName is user name entered
     * @param edtPassWord is password entered
     * @param edtRepeatPass is repassword entered*/
    private boolean isEmptyField(EditText edtEmail, EditText edtFullName, EditText edtUserName, EditText edtPassWord, EditText edtRepeatPass) {
        if(edtEmail.toString().equals("")){
            Toast.makeText(this,"Email không được để trống",Toast.LENGTH_LONG).show();
            return true;
        }
        if(edtFullName.toString().equals("")){
            Toast.makeText(this,"Full name không được để trống",Toast.LENGTH_LONG).show();
            return true;
        }

        if(edtPassWord.toString().equals("")){
            Toast.makeText(this,"Password không được để trống",Toast.LENGTH_LONG).show();
            return true;
        }

        if(edtUserName.toString().equals("")){
            Toast.makeText(this,"UserName không được để trống",Toast.LENGTH_LONG).show();
            return true;
        }

        if(edtRepeatPass.toString().equals(edtPassWord.toString())){
            Toast.makeText(this,"Nhập lại password không khớp",Toast.LENGTH_LONG).show();
            return true;
        }

        return false;
    }


}
