package com.ambientdigitalgroup.ambchat.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;

import com.ambientdigitalgroup.ambchat.R;
import com.ambientdigitalgroup.ambchat.activities.StartActivity;
import com.ambientdigitalgroup.ambchat.utils.ProfileUser;
import com.ambientdigitalgroup.ambchat.utils.ProfileUsers;

public class InfoUserFragment extends Fragment {
    private EditText edtFullNameInfo,edtEmailInfo,edtPhoneInfo;
    private Spinner spnAreaPhone;
    private RadioGroup gradGender;
    private RadioButton radMaleInfo,radFemaleInfo;
    private Button btnSaveProfile;
    private Toolbar toolbar;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup root = ( ViewGroup ) inflater.inflate(R.layout.fragment_infor_user,container,false);
        getView(root);
        return root;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        AddEvent();
        getInforUser();
    }

    public void getView(ViewGroup root){
        toolbar=root.findViewById(R.id.app_bar_profile_user);
        (( AppCompatActivity )getActivity()).setSupportActionBar(toolbar);
        ((AppCompatActivity)getActivity()).setTitle(ProfileUsers.getInstance().getFull_name());
        ((AppCompatActivity)getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (getFragmentManager().getBackStackEntryCount() > 0) {
                    getFragmentManager().popBackStack();
                }
            }
        });
        edtFullNameInfo = root.findViewById(R.id.edtFullNameInfo);
        edtEmailInfo = root.findViewById(R.id.edtEmailInfo);
        edtPhoneInfo = root.findViewById(R.id.edtPhoneInfo);
        spnAreaPhone = root.findViewById(R.id.spnAreaPhone);
        gradGender= root.findViewById(R.id.gradGender);
        radMaleInfo = root.findViewById(R.id.radMaleInfo);
        radFemaleInfo = root.findViewById(R.id.radFemaleInfo);
    }
    public  void AddEvent(){
        btnSaveProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //getDataFromForm();
            }
        });
    }

    public void getInforUser(){
        ProfileUsers profile = ProfileUsers.getInstance();
        edtFullNameInfo.setText(profile.getFull_name());
        edtEmailInfo.setText(profile.getEmail());
        edtPhoneInfo.setText(profile.getPhone());

        if(profile.getGender()==1){
            radMaleInfo.setChecked(true);
        }
        else{
            radFemaleInfo.setChecked(true);
        }


    }
}
