package com.ambientdigitalgroup.ambchat.fragments;



import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.ambientdigitalgroup.ambchat.R;
import com.ambientdigitalgroup.ambchat.adapters.SectionsPagerAdapter;
import com.ambientdigitalgroup.ambchat.utils.Extension;


public class MainFragment extends Fragment {


    private android.support.v7.widget.Toolbar mToolBar;

    private ViewPager mViewPager;
    private SectionsPagerAdapter mSectinosPagerAdapter;

    private TabLayout mTabLayout;


    ViewGroup root;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        root = ( ViewGroup ) inflater.inflate(R.layout.fragment_main,container,false);
        getView(root);


        ((AppCompatActivity)getActivity()).setSupportActionBar(mToolBar);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle("AbmChat");
        ((AppCompatActivity)getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //tab
        mSectinosPagerAdapter =new SectionsPagerAdapter(getFragmentManager());
        mViewPager.setAdapter(mSectinosPagerAdapter);
        mTabLayout.setupWithViewPager(mViewPager);

        return root;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);

    }
    /**
     * add control from layout
     * @param root is ViewGroup get from onCreateView method*/
    public  void getView(View root){
        mToolBar = root.findViewById(R.id.main_page_toolbar);
        //tab
        mViewPager = (ViewPager) root.findViewById(R.id.main_tabPager);
        mTabLayout = root.findViewById(R.id.main_tabs);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.main_menu,menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        super.onOptionsItemSelected(item);
        if(item.getItemId() == R.id.main_logout_btn){
            Fragment fragment = getFragmentManager().findFragmentByTag("SIGN_IN");
                FragmentTransaction transaction = null;
                if (fragment==null) {
                    SignInFragment signInFragment = new SignInFragment();
                    transaction = getFragmentManager().beginTransaction();
                    transaction.replace(R.id.flContainer, signInFragment);
                    transaction.addToBackStack(null);
                    transaction.commit();
                }else {
                    transaction.replace(R.id.flContainer, fragment);
                    transaction.addToBackStack(null);
                    transaction.commit();
            }


        }

        if(item.getItemId() == R.id.main_account_btn){
            Fragment fragment = new InforUserFragment();
            Extension.replaceFragment(getFragmentManager(),fragment);
        }

        if(item.getItemId() == R.id.main_all_btn){

        }

        return true;
    }



}
