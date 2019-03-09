package com.ambientdigitalgroup.ambchat.fragments;


import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.ambientdigitalgroup.ambchat.R;
import com.ambientdigitalgroup.ambchat.activities.StartActivity;
import com.ambientdigitalgroup.ambchat.networks.SearchRequest;
import com.ambientdigitalgroup.ambchat.networks.SeverRequest;
import com.ambientdigitalgroup.ambchat.networks.SignInRequest;
import com.ambientdigitalgroup.ambchat.utils.BaseBackPressListenerListener;
import com.ambientdigitalgroup.ambchat.utils.Extension;
import com.ambientdigitalgroup.ambchat.utils.ProfileUser;
import com.ambientdigitalgroup.ambchat.utils.ProfileUsers;
import com.ambientdigitalgroup.ambchat.utils.Result;

import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 */
public class SearchFargment extends Fragment {

    SearchView searchView;
    EditText editText;
    Button imgSearch;
    AlertDialog mLoginProgress;
    public SearchFargment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        View view = inflater.inflate(R.layout.fragment_searchs, container, false);

        Activity activity = getActivity();

        ((StartActivity)activity).setOnBackPressListener(new BaseBackPressListenerListener((FragmentActivity) activity));

        Toolbar toolbar = view.findViewById(R.id.toolbar);
        ((AppCompatActivity)getActivity()).setSupportActionBar(toolbar);
        /*((AppCompatActivity)getActivity()).getSupportActionBar().setTitle("Search");*/
        ((AppCompatActivity)getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // and this
                startActivity(new Intent(getContext(), StartActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
            }
        });
        // Inflate the layout for this fragment

        // searchView=(SearchView)view.findViewById(R.id.search);
        editText=(EditText) view.findViewById(R.id.edtxtKey);
        imgSearch=(Button) view.findViewById(R.id.btnSearch);

        LoadUser();
       return  view;
    }

    public void LoadUser(){
       imgSearch.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {


               String keySearch = editText.getText().toString().trim();




               Map<String, String> parameter = new HashMap<>();
               parameter.put("keysearch", keySearch);


               SearchRequest request = new SearchRequest(new SeverRequest.SeverRequestListener() {
                   @Override
                   public void completed(Object obj) {
                       boolean success = false;


                       if (obj != null) {
                           Result res = (Result) obj;


                       }

                   }
               });

               request.execute(parameter);
           }
       });


    }
}
