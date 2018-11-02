package com.ambientdigitalgroup.ambchat.fragments;


import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
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
import com.ambientdigitalgroup.ambchat.utils.User;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;


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
        arrUser = new ArrayList<User>();
        new GetListFriend().execute();

        // Inflate the layout for this fragment
        arrUser.add(new User(1," Ngoc Danh","ThuyDuong"));
        arrUser.add(new User(2," Thi Na","ThuyDuong"));
        arrUser.add(new User(3," Van lam","ThuyDuong"));
        arrUser.add(new User(3," Van lam","ThuyDuong"));
        arrUser.add(new User(3," Van lam","ThuyDuong"));
        arrUser.add(new User(3," Van lam","ThuyDuong"));

          ShowMessage(getContext(),String.valueOf(arrUser.size()));
        UsersAdapter adapter=new UsersAdapter(getActivity(), R.layout.list_items_test,arrUser);
        setListAdapter(adapter);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        mMainView = inflater.inflate(R.layout.fragment_friends, container, false);
        arrUser=new ArrayList<>();
        new  GetListFriend().execute();

        return mMainView;
    }

    class GetListFriend extends AsyncTask<String,Void,String>
    {
        Response response;
        OkHttpClient client=new OkHttpClient.Builder().build();
        @Override
        protected String doInBackground(String... strings) {
            Request request =new Request.Builder()
                    .url(urlGetLitsFriend)
                    .get()
                    .build();

            try {
                response=client.newCall(request).execute();
                return  response.body().string();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return  null;
        }

        @Override
        protected void onPostExecute(String s) {
            if (response.isSuccessful()){
                try {
                    JSONArray array_user= new JSONArray(s);
                    for(int i=0;i<array_user.length();i++){
                        JSONObject ob=array_user.getJSONObject(i);
                        arrUser.add(new User(
                                ob.getInt("User_ID"),
                                ob.getString("User_Name"),
                                ob.getString("Full_Name")
                        ));
                    }
                } catch (Exception e){
                    e.printStackTrace();
                }
            }
            super.onPostExecute(s);
        }
    }

    public  void ShowMessage(final Context context, final String msg) {
        if (context != null && msg != null) {
            new Handler(Looper.getMainLooper()).post(new Runnable() {

                @Override
                public void run() {
                    Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
                }
            });
        }
    }




}
