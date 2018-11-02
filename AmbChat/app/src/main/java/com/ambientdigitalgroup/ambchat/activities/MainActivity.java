package com.ambientdigitalgroup.ambchat.activities;



import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TableLayout;
import android.widget.Toast;
import android.widget.Toolbar;

import com.ambientdigitalgroup.ambchat.R;
import com.ambientdigitalgroup.ambchat.adapters.SectionsPagerAdapter;
import com.ambientdigitalgroup.ambchat.fragments.InforUserFragment;


public class MainActivity extends AppCompatActivity {


    private android.support.v7.widget.Toolbar mToolBar;

    private ViewPager mViewPager;
    private SectionsPagerAdapter mSectinosPagerAdapter;

    private TabLayout mTabLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getView();

        setSupportActionBar(mToolBar);
        getSupportActionBar().setTitle("AbmChat");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        //tab
        mSectinosPagerAdapter =new SectionsPagerAdapter(getSupportFragmentManager());
        mViewPager.setAdapter(mSectinosPagerAdapter)    ;
        mTabLayout.setupWithViewPager(mViewPager)   ;



    }


    public  void getView(){
        mToolBar =findViewById(R.id.main_page_toolbar);
        //tab
        mViewPager = (ViewPager) findViewById(R.id.main_tabPager);
        mTabLayout=findViewById(R.id.main_tabs);
    }
    public boolean onCreateOptionsMenu(Menu menu){
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.main_menu,menu);
       return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        super.onOptionsItemSelected(item);

        if(item.getItemId() == R.id.main_logout_btn){


        }

        if(item.getItemId() == R.id.main_acount_btn){

            Intent settingsIntent = new Intent(MainActivity.this, InforUserFragment.class);
            startActivity(settingsIntent);
        }

        if(item.getItemId() == R.id.main_all_btn){

        }

        return true;
    }

}
