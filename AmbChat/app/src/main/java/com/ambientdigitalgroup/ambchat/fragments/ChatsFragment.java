package com.ambientdigitalgroup.ambchat.fragments;


import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.ambientdigitalgroup.ambchat.R;
import com.ambientdigitalgroup.ambchat.activities.StartActivity;
import com.ambientdigitalgroup.ambchat.adapters.MessageAdapter;
import com.ambientdigitalgroup.ambchat.networks.GetFriendsRequest;
import com.ambientdigitalgroup.ambchat.networks.SeverRequest;
import com.ambientdigitalgroup.ambchat.notification.Token;
import com.ambientdigitalgroup.ambchat.utils.BaseBackPressListenerListener;
import com.ambientdigitalgroup.ambchat.utils.Chatlist;
import com.ambientdigitalgroup.ambchat.utils.ProfileUsers;
import com.ambientdigitalgroup.ambchat.utils.Result;
import com.ambientdigitalgroup.ambchat.utils.User;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.iid.FirebaseInstanceId;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * A simple {@link Fragment} subclass.
 */
public class
ChatsFragment extends Fragment {



    View view;
    FirebaseDatabase db;
    private RecyclerView recyclerView;

    private MessageAdapter.ChatsUserAdapter chatUserAdapter;
    private List<User> mUsers;

    String  mCurentUserID;
    DatabaseReference reference;

    private List<Chatlist> usersList;
    public ChatsFragment() {
        // Required empty public constructor

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_chats, container, false);


        Activity activity = getActivity();
        ((StartActivity)activity).setOnBackPressListener(new BaseBackPressListenerListener(( FragmentActivity ) activity));


        recyclerView = view.findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        mCurentUserID = String.valueOf(ProfileUsers.getInstance().user_id);

        usersList = new ArrayList<>();

        reference = FirebaseDatabase.getInstance().getReference("Chatlist").child(mCurentUserID);
        reference.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                usersList.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()){
                    Chatlist chatlist = snapshot.getValue(Chatlist.class);
                    usersList.add(chatlist);
                }

                chatList();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        updateToken(FirebaseInstanceId.getInstance().getToken());

        return view;
    }

    private void updateToken(String token){
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Tokens");
        Token token1 = new Token(token);
        reference.child(mCurentUserID).setValue(token1);
    }

    private void chatList() {
       /* mUsers = new ArrayList<>();
        reference = FirebaseDatabase.getInstance().getReference("Users");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                mUsers.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()){
                    User user = snapshot.getValue(User.class);
                    for (Chatlist chatlist : usersList){
                        // if (user.getId().equals(chatlist.getId())){
                        if(user.getId().compareTo(chatlist.getId())==0)
                            mUsers.add(user);
                        // }
                    }
                }
                userAdapter = new UserAdapter(getContext(), mUsers, true);
                recyclerView.setAdapter(userAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });*/
        Map<String, String> parameter = new HashMap<>();
        mUsers = new ArrayList<>();
        GetFriendsRequest request = new GetFriendsRequest(new SeverRequest.SeverRequestListener() {
            @Override
            public void completed(Object obj) {

                if (obj != null) {
                    Result res = (Result) obj;

                    final ArrayList<User> arr = (ArrayList<User>) res.getData();

                    for (int i=0;i<usersList.size();i++){
                        for(int j=0;j<arr.size();j++){
                            if(String.valueOf(arr.get(j).getUser_id()).compareTo(usersList.get(i).id)==0)
                                mUsers.add(arr.get(j));
                            // }
                        }
                    }
                    chatUserAdapter = new MessageAdapter.ChatsUserAdapter(getContext(), mUsers, true);
                    recyclerView.setAdapter(chatUserAdapter);
                  /*  lvListFriends.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                            User us = arr.get(i);
                            Bundle bundle = new Bundle();
                            bundle.putInt("UserID", us.getUser_id());
                            ConvertionFragment fConv = new ConvertionFragment();
                            fConv.setArguments(bundle);

                            replaceFragment(fConv);
                        }
                    });*/

                } else {
                    //ERROR

                    Toast.makeText(getActivity().getBaseContext(), "lOI", Toast.LENGTH_SHORT).show();

                    Toast.makeText(getActivity().getBaseContext(),"Khong co danh sach ban be",Toast.LENGTH_SHORT).show();

                }
            }
        });
        request.execute(parameter);

    }

    @Override
    public void onResume() {
        super.onResume();

    }
}

