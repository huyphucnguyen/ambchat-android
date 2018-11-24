package com.ambientdigitalgroup.ambchat.fragments;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.ambientdigitalgroup.ambchat.R;

public class HomeFragment extends Fragment {

    TextView txtMsg;
    Button btnThoat;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup root = ( ViewGroup ) inflater.inflate(R.layout.fragment_request, container, false);
        txtMsg = ( TextView ) root.findViewById(R.id.txtmsg);
        btnThoat = ( Button ) root.findViewById(R.id.btnThoat);
        return root;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        addEvent();


    }

    private void addEvent() {
        btnThoat.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                // TODO Auto-generated method stub
            }
        });
        Bundle bundle = getArguments();
        if (bundle != null) {
            txtMsg.setText("Hello : " + bundle.getString("userName"));
        }

    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.main_menu, menu);
    }
}
