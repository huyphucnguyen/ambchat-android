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
import com.ambientdigitalgroup.ambchat.networks.AuthenticTokenRequest;
import com.ambientdigitalgroup.ambchat.networks.SeverRequest;
import com.ambientdigitalgroup.ambchat.utils.Extension;
import com.ambientdigitalgroup.ambchat.utils.OnBackPressListener;
import com.ambientdigitalgroup.ambchat.utils.ProfileUser;
import com.ambientdigitalgroup.ambchat.utils.Result;

import java.util.HashMap;
import java.util.Map;

public class StartActivity extends AppCompatActivity {
    private ProgressDialog mLoginProgress;
    public static final String TOKEN = "TOKEN";
    String token;
    String username;
    String prefname = "my_data";
    public static final String USERNAME = "USERNAME";
    public static final String FULLNAME = "FULLNAME";
    public static final String USERID = "USERID";
    public static final String EMAIL = "EMAIL";
    protected OnBackPressListener onBackPressListener;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_container);

        mLoginProgress = new ProgressDialog(StartActivity.this);
        mLoginProgress.setTitle("Logging In");
        mLoginProgress.setMessage("Please wait while we check your account.");
        mLoginProgress.setCanceledOnTouchOutside(false);

        username = readUsername();
        token = readToken();

        if (token != null && !token.isEmpty()) {
            mLoginProgress.show();
            Map<String, String> parameter = new HashMap<>();
            parameter.put(TOKEN, token);
            AuthenticTokenRequest request = new AuthenticTokenRequest(new SeverRequest.SeverRequestListener() {
                @Override
                public void completed(Object obj) {

                    boolean success = false;
                    if (obj != null) {
                        Result res = ( Result ) obj;
                        if (res.getError() == 0) {
                            success = true;
                            token = res.getToken();
                            ProfileUser user = ( ProfileUser ) res.getData();
                            Fragment fragment = new MainFragment();
                            //Create bundle to send data
                            Bundle args = new Bundle();
                            args.putString(USERNAME, user.getUser_name());
                            args.putString(FULLNAME, user.getFull_name());
                            args.putInt(USERID, user.getUser_id());
                            args.putString(EMAIL, user.getEmail());
                            fragment.setArguments(args);
                            //add fragment
                            FragmentTransaction transaction = getSupportFragmentManager()
                                    .beginTransaction();
                            transaction.add(R.id.flContainer, fragment,"MAIN")
                                    .addToBackStack(null)
                                    .commit();
                        }
                    }//obj != null
                    if (!success) {
                        SignInFragment fragment = new SignInFragment();
                        if(username!=null&&username.isEmpty()){
                            Bundle bundle = new Bundle();
                            bundle.putString("username", username);
                            fragment.setArguments(bundle);
                        }
                        FragmentManager fragmentManager = getSupportFragmentManager();
                        FragmentTransaction transaction = fragmentManager.beginTransaction();
                        transaction
                                .add(R.id.flContainer, fragment, "SIGN_IN")
                                .addToBackStack(null)
                                .commit();
                    }
                    mLoginProgress.dismiss();
                }

            });
            request.execute(parameter);
        } else {
            SignInFragment fragment = new SignInFragment();
            if(username!=null&&username.isEmpty()){
                Bundle bundle = new Bundle();
                bundle.putString("username", username);
                fragment.setArguments(bundle);
            }

            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction transaction = fragmentManager.beginTransaction();
            transaction.add(R.id.flContainer, fragment, "SIGN_IN");
            transaction.addToBackStack(null);
            transaction.commit();
        }



    }

    public String readToken() {
        SharedPreferences sharedPreferences = getSharedPreferences(prefname, Context.MODE_PRIVATE);
        String token = sharedPreferences.getString("token", "");
        return token;
    }

    public String readUsername() {
        SharedPreferences sharedPreferences = getSharedPreferences(prefname, Context.MODE_PRIVATE);
        String username = sharedPreferences.getString("username", "");
        return username;
    }

    @Override
    public void onBackPressed() {
       if(onBackPressListener !=null){
           Toast.makeText(this,"Day la custom back press",Toast.LENGTH_LONG).show();
           onBackPressListener.doBack();
       }else{
           Toast.makeText(this,"Day la super back press",Toast.LENGTH_LONG).show();
           super.onBackPressed();
       }
    }

    public void setOnBackPressListener(OnBackPressListener onBackPressListener){
        this.onBackPressListener = onBackPressListener;
    }

}
