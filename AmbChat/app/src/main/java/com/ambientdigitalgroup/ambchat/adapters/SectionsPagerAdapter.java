package com.ambientdigitalgroup.ambchat.adapters;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.ambientdigitalgroup.ambchat.fragments.ChatsFragment;
import com.ambientdigitalgroup.ambchat.fragments.FriendsFragment;
import com.ambientdigitalgroup.ambchat.fragments.RequestFragment;

public class SectionsPagerAdapter extends FragmentPagerAdapter {


    public SectionsPagerAdapter(FragmentManager fragmentManager) {
        super(fragmentManager);
    }

    @Override
    public Fragment getItem(int position) {
       switch (position){
           case 0:
               ChatsFragment chatsFragment=new ChatsFragment();
               return  chatsFragment;
           case 1:
               FriendsFragment friendsFragment=new FriendsFragment();
               return  friendsFragment;
           case 2:
               RequestFragment requestFragment=new RequestFragment();
               return  requestFragment;
            default:
                return null;
       }

    }

    @Override
    public int getCount() {
        return 3;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        switch (position){
            case 0:
                return "CHATS";
            case 1:
                return "FRIENDS";
            case 2:
                return "REQUESTS";
            default:
                return null;
        }
    }
}
