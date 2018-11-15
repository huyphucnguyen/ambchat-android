package com.ambientdigitalgroup.ambchat;
import com.google.gson.annotations.SerializedName;
public class Employees {
    @SerializedName("first_name")
    private String mFirstName;
    @SerializedName("age")
    private int mAge;
    @SerializedName("mail")
    private String mMail;

    public Employees(String firstName, int age, String mail) {
        mFirstName = firstName;
        mAge = age;
        mMail = mail;
    }

    public String getmFirstName() {
        return mFirstName;
    }

    public int getmAge() {
        return mAge;
    }

    public String getmMail() {
        return mMail;
    }
}
