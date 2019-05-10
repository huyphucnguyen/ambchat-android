package com.ambientdigitalgroup.ambchat.fragments;


import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.ambientdigitalgroup.ambchat.R;
import com.ambientdigitalgroup.ambchat.activities.StartActivity;
import com.ambientdigitalgroup.ambchat.adapters.UserAdapter;
import com.ambientdigitalgroup.ambchat.networks.GetFriendsRequest;
import com.ambientdigitalgroup.ambchat.networks.SeverRequest;
import com.ambientdigitalgroup.ambchat.notification.Token;
import com.ambientdigitalgroup.ambchat.utils.BaseBackPressListenerListener;
import com.ambientdigitalgroup.ambchat.utils.Extension;
import com.ambientdigitalgroup.ambchat.utils.ProfileUsers;
import com.ambientdigitalgroup.ambchat.utils.Result;
import com.ambientdigitalgroup.ambchat.utils.User;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.iid.FirebaseInstanceId;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


/**
 * A simple {@link Fragment} subclass.
 */
public class FriendsFragment extends Fragment {

    private View mMainView;
    private String mCurrentUserId;
    ListView lvListFriends;
    public static final String urlGetLitsFriend = "https://ambchat.herokuapp.com/api/getlistfriend.php";
    ArrayList<User> arrUser;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        mMainView = inflater.inflate(R.layout.fragment_friends, container, false);
        mCurrentUserId = String.valueOf(Extension.UserID);
        Map<String, String> parameter = new HashMap<>();
        parameter.put("user_id",String.valueOf(ProfileUsers.getInstance().user_id));
        lvListFriends = mMainView.findViewById(R.id.lvFriends);
        GetFriendsRequest request = new GetFriendsRequest(new SeverRequest.SeverRequestListener() {
            @Override
            public void completed(Object obj) {
                if (obj != null) {

                    final ArrayList<User> arr = (ArrayList<User>)obj;
                    UserAdapter adapter = new UserAdapter(getActivity(),arr);
                    lvListFriends.setAdapter(adapter);
                    lvListFriends.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                         @Override
                        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                            User us = arr.get(i);
                            Bundle bundle = new Bundle();
                            bundle.putInt("UserID", us.getUser_id());
                            ConvertionFragment fConv = new ConvertionFragment();
                            fConv.setArguments(bundle);
                             replaceFragment(fConv);
                        }
                    });

                } else {
                    //ERROR
                    Toast.makeText(getActivity().getBaseContext(), "lOI", Toast.LENGTH_SHORT).show();
                    Toast.makeText(getActivity().getBaseContext(),"Khong co danh sach ban be",Toast.LENGTH_SHORT).show();
                }
            }
        });
        request.execute(parameter);
        updateToken(FirebaseInstanceId.getInstance().getToken());

        return mMainView;
    }
    private void updateToken(String token){
        DatabaseReference databaseReference= FirebaseDatabase.getInstance().getReference("Tokens");
        Token token1=new Token(token);
        databaseReference.child(mCurrentUserId).setValue(token1);

    }


    private void replaceFragment(Fragment fConv) {
        if(getChildFragmentManager()!=null){
            FragmentManager manager = getActivity().getSupportFragmentManager();
            FragmentTransaction transaction = manager.beginTransaction();
            transaction.add(R.id.flContainer, fConv,"chat");
            transaction.addToBackStack(null);
            transaction.commit();
        }

    }

    @Override
    public void onResume() {
        super.onResume();
        mCurrentUserId = String.valueOf(Extension.UserID);
    }

    @Override
    public void onStart() {
        super.onStart();
        mCurrentUserId = String.valueOf(Extension.UserID);
    }
}
