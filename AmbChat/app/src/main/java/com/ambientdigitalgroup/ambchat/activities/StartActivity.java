package com.ambientdigitalgroup.ambchat.activities;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.ambientdigitalgroup.ambchat.R;
import com.ambientdigitalgroup.ambchat.fragments.MainFragment;
import com.ambientdigitalgroup.ambchat.fragments.SignInFragment;
import com.ambientdigitalgroup.ambchat.fragments.StartPageFragment;
import com.ambientdigitalgroup.ambchat.networks.SeverRequest;
import com.ambientdigitalgroup.ambchat.networks.SignInRequest;
import com.ambientdigitalgroup.ambchat.utils.Extension;
import com.ambientdigitalgroup.ambchat.utils.ProfileUser;
import com.ambientdigitalgroup.ambchat.utils.Result;

import java.util.HashMap;
import java.util.Map;

public class StartActivity extends AppCompatActivity
{
    private ProgressDialog mLoginProgress;
    public static final String TOKEN= "TOKEN" ;
    String token;
    String username;
    String prefname="my_data";
    public static final String USERNAME= "USERNAME" ;
    public static final String FULLNAME = "FULLNAME";
    public static final String USERID="USERID";
    public static final String EMAIL="EMAIL";


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_container);
        mLoginProgress = new ProgressDialog(getApplicationContext());
        mLoginProgress.setTitle("Logging In");
        mLoginProgress.setMessage("Please wait while we check your account.");
        mLoginProgress.setCanceledOnTouchOutside(false);
        username=readUsername();
        token = readToken();
        if(!token.isEmpty()){
            mLoginProgress.show();

            Map<String, String> parameter = new HashMap<>();
            parameter.put("TOKEN", token);
            SignInRequest request = new SignInRequest(new SeverRequest.SeverRequestListener() {
                @Override
                public void completed(Object obj) {
                    if (obj != null) {

                        mLoginProgress.dismiss();
                        Result res = (Result) obj;
                        token= res.getToken();
                        ProfileUser user=(ProfileUser) res.getData();
                        Fragment fragment = new MainFragment();
                        Bundle args = new Bundle();
                        args.putString(USERNAME,user.getUser_name());
                        args.putString(FULLNAME,user.getFull_name());
                        args.putInt(USERID,user.getUser_id());
                        args.putString(EMAIL,user.getEmail());
                        fragment.setArguments(args);
                        Extension.replaceFragment(getSupportFragmentManager(),fragment);

                    } else {
                        //ERROR
                        mLoginProgress.hide();
                        Bundle bundle=new Bundle();
                        bundle.putString("username",username);
                        SignInFragment fragment = new SignInFragment();
                        fragment.setArguments(bundle);
                        FragmentManager fragmentManager = getSupportFragmentManager();
                        FragmentTransaction transaction = fragmentManager.beginTransaction();
                        transaction.add(R.id.flContainer,fragment);
                        transaction.commit();

                    }
                }
            });
        }else{
            StartPageFragment fragment = new StartPageFragment();
            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction transaction = fragmentManager.beginTransaction();
            transaction.add(R.id.flContainer,fragment);
            transaction.commit();
        }

        //TODO neu user da login roi thi sao?

    }
    public String readToken(){
        SharedPreferences sharedPreferences =getSharedPreferences(prefname, Context.MODE_PRIVATE);
        String token = sharedPreferences.getString("token","");
        Toast.makeText(getApplicationContext(),token,Toast.LENGTH_LONG).show();
        return token;
    }
    public String readUsername(){
        SharedPreferences sharedPreferences =getSharedPreferences(prefname, Context.MODE_PRIVATE);
        String username = sharedPreferences.getString("username","");
        Toast.makeText(getApplicationContext(),username,Toast.LENGTH_LONG).show();
        return username;
    }


}
