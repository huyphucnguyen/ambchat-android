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
        //get view
        getsView();
        //get intent


        //send message
        sendMessage();
        displayChatMessages();
        //send mess
        return  view;
    }
    public void sendMessage(){
        Bundle args = getArguments();
        String username = "";
        if(args!=null){
            username = args.getString(SignInFragment.USERNAME);
        }
        final String finalUsername = username;
        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                db.getInstance()
                        .getReference("message")
                        .push()
                        .setValue(new ChatMessage(
                                edt_Mess.getText().toString(),
                                finalUsername
                        ));
                //clearn the input
                edt_Mess.setText("");
            }
        });
    }

    public void displayChatMessages(){
        ListView listOfMessages = (ListView)view.findViewById(R.id.lv_mess);


         FirebaseListAdapter<ChatMessage> adapter = new FirebaseListAdapter<ChatMessage>(getActivity(),ChatMessage.class,
                R.layout.list_message_items,db.getInstance().getReference("message")) {
            @Override
            protected void populateView(View v, ChatMessage model, int position) {
                // Get references to the views of message.xml
                TextView messageText = (TextView)v.findViewById(R.id.message_text);
                TextView messageUser = (TextView)v.findViewById(R.id.message_user);
                TextView messageTime = (TextView)v.findViewById(R.id.message_time);
                // Set their text
                messageText.setText(model.getMessageText());
                messageUser.setText(model.getMessageUser());
                // Format the date before showing it
                messageTime.setText(DateFormat.format("dd-MM-yyyy (HH:mm:ss)",
                        model.getMessageTime())
                );
            }
        };

        listOfMessages.setAdapter(adapter);
    }
   public void getsView(){
       edt_Mess=view.findViewById(R.id.edt_mess);
       btnSend=view.findViewById(R.id.fab);

   }


}

