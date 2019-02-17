package com.ambientdigitalgroup.ambchat.utils;


import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Base64;

import com.ambientdigitalgroup.ambchat.R;

import java.io.UnsupportedEncodingException;
import java.util.regex.*;
public   class Extension {

    public static int UserID;
    public static String UserName="anymous";

    public static int checkUserName(String userName){
        if(userName.length()<8||userName.length()>30){
            return 0;
        }
        else return 1;
    }

    public static int checkPassWord(String passWord){
        Pattern pattern;
        Matcher matcher;

        String searchString = "\\d";
        pattern = Pattern.compile(searchString, Pattern.CASE_INSENSITIVE);
        matcher = pattern.matcher(passWord);
        //int flag = 0;
        while (matcher.find()) {
            //ton tai ky tu so,pass hop le
            return 1;
        }
        //khong ton tai so
        return 0;
    }

    public static int checkStringIsNumber(String s){
        try{
            double d=Double.parseDouble(s);
        }
        catch(NumberFormatException num) {
            return 0;
        }
        return 1;
    }


    public static int checkEmail(String email) {
        String emailPattern = "^[\\w!#$%&â€™*+/=?`{|}~^-]+(?:\\.[\\w!#$%&â€™*+/=?`{|}~^-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,6}$";
        Pattern regex = Pattern.compile(emailPattern);
        Matcher matcher = regex.matcher(email);
        if (matcher.find()) {
            return 1;//email hop le

        } else {
            return 0;//email khong hop le
        }
    }

    public static void replaceFragment(FragmentManager fgManager,Fragment fragment) {
        FragmentTransaction transaction = null;
        if (fgManager != null) {
            transaction = fgManager.beginTransaction();
            transaction.replace(R.id.flContainer, fragment);
            transaction.addToBackStack(null);
            transaction.commit();
        }
    }

    private static final char[] HEX_CHARS = "0123456789abcdef".toCharArray();

    public static String toHexString(final byte[] bytes) {
        if (bytes == null) {
            return "(null)";
        }
        int slen = bytes.length * 2;
        StringBuilder ret = new StringBuilder(slen);
        for (int i = 0; i < bytes.length; i++) {
            ret.append(HEX_CHARS[(bytes[i] >> 4) & 0xF]);
            ret.append(HEX_CHARS[(bytes[i] & 0xF)]);
        }
        return ret.toString();
    }
}
