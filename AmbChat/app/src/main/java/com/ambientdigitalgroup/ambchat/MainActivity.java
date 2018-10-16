package com.ambientdigitalgroup.ambchat;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity
        {
            private Button lbtnSigIn;
            private Button lbtnSigUp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        AnhXa();
        AddEvent();

//        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
//        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
//                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
//        drawer.addDrawerListener(toggle);
//        toggle.syncState();
//
//        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
//        navigationView.setNavigationItemSelectedListener(this);
    }
     public void AnhXa(){
         lbtnSigIn =(Button) findViewById(R.id.btnLinkSigIn);
         lbtnSigUp=(Button) findViewById(R.id.btnLinkRegister);

    }

    public  void AddEvent(){
        //link sign in click
        lbtnSigIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               Intent intActivitySigin=new Intent(MainActivity.this,SignIn.class);
               startActivity(intActivitySigin);
               // Toast.makeText(getBaseContext(),"Co len",Toast.LENGTH_SHORT).show();
            }
        });

        lbtnSigUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intActivitySignup=new Intent(MainActivity.this,SignUp.class);
                startActivity(intActivitySignup);
            }
        });
    }
}
