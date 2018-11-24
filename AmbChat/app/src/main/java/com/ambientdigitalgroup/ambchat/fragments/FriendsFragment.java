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
      /*  arrUser = new ArrayList<User>();
        // Inflate the layout for this fragment
        arrUser.add(new User(1," Ngoc Danh","ThuyDuong"));
        arrUser.add(new User(2," Thi Na","ThuyDuong"));
        arrUser.add(new User(3," Van lam","ThuyDuong"));
        arrUser.add(new User(3," Van lam","ThuyDuong"));
        arrUser.add(new User(3," Van lam","ThuyDuong"));
        arrUser.add(new User(3," Van lam","ThuyDuong"));
*/



////////////////////////////////////
        Map<String, String> parameter = new HashMap<>();
        GetFriendsRequest request = new GetFriendsRequest(new SeverRequest.SeverRequestListener() {
            @Override
            public void completed(Object obj) {

                if (obj != null) {
                    ArrayList<User> arr= (ArrayList<User>) obj;
                    UsersAdapter adapter=new UsersAdapter(getActivity(), R.layout.list_items_test,arr);
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
