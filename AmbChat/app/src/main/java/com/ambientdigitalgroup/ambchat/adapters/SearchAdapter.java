package com.ambientdigitalgroup.ambchat.adapters;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.ambientdigitalgroup.ambchat.R;
import com.ambientdigitalgroup.ambchat.fragments.APIService;
import com.ambientdigitalgroup.ambchat.networks.AddFriendRequest;
import com.ambientdigitalgroup.ambchat.networks.GetListRequestFriend;
import com.ambientdigitalgroup.ambchat.networks.SeverRequest;
import com.ambientdigitalgroup.ambchat.notification.Data;
import com.ambientdigitalgroup.ambchat.notification.MyResponse;
import com.ambientdigitalgroup.ambchat.notification.Sender;
import com.ambientdigitalgroup.ambchat.notification.Token;
import com.ambientdigitalgroup.ambchat.utils.ProfileUsers;
import com.ambientdigitalgroup.ambchat.utils.Result;
import com.ambientdigitalgroup.ambchat.utils.User;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;

public class SearchAdapter extends ArrayAdapter<User> {
    String fuser=String.valueOf(ProfileUsers.getInstance().user_id);
    APIService apiService;
    private boolean notify=false;
    private static final String LOCATION_SEPARATOR = " of ";

    private static final String LOG_TAG = UserAdapter.class.getSimpleName();


    public SearchAdapter(Activity context, ArrayList<User> androidFlavors) {

        super(context, 0, androidFlavors);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Check if the existing view is being reused, otherwise inflate the view
        View listItemView = convertView;
        if(listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.add_friend_item, parent, false);
        }
        // Get the {@link AndroidFlavor} object located at this position in the list
        final User currentAndroidFlavor = getItem(position);
        // Find the TextView in the list_item.xml layout with the ID version_name
        TextView txtFullName = (TextView) listItemView.findViewById(R.id.fullname);
        // Get the version name from the current AndroidFlavor object and
        // set this text on the name TextView
        txtFullName.setText(currentAndroidFlavor.getFull_name());

        final Button btnAdd=(Button) listItemView.findViewById(R.id.btnAdd);

        String userid = String.valueOf(ProfileUsers.getInstance().getUser_id());
        Map<String, String> parameter = new HashMap<>();
        parameter.put("user_id",userid);


        GetListRequestFriend request = new GetListRequestFriend(new SeverRequest.SeverRequestListener() {
            @Override
            public void completed(Object obj) {
                if (obj != null) {
                    final ArrayList<User> arr = (ArrayList<User>)obj;
                    for (int i=0;i<arr.size();i++){
                        if(arr.get(i).getUser_id()==currentAndroidFlavor.getUser_id()){
                            btnAdd.setBackgroundColor(1);
                            btnAdd.setText("");
                            btnAdd.setEnabled(false);
                        }
                    }
                } else {
                    //ERROR
                    Toast.makeText(getContext(), "lOI", Toast.LENGTH_SHORT).show();

                }
            }
        });
        request.execute(parameter);

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               sendRequestAddFriend(String.valueOf(ProfileUsers.getInstance().getUser_id()),String.valueOf(currentAndroidFlavor.getUser_id()));
               btnAdd.setBackgroundColor(1);
               btnAdd.setText("");
               btnAdd.setEnabled(false);
            }
        });

        /*TextView txtStatus=(TextView) listItemView.findViewById(R.id.user_single_status);
        txtStatus.setText(currentAndroidFlavor.getStatus());*/
        return listItemView;
    }

    public void sendRequestAddFriend(String user_id, final String friend_id){
        Map<String, String> parameter = new HashMap<>();
        parameter.put("user_id", user_id);
        parameter.put("friend_id",friend_id);

        AddFriendRequest request = new AddFriendRequest(new SeverRequest.SeverRequestListener() {
            @Override
            public void completed(Object obj) {
                if (obj != null) {

                    final Result res = (Result) obj;
                    if(res.getError()==0){
                     //   notify=true;
                       /* if (notify) {
                            sendNotifiaction(friend_id,ProfileUsers.getInstance().getUser_name(),"Đã gửi yêu cầu kết bạn đến bạn");
                        }
                        notify = false;*/

                        Toast.makeText(getContext(),"yeu cau cua ban da duoc gui",Toast.LENGTH_SHORT).show();
                    }


                } else {
                    //ERROR

                    Toast.makeText(getContext(),"yeu cau cua ban chua duoc gui",Toast.LENGTH_SHORT).show();
                }
            }
        });
        request.execute(parameter);

    }


    public void sendNotifiaction(final String receiver, final String username, final String message){
        DatabaseReference tokens = FirebaseDatabase.getInstance().getReference("Tokens");
        Query query = tokens.orderByKey().equalTo(receiver);
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()){
                    Token token = snapshot.getValue(Token.class);
                    Data data = new Data(fuser, R.mipmap.ic_launcher, username+": "+message, "New Notifiaction",
                            receiver);

                    Sender sender = new Sender(data, token.getToken());

                    apiService.sendNotification(sender)
                            .enqueue(new Callback<MyResponse>() {
                                @Override
                                public void onResponse(Response<MyResponse> response, Retrofit retrofit) {
                                    if (response.code() == 200){
                                        if (response.body().success != 1){
                                           Toast.makeText(getContext(), "Failed!", Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                }

                                @Override
                                public void onFailure(Throwable t) {

                                }


                            });
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }



}
