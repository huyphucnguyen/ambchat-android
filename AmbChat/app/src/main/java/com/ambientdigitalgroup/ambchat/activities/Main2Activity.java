package com.ambientdigitalgroup.ambchat.activities;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.view.Gravity;
import android.view.MenuInflater;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.ambientdigitalgroup.ambchat.R;
import com.ambientdigitalgroup.ambchat.adapters.SectionsPagerAdapter;
import com.ambientdigitalgroup.ambchat.fragments.InforUserFragment;
import com.ambientdigitalgroup.ambchat.fragments.SignInFragment;
import com.ambientdigitalgroup.ambchat.networks.CheckUserOnlineRequest;
import com.ambientdigitalgroup.ambchat.networks.SeverRequest;
import com.ambientdigitalgroup.ambchat.utils.Extension;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.widget.Toast;

import com.ambientdigitalgroup.ambchat.fragments.MainFragment;
import com.ambientdigitalgroup.ambchat.fragments.SignInFragment;
import com.ambientdigitalgroup.ambchat.networks.AuthenticTokenRequest;
import com.ambientdigitalgroup.ambchat.networks.CheckUserOnlineRequest;
import com.ambientdigitalgroup.ambchat.networks.SeverRequest;
import com.ambientdigitalgroup.ambchat.utils.Extension;
import com.ambientdigitalgroup.ambchat.utils.ProfileUser;
import com.ambientdigitalgroup.ambchat.utils.Result;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.widget.Toast;

import java.sql.Time;
import java.util.HashMap;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

public class Main2Activity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    private android.support.v7.widget.Toolbar mToolBar;

    private ViewPager mViewPager;
    private SectionsPagerAdapter mSectinosPagerAdapter;

    private TabLayout mTabLayout;
    String prefname="my_data";
    String token;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        getView();
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Toast.makeText(this,"Day la activity",Toast.LENGTH_LONG).show();

        //tab
        mSectinosPagerAdapter =new SectionsPagerAdapter(getSupportFragmentManager());
        mViewPager.setAdapter(mSectinosPagerAdapter);
        mTabLayout.setupWithViewPager(mViewPager);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        //auto sen request
        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                chekUserOnline();
            }
        };
        Timer timer = new Timer();
        timer.scheduleAtFixedRate(timerTask, 0, 1000);
    }

    public  void chekUserOnline(){
        token = readToken();
        if(!token.isEmpty()){

            Map<String, String> parameter = new HashMap<>();
            parameter.put("TOKEN", token);
            CheckUserOnlineRequest request = new CheckUserOnlineRequest(new SeverRequest.SeverRequestListener() {
                @Override
                public void completed(Object obj) {
                    if (obj != null) {




                    } else {
                        //ERROR


                    }
                }
            });
            request.execute(parameter);


        }

    }
    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public  void getView(){
        mToolBar = findViewById(R.id.main_page_toolbar);
        //tab
        mViewPager = (ViewPager) findViewById(R.id.main_tabPager);
        mTabLayout = findViewById(R.id.main_tabs);
    }

/*

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.main_menu,menu);
    }
*/

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.main_menu,menu);
        return  true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        super.onOptionsItemSelected(item);
        if(item.getItemId() == R.id.main_logout_btn){
            Fragment fragment = getSupportFragmentManager().findFragmentByTag("SIGN_IN");
            FragmentTransaction transaction = null;
            if (fragment==null) {
                SignInFragment signInFragment = new SignInFragment();
                transaction = getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.flContainer, signInFragment);
                transaction.addToBackStack(null);
                transaction.commit();
            }else {
                transaction.replace(R.id.flContainer, fragment);
                transaction.addToBackStack(null);
                transaction.commit();
            }


        }

        if(item.getItemId() == R.id.main_account_btn){
            Fragment fragment = new InforUserFragment();
            Extension.replaceFragment(getSupportFragmentManager(),fragment);
        }

        if(item.getItemId() == R.id.main_all_btn){

        }

        return true;
    }


    public String readToken(){
        SharedPreferences sharedPreferences =getSharedPreferences(prefname, Context.MODE_PRIVATE);
        String token = sharedPreferences.getString("token","");
        return token;
    }
}
