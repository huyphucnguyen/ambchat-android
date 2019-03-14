package com.ambientdigitalgroup.ambchat.adapters;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.ambientdigitalgroup.ambchat.R;
import com.ambientdigitalgroup.ambchat.utils.User;

import java.util.ArrayList;

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
        User currentAndroidFlavor = getItem(position);
        // Find the TextView in the list_item.xml layout with the ID version_name
        TextView txtFullName = (TextView) listItemView.findViewById(R.id.fullname);
        // Get the version name from the current AndroidFlavor object and
        // set this text on the name TextView
        txtFullName.setText(currentAndroidFlavor.getFull_name());

        Button btnAdd=(Button) listItemView.findViewById(R.id.btnAdd);
        /*TextView txtStatus=(TextView) listItemView.findViewById(R.id.user_single_status);
        txtStatus.setText(currentAndroidFlavor.getStatus());*/
        return listItemView;
    }
}
