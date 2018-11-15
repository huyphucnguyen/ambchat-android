package com.ambientdigitalgroup.ambchat;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

public class TestGson extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_gson);
        Gson gson=new Gson();
       /* Employees e=new Employees("Ngoc danh",21,"ngocdanh@gmail.doc");
        String json=gson.toJson(e);
        Toast.makeText(getBaseContext(),json,Toast.LENGTH_SHORT).show();*/
        String json = "{\"first_name\":\"John\",\"age\":30,\"mail\":\"john@gmail.com\"}";
        Employees employee = gson.fromJson(json, Employees.class);
        TextView txt_gson=findViewById(R.id.txt_gson);
        Toast.makeText(getBaseContext(),employee.getmFirstName(),Toast.LENGTH_SHORT).show();
        }

}
