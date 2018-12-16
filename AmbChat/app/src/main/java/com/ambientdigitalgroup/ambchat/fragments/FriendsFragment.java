package com.ambientdigitalgroup.ambchat.fragments;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import com.ambientdigitalgroup.ambchat.R;
import com.ambientdigitalgroup.ambchat.adapters.UsersAdapter;
import com.ambientdigitalgroup.ambchat.networks.GetFriendsRequest;
import com.ambientdigitalgroup.ambchat.networks.SeverRequest;
import com.ambientdigitalgroup.ambchat.utils.Result;
import com.ambientdigitalgroup.ambchat.utils.User;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


/**
 * A simple {@link Fragment} subclass.
 */
public class FriendsFragment extends ListFragment {

    private View mMainView;
    ListView lvListFriends;
    public static final String urlGetLitsFriend = "https://ambchat.herokuapp.com/api/getlistfriend.php";
    ArrayList<User> arrUser;
    public FriendsFragment() {
        // Required empty public constructor
    }

    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Map<String, String> parameter = new HashMap<>();
        GetFriendsRequest request = new GetFriendsRequest(new SeverRequest.SeverRequestListener() {
            @Override
            public void completed(Object obj) {

                if (obj != null) {
                    Result res=(Result) obj;
                    ArrayList<User> arr= (ArrayList<User>) res.getData();
                    UsersAdapter adapter=new UsersAdapter(getActivity(), R.layout.list_friend_items,arr);
                    setListAdapter(adapter);

                } else {
                    //ERROR
                    Toast.makeText(getActivity().getBaseContext(),"lOI",Toast.LENGTH_SHORT).show();
                }
            }
        });
        request.execute(parameter);


    }




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        mMainView = inflater.inflate(R.layout.fragment_friends, container, false);
        return mMainView;
    }






}
