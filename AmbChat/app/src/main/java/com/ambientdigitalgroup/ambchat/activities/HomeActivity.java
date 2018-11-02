package com.ambientdigitalgroup.ambchat.activities;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.ambientdigitalgroup.ambchat.R;
import com.ambientdigitalgroup.ambchat.adapters.UserAdapter;
import com.ambientdigitalgroup.ambchat.fragments.InforUserFragment;
import com.ambientdigitalgroup.ambchat.utils.User;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class HomeActivity extends AppCompatActivity {
    ImageView imgAcount;
    ImageView imgListFriend;
    ImageView imgMessage;
    public static final String urlGetLitsFriend = "https://ambchat.herokuapp.com/api/getlistfriend.php";
    ArrayList<User> arrUser;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        arrUser = new ArrayList<User>();
        new GetListFriend().execute();
        ShowMessage(getBaseContext(), String.valueOf(arrUser.size()));
        // Find a reference to the {@link ListView} in the layout
        ListView lvListFriend = (ListView) findViewById(R.id.lvListFriends);
        UserAdapter adapter = new UserAdapter(this, arrUser);
        lvListFriend.setAdapter(adapter);
        new GetListFriend().execute();
        GetView();
        AddEvent();
    }

    class GetListFriend extends AsyncTask<String, Void, String> {
        Response response;
        OkHttpClient client = new OkHttpClient.Builder().build();

        @Override
        protected String doInBackground(String... strings) {
            Request request = new Request.Builder()
                    .url(urlGetLitsFriend)
                    .get()
                    .build();

            try {
                response = client.newCall(request).execute();
                return response.body().string();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            if (response.isSuccessful()) {
                try {
                    JSONArray array_user = new JSONArray(s);
                    for (int i = 0; i < array_user.length(); i++) {
                        JSONObject ob = array_user.getJSONObject(i);
                        arrUser.add(new User(
                                ob.getInt("User_ID"),
                                ob.getString("User_Name"),
                                ob.getString("Full_Name")
                        ));
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            super.onPostExecute(s);
        }
    }

    public void ShowMessage(final Context context, final String msg) {
        if (context != null && msg != null) {
            new Handler(Looper.getMainLooper()).post(new Runnable() {

                @Override
                public void run() {
                    Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    public void GetView() {
        imgAcount = findViewById(R.id.imgAcount);
        imgListFriend = findViewById(R.id.imgFriend);
        imgMessage = findViewById(R.id.imgListMess);
    }

    public void AddEvent() {
        imgAcount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent int_ActivityUser_Infor = new Intent(HomeActivity.this, InforUserFragment.class);
                startActivity(int_ActivityUser_Infor);
            }
        });

    }
}
