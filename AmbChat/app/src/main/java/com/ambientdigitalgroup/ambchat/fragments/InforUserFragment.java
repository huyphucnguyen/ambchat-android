package com.ambientdigitalgroup.ambchat.fragments;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;

import com.ambientdigitalgroup.ambchat.R;
import com.ambientdigitalgroup.ambchat.utils.ProfileUser;

public class InforUserFragment extends AppCompatActivity {
    EditText edtUserNameInfor;
    EditText edtPassWordInfor;
    EditText edtFullNameInfor;
    EditText edtEmailInfor;
    RadioButton rdoMaleInfor;
    RadioButton rdoFeMaleInfor;
    Button btnUpdateInforUser;
    Button btnUnfriend;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState, @Nullable PersistableBundle persistentState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_infor_user);
       getView();
        AddEvent();
       getInforUser();;
    }

    public void getView(){
        edtUserNameInfor = findViewById(R.id.edtUserNameInfor);
        edtFullNameInfor = findViewById(R.id.edtFullNameInfor);
        edtPassWordInfor = findViewById(R.id.edtPassWordInfor);
        edtEmailInfor    = findViewById(R.id.edtEmailInfor);
        rdoMaleInfor=findViewById(R.id.radMaleInfor);
        rdoFeMaleInfor=findViewById(R.id.radFemaleInfor);
        btnUpdateInforUser=findViewById(R.id.btnUpdateInfor);
    }
    public  void AddEvent(){
    }

    public void getInforUser(){
        /*edtUserNameInfor.setText(ProfileUser.username);
        edtFullNameInfor.setText(ProfileUser.fullname);
        edtPassWordInfor.setText(ProfileUser.passsword);
        edtEmailInfor.setText(ProfileUser.email);
        if(ProfileUser.gender==1){
           // rdoMaleInfor.setfo
        }*/

    }
}
