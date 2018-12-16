package com.ambientdigitalgroup.ambchat.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.ambientdigitalgroup.ambchat.R;
import com.ambientdigitalgroup.ambchat.utils.User;

import java.util.List;

public class UsersAdapter extends BaseAdapter{


    private Context context;
    private int layout;
    private List<User> userList;

    public UsersAdapter(Context context, int layout, List<User> usersList) {
        this.context = context;
        this.layout = layout;
        this.userList = usersList;
    }

    @Override
    public int getCount() {
        return userList.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }


    private class  ViewHolder{
        TextView txtDisplayName;
    }
    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
       ViewHolder holder;
       if(view==null){
           holder=new ViewHolder();
           LayoutInflater inflater=(LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
           view=inflater.inflate(layout,null);
           holder.txtDisplayName=(TextView) view.findViewById(R.id.user_single_name);
           view.setTag(holder);
       }else{
           holder =(ViewHolder) view.getTag();
       }
       //lay du lieu tu array list(list)
       User us=userList.get(i);
       //chay sau MainActivity.java
       holder.txtDisplayName.setText(us.getUser_name());
       return view;
    }


}
