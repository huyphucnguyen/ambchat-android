package com.example.ngocdanh.appchat;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

public class SignIn extends AppCompatActivity {

    private EditText edTextUserName;
    private EditText edPassWord;
    private Button btnLogIn;
    String url="http://192.168.0.106:8080/androidwebservice/insert.php";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
        AnhXa();
        btnLogIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DoLogIn(url);
            }
        });

    }

    private void AnhXa(){
        edTextUserName=(EditText) findViewById(R.id.edtUserName);
        edPassWord=(EditText) findViewById(R.id.edtPass);
        btnLogIn=(Button) findViewById(R.id.btnLogIn);
    }
    private void DoLogIn(String url){
        if(Extension.checkPassWord(edPassWord.getText().toString())==0){
            Toast.makeTex(SignIn.this,"Pass word khong hop le",Toast.LENGTH_SHORT).show();


        }
        if(Extension.checkUserName(edTextUserName.getText().toString())==0){
            Toast.makeTex(SignIn.this,"User name khong hop le",Toast.LENGTH_SHORT).show();

        }



        RequestQueue requestQueue= Volley.newRequestQueue(this);
        StringRequest stringRequest=new StringRequest(Request.Method.POST,url
                , new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if(response.trim().equals("sucess")){
                    Toast.makeText(M.this,"Dang nhap thanh cong",Toast.LENGTH_SHORT).show();
                 //   startActivity(new Intent(AddSinhVien.this,MainActivity.class));}
                else{
                    Toast.makeText(AddSinhVien.this,"Lỗi dang nhap!",Toast.LENGTH_SHORT).show();}
            }

        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(AddSinhVien.this,"Lỗi!",Toast.LENGTH_SHORT).show();
                        Log.v("AAA=","Lôi! \n"+error.toString());
                    }
                }

        ){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params=new HashMap<>();
                params.put("username",edTextUserName.getText().toString().trim());
                params.put("password",edPassWord.getText().toString().trim());

                return params;
            }
        };
        requestQueue.add(stringRequest);
    }
}
