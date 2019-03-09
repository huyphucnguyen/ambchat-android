package com.ambientdigitalgroup.ambchat.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;

import com.ambientdigitalgroup.ambchat.R;
import com.ambientdigitalgroup.ambchat.activities.StartActivity;

public class InfoUserFragment extends Fragment {
    EditText edtUserNameInfor;
    EditText edtPassWordInfor;
    EditText edtFullNameInfor;
    EditText edtEmailInfor;
    RadioButton rdoMaleInfor;
    RadioButton rdoFeMaleInfor;
    Button btnUpdateInforUser;
    Button btnUnfriend;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup root = ( ViewGroup ) inflater.inflate(R.layout.fragment_infor_user,container,false);
        getView(root);

        FragmentActivity activity = getActivity();
        ((StartActivity )activity).setOnBackPressListener(null);

        return root;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        AddEvent();
        getInforUser();
    }

    public void getView(ViewGroup root){
        edtUserNameInfor = root.findViewById(R.id.edtUserNameInfor);
        edtFullNameInfor = root.findViewById(R.id.edtFullNameInfor);
        edtPassWordInfor = root.findViewById(R.id.edtPassWordInfor);
        edtEmailInfor    = root.findViewById(R.id.txtEmailInfor);
        rdoMaleInfor = root.findViewById(R.id.radMaleInfor);
        rdoFeMaleInfor= root.findViewById(R.id.radFemaleInfor);
        btnUpdateInforUser = root.findViewById(R.id.btnUpdateInfor);
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
