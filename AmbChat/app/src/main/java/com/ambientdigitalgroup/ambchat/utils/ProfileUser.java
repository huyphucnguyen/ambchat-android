package com.ambientdigitalgroup.ambchat.utils;
import com.google.gson.annotations.SerializedName;

import java.util.Date;

public class ProfileUser {
   @SerializedName("user_name")
   public String user_name;
   @SerializedName("full_name")
   public String full_name;
   @SerializedName("picture")
   public String picture;
   @SerializedName("email")
   public String email;
   @SerializedName("date_create")
   public Date date_create;
   @SerializedName("gender")
   public int gender;
   @SerializedName("user_id")
   public int user_id;

   public String getUser_name() {
      return user_name;
   }

   public String getPicture() {
      return picture;
   }
   public String getEmail() {
      return email;
   }

   public Date getDate_create() {
      return date_create;
   }

   public int getGender() {
      return gender;
   }

   public int getUser_id() {
      return user_id;
   }

   public void setUser_name(String user_name) {
      this.user_name = user_name;
   }

   public void setPicture(String picture) {
      this.picture = picture;
   }

   public void setEmail(String email) {
      this.email = email;
   }

   public void setDate_create(Date date_create) {
      this.date_create = date_create;
   }

   public void setGender(int gender) {
      this.gender = gender;
   }

   public void setUser_id(int user_id) {
      this.user_id = user_id;
   }

   public ProfileUser(String user_name, String full_name, String picture, String email, Date date_create, int gender, int user_id) {
      this.user_name = user_name;
      this.full_name = full_name;
      this.picture = picture;
      this.email = email;
      this.date_create = date_create;
      this.gender = gender;
      this.user_id = user_id;
   }
}
