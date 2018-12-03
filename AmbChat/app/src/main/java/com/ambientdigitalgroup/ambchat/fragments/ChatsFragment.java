package com.ambientdigitalgroup.ambchat.fragments;


import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.ambientdigitalgroup.ambchat.R;
import com.ambientdigitalgroup.ambchat.utils.ChatMessage;
import com.firebase.ui.database.FirebaseListAdapter;
import com.google.firebase.database.FirebaseDatabase;


/**
 * A simple {@link Fragment} subclass.
 */
public class ChatsFragment extends Fragment {


    EditText edt_Mess;
    FloatingActionButton btnSend;
    ListView lvMess;
    View view;
    FirebaseDatabase db;

    public ChatsFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view= inflater.inflate(R.layout.fragment_chats, container, false);

        return  view;
    }



}

