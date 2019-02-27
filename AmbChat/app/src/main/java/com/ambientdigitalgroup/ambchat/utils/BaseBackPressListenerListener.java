package com.ambientdigitalgroup.ambchat.utils;

import android.support.v4.app.FragmentActivity;

public class BaseBackPressListenerListener implements OnBackPressListener {
    private final FragmentActivity activity;
    public BaseBackPressListenerListener(FragmentActivity activity){
        this.activity = activity;
    }
    @Override
    public void doBack() {
        //activity.getSupportFragmentManager().popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
        activity.finish();
    }
}
