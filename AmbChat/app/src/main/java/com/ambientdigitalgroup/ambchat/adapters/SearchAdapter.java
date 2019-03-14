package com.ambientdigitalgroup.ambchat.adapters;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.ambientdigitalgroup.ambchat.R;
import com.ambientdigitalgroup.ambchat.fragments.ConvertionFragment;
import com.ambientdigitalgroup.ambchat.networks.AddFriendRequest;
import com.ambientdigitalgroup.ambchat.networks.GetFriendsRequest;
import com.ambientdigitalgroup.ambchat.networks.SeverRequest;
import com.ambientdigitalgroup.ambchat.utils.ProfileUsers;
import com.ambientdigitalgroup.ambchat.utils.Result;
import com.ambientdigitalgroup.ambchat.utils.User;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class SearchAdapter extends ArrayAdapter<User> {

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
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               sendRequestAddFriend(String.valueOf(ProfileUsers.getInstance().getUser_id()),String.valueOf(currentAndroidFlavor.getUser_id()));
               btnAdd.setBackgroundColor(1);
               btnAdd.setEnabled(false);
            }
        });
        /*TextView txtStatus=(TextView) listItemView.findViewById(R.id.user_single_status);
        txtStatus.setText(currentAndroidFlavor.getStatus());*/
        return listItemView;
    }

    public void sendRequestAddFriend(String user_id,String friend_id){
        Map<String, String> parameter = new HashMap<>();
        parameter.put("user_id", user_id);
        parameter.put("friend_id",friend_id);

        AddFriendRequest request = new AddFriendRequest(new SeverRequest.SeverRequestListener() {
            @Override
            public void completed(Object obj) {
                if (obj != null) {

                    final Result res = (Result) obj;
                    if(res.getError()==0){
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
}
