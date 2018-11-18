package com.ambientdigitalgroup.ambchat.fragments;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.ambientdigitalgroup.ambchat.R;


/**
 * A simple {@link Fragment} subclass.
 */
public class RequestFragment extends Fragment {
    TextView txtMsg;
    Button btnThoat;
    View view;
    public RequestFragment() {
        // Required empty public constructor
    }
   @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
       // Inflate the layout for this fragment
       ViewGroup root = (ViewGroup) inflater.inflate(R.layout.fragment_request, container, false);
       txtMsg = view.findViewById(R.id.txtmsg);
       btnThoat = view.findViewById(R.id.btnThoat);


       return root;
   }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        btnThoat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                Intent i = getActivity().getIntent();
                txtMsg.setText("Hello : " + i.getStringExtra("userName"));
                System.exit(0);
            }
        });
    }

}
