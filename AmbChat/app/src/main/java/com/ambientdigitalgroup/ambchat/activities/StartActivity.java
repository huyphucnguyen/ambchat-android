package com.ambientdigitalgroup.ambchat;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.Button;

public class StartActivity extends AppCompatActivity
        {
            private Button lbtnSigIn;
            private Button lbtnSigUp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);


        getView();
        addEvent();

    }
     public void getView(){
         lbtnSigIn =(Button) findViewById(R.id.btnLinkSigIn);
         lbtnSigUp=(Button) findViewById(R.id.btnLinkRegister);

    }

    public  void addEvent(){
        //link sign in click
        lbtnSigIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               Intent intActivitySigin=new Intent(StartActivity.this,SignInActivity.class);
               startActivity(intActivitySigin);
               // Toast.makeText(getBaseContext(),"Co len",Toast.LENGTH_SHORT).show();
            }
        });

        lbtnSigUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intActivitySignup=new Intent(StartActivity.this,SignUp.class);
                startActivity(intActivitySignup);
            }
        });
    }
}