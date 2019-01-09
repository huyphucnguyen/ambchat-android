package com.ambientdigitalgroup.ambchat.fragments;


import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.ambientdigitalgroup.ambchat.R;
import com.ambientdigitalgroup.ambchat.utils.Extension;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ServerValue;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;
import java.util.Timer;


/**
 * A simple {@link Fragment} subclass.
 */
public class ChatsFragment extends Fragment {


    EditText edt_Mess;
    FloatingActionButton btnSend;
    ListView lvMess;
    View view;
    FirebaseDatabase db;
    private int mCurrentUserId;
    private int mChatUser;
    private EditText mChatMessageView;
    private DatabaseReference mRootRef;
    private ImageView imgSend;

    public ChatsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.chat_dashboard, container, false);
        Timer t = new Timer();
        final int time = 0;

        // Danh sửa chỗ này nhé
        mChatUser = 1;
        mCurrentUserId = Extension.UserID;
        mRootRef = FirebaseDatabase.getInstance().getReference();
        mChatMessageView = (EditText)getActivity().findViewById(R.id.edtMess);


        mRootRef.child("Chat").child(String.valueOf(mCurrentUserId)).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                if (!dataSnapshot.hasChild(String.valueOf(mChatUser))) {

                    Map chatAddMap = new HashMap();
                    chatAddMap.put("timestamp", ServerValue.TIMESTAMP);
                    // Có thể bỏ dòng 78 . Test
                    chatAddMap.put("message", "Hello, I am test tin nhan");


                    Map chatUserMap = new HashMap();
                    chatUserMap.put("Chat/" + mCurrentUserId + "/" + mChatUser, chatAddMap);
                    chatUserMap.put("Chat/" + mChatUser + "/" + mCurrentUserId, chatAddMap);


                    mRootRef.updateChildren(chatUserMap, new DatabaseReference.CompletionListener() {
                        @Override
                        public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {

                            if (databaseError != null) {

                                Log.d("CHAT_LOG", databaseError.getMessage().toString());

                            }

                        }
                    });

                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        imgSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                sendMessage();

            }
        });

        return view;
    }

    private void sendMessage() {


        String message = mChatMessageView.getText().toString();

        if (!TextUtils.isEmpty(message)) {

            String current_user_ref = "messages/" + mCurrentUserId + "/" + mChatUser;
            String chat_user_ref = "messages/" + mChatUser + "/" + mCurrentUserId;

            DatabaseReference user_message_push = mRootRef.child("messages")
                    .child(String.valueOf(mCurrentUserId)).child(String.valueOf(mChatUser)).push();

            String push_id = user_message_push.getKey();

            Map messageMap = new HashMap();
            messageMap.put("message", message);
            messageMap.put("time", ServerValue.TIMESTAMP);
            messageMap.put("from", mCurrentUserId);

            Map messageUserMap = new HashMap();
            messageUserMap.put(current_user_ref + "/" + push_id, messageMap);
            messageUserMap.put(chat_user_ref + "/" + push_id, messageMap);

            mChatMessageView.setText("");

            mRootRef.child("Chat").child(String.valueOf(mCurrentUserId)).child(String.valueOf(mChatUser)).child("seen").setValue(true);
            mRootRef.child("Chat").child(String.valueOf(mCurrentUserId)).child(String.valueOf(mChatUser)).child("timestamp").setValue(ServerValue.TIMESTAMP);

            mRootRef.child("Chat").child(String.valueOf(mChatUser)).child(String.valueOf(mCurrentUserId)).child("seen").setValue(false);
            mRootRef.child("Chat").child(String.valueOf(mChatUser)).child(String.valueOf(mCurrentUserId)).child("timestamp").setValue(ServerValue.TIMESTAMP);

            mRootRef.updateChildren(messageUserMap, new DatabaseReference.CompletionListener() {
                @Override
                public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {

                    if (databaseError != null) {

                        Log.d("CHAT_LOG", databaseError.getMessage().toString());

                    }

                }
            });

        }

    }


}

