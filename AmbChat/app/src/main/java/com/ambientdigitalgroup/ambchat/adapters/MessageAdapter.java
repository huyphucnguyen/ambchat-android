package com.ambientdigitalgroup.ambchat.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.ambientdigitalgroup.ambchat.R;
import com.ambientdigitalgroup.ambchat.activities.StartActivity;
import com.ambientdigitalgroup.ambchat.utils.Chat;
import com.ambientdigitalgroup.ambchat.utils.ProfileUsers;
import com.ambientdigitalgroup.ambchat.utils.User;
import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


import java.util.List;

public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.ViewHolder> {

    public static  final int MSG_TYPE_LEFT = 0;
    public static  final int MSG_TYPE_RIGHT = 1;

    private Context mContext;
    private List<Chat> mChat;
    private String imageurl;

    FirebaseUser fuser;

    public MessageAdapter(Context mContext, List<Chat> mChat, String imageurl){
        this.mChat = mChat;
        this.mContext = mContext;
        this.imageurl = imageurl;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == MSG_TYPE_RIGHT) {
            View view = LayoutInflater.from(mContext).inflate(R.layout.chat_item_right, parent, false);
            return new ViewHolder(view);
        } else {
            View view = LayoutInflater.from(mContext).inflate(R.layout.chat_item_left, parent, false);
            return new ViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        Chat chat = mChat.get(position);

        holder.show_message.setText(chat.getMessage());

        if (imageurl.equals("default")){
            holder.profile_image.setImageResource(R.mipmap.ic_launcher);
        } else {
            Glide.with(mContext).load(imageurl).into(holder.profile_image);
        }

        if (position == mChat.size()-1){
            if (chat.isIsseen()){
                holder.txt_seen.setText("Seen");
            } else {
                holder.txt_seen.setText("Delivered");
            }
        } else {
            holder.txt_seen.setVisibility(View.GONE);
        }

    }

    @Override
    public int getItemCount() {
        return mChat.size();
    }

    public  class ViewHolder extends RecyclerView.ViewHolder{

        public TextView show_message;
        public ImageView profile_image;
        public TextView txt_seen;

        public ViewHolder(View itemView) {
            super(itemView);

            show_message = itemView.findViewById(R.id.show_message);
            profile_image = itemView.findViewById(R.id.profile_image);
            txt_seen = itemView.findViewById(R.id.txt_seen);
        }
    }

    @Override
    public int getItemViewType(int position) {

        if (mChat.get(position).getSender().compareTo(String.valueOf(ProfileUsers.getInstance().getUser_id()))==0){
            return MSG_TYPE_RIGHT;
        } else {
            return MSG_TYPE_LEFT;
        }
    }

    public static class ChatsUserAdapter extends RecyclerView.Adapter<ChatsUserAdapter.ViewHolder> {

        private Context mContext;
        private List<User> mUsers;
        private boolean ischat;

        String theLastMessage;

        public ChatsUserAdapter(Context mContext, List<User> mUsers, boolean ischat){
            this.mUsers = mUsers;
            this.mContext = mContext;
            this.ischat = ischat;
        }

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(mContext).inflate(R.layout.user_item, parent, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

            final User user = mUsers.get(position);
            holder.username.setText(user.getUser_name());
           /* if (user.getImageURL().equals("default")){

            } else {
                Glide.with(mContext).load(user.getImageURL()).into(holder.profile_image);
            }
    */
            holder.profile_image.setImageResource(R.drawable.ic_account);
            lastMessage(String.valueOf(user.getUser_id()), holder.last_msg);
            if (ischat){
                lastMessage(String.valueOf(user.getUser_id()), holder.last_msg);
            } else {
                holder.last_msg.setVisibility(View.GONE);
            }

            if (ischat){
                if (user.getStatus().equals("online")){
                    holder.img_on.setVisibility(View.VISIBLE);
                    holder.img_off.setVisibility(View.GONE);
                } else {
                    holder.img_on.setVisibility(View.GONE);
                    holder.img_off.setVisibility(View.VISIBLE);
                }
            } else {
                holder.img_on.setVisibility(View.GONE);
                holder.img_off.setVisibility(View.GONE);
            }

            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //sau sau
                    Intent intent = new Intent(mContext, StartActivity.class);
                    intent.putExtra("userid", user.getUser_id());
                    mContext.startActivity(intent);
                }
            });
        }

        @Override
        public int getItemCount() {
            return mUsers.size();
        }

        public  class ViewHolder extends RecyclerView.ViewHolder{

            public TextView username;
            public ImageView profile_image;
            private ImageView img_on;
            private ImageView img_off;
            private TextView last_msg;

            public ViewHolder(View itemView) {
                super(itemView);

                username = itemView.findViewById(R.id.username);
                profile_image = itemView.findViewById(R.id.profile_image);
                img_on = itemView.findViewById(R.id.img_on);
                img_off = itemView.findViewById(R.id.img_off);
                last_msg = itemView.findViewById(R.id.last_msg);
            }
        }

        //check for last message
        private void lastMessage(final String userid, final TextView last_msg){

            // add user to chat fragment
        /*    final DatabaseReference chatRef = FirebaseDatabase.getInstance().getReference("Chatlist")
                    .child(String.valueOf(ProfileUsers.getInstance().getUser_id()))
                    .child(userid);

            chatRef.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                    Chatlist value = dataSnapshot.getValue(Chatlist.class);
                    last_msg.setText(value.getLast_mess());
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    last_msg.setText("Error while reading data");
                }
            });*/

           // last_msg.setText("no message");

            theLastMessage = "default";
           final String myID=String.valueOf(ProfileUsers.getInstance().user_id);
            DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Chats");

            reference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()){
                        Chat chat = snapshot.getValue(Chat.class);
                        if (myID != null && chat != null) {
                            if (chat.getReceiver().equals(myID) && chat.getSender().equals(userid) ||
                                    chat.getReceiver().equals(userid) && chat.getSender().equals(myID)) {
                                theLastMessage = chat.getMessage();
                            }
                        }
                    }

                    switch (theLastMessage){
                        case  "default":
                            last_msg.setText("No Message");
                            break;

                        default:
                            last_msg.setText(theLastMessage);
                            break;
                    }

                    theLastMessage = "default";
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });

        }
    }
}