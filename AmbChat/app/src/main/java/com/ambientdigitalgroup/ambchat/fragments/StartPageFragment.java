package com.ambientdigitalgroup.ambchat.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.ambientdigitalgroup.ambchat.R;
import com.ambientdigitalgroup.ambchat.utils.Extension;

public class StartPageFragment extends Fragment {
    private Button lbtnSigIn;
    private Button lbtnSigUp;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup root = ( ViewGroup ) inflater.inflate(R.layout.fragment_start_page,container,false);

        getView(root);
        return root;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        addEvent();
    }

    public void getView(ViewGroup root){
        lbtnSigIn =(Button) root.findViewById(R.id.btnLinkSigIn);
        lbtnSigUp=(Button) root.findViewById(R.id.btnLinkRegister);

    }


    public  void addEvent(){
        //link sign in click
        lbtnSigIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //open fragment sign_in
                Fragment fragment = new SignInFragment();
                addFragment(getFragmentManager(),fragment,"SIGN_IN");
            }
        });

        lbtnSigUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Opent fragment sign_up
                Fragment fragment = new SignUpFragment();
                addFragment(getFragmentManager(),fragment,"SIGN_UP");
            }
        });
    }

    public void addFragment(FragmentManager fgManager, Fragment fragment,String tagName) {
        FragmentTransaction transaction = null;

        if (fgManager != null) {
            transaction = fgManager.beginTransaction();
            transaction.replace(R.id.flContainer, fragment,tagName);
            transaction.addToBackStack("SIGN_IN");
            transaction.commit();
        }
    }


}
