package com.ambientdigitalgroup.ambchat.activities;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import com.ambientdigitalgroup.ambchat.R;
import com.ambientdigitalgroup.ambchat.fragments.StartPageFragment;

public class StartActivity extends AppCompatActivity
{
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_container);


        //TODO neu user da login roi thi sao?
        StartPageFragment fragment = new StartPageFragment();

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.add(R.id.flContainer,fragment);
        transaction.commit();
    }
}
