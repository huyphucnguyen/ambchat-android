package com.ambientdigitalgroup.ambchat.fragments;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.ambientdigitalgroup.ambchat.R;
import com.ambientdigitalgroup.ambchat.networks.SeverRequest;
import com.ambientdigitalgroup.ambchat.networks.SignInRequest;
import com.ambientdigitalgroup.ambchat.utils.Extension;
import com.ambientdigitalgroup.ambchat.utils.ProfileUser;
import com.ambientdigitalgroup.ambchat.utils.Result;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;

import static android.content.Context.MODE_PRIVATE;

public class SignInFragment extends Fragment {
    private EditText edtUserName, edtPassWord;
    private Button btnSigIn;
    private CheckBox ckbRememberpass;
    private TextView txtForgotPassword, txtRegisterAcount;
    String prefname="my_data";
    public static final String urlSignIn = "https://ambchat.herokuapp.com/api/sign_in.php";
    private ProgressDialog mLoginProgress;
    public static final String USERNAME= "USERNAME" ;
    public static final String FULLNAME = "FULLNAME";
    public static final String USERID="USERID";
    public static final String EMAIL="EMAIL";
    String userName;
    String password;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup root = ( ViewGroup ) inflater.inflate(R.layout.signin,container,false);
        addControls(root);
        return root;
    }

    public void addControls(ViewGroup root) {
        edtUserName = (EditText) root.findViewById(R.id.edtUserName);
        edtPassWord = (EditText) root.findViewById(R.id.edtPass);
        txtForgotPassword = (TextView) root.findViewById(R.id.txtForgotPassword);
        txtRegisterAcount = (TextView) root.findViewById(R.id.txtRegisterAcount);
        ckbRememberpass = (CheckBox) root.findViewById(R.id.ckbRemmemberPass);
        btnSigIn = (Button) root.findViewById(R.id.btnLogIn);
        mLoginProgress = new ProgressDialog(getContext());

        Bundle bundle=getArguments();
        if(bundle!=null){
            edtUserName.setText(bundle.getString("username"));
        }
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //String pass = edtPassWord.getText().toString();

//        ckbRememberpass.setOnClickListener((View.OnClickListener) this);
//        // if(CheckInvalidData()==true){
        addEvent();
        // }-
        //  else{
        //  Toast.makeText(getBaseContext(),"User name or password Invalid",Toast.LENGTH_SHORT).show();
        //   return;
        //  }
    }

    public void addEvent() {
//        final Intent intent = getActivity().getIntent();
//        //Get data from signup
//                    /*userName= intent.getStringExtra(SignUpFragment.USERNAME);
//                    password= intent.getStringExtra(SignUpFragment.PASSWORD);*/
//        if(intent!=null){
//            edtUserName.setText(intent.getStringExtra(SignUpFragment.USERNAME));
//            edtPassWord.setText(intent.getStringExtra(SignUpFragment.PASSWORD));
//        }

        Bundle args = getArguments();
        if(args!=null){
            edtUserName.setText(args.getString("username"));

            if( args .getString("password") !=null ){
                edtPassWord.setText(args.getString("password"));
            }
            if(args.getString("password")!=null){
                edtPassWord.setText(args.getString("password"));
            }

        }

        //EVENT LOGIN
        btnSigIn.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.CUPCAKE)
            @Override
            public void onClick(View view) {
                mLoginProgress.setTitle("Logging In");
                mLoginProgress.setMessage("Please wait while we check your account.");
                mLoginProgress.setCanceledOnTouchOutside(false);
                mLoginProgress.show();
                userName = edtUserName.getText().toString().trim();
                password = edtPassWord.getText().toString().trim();
                String sha256OfPassword = "";

                String deviceID = getDeviceID();
                try {
                    sha256OfPassword = SHA256(password);
                } catch (NoSuchAlgorithmException e) {
                    e.printStackTrace();
                }
                Map<String, String> parameter = new HashMap<>();
                parameter.put("username", userName);
                parameter.put("password", sha256OfPassword);
                parameter.put("device_id",deviceID);
                SignInRequest request = new SignInRequest(new SeverRequest.SeverRequestListener() {
                    @Override
                    public void completed(Object obj) {
                        if (obj != null) {

                            mLoginProgress.dismiss();
                            Result res = (Result) obj;
                            String token= res.getToken();
                            savingPreferences(token);
                            ProfileUser user=(ProfileUser) res.getData();
                            Extension.UserID=user.user_id;
                            Extension.UserName=user.user_name;
                            Fragment fragment = new MainFragment();
                            Bundle args = new Bundle();
                            args.putString(USERNAME,user.getUser_name());
                            args.putString(FULLNAME,user.getFull_name());
                            args.putInt(USERID,user.getUser_id());
                            args.putString(EMAIL,user.getEmail());
                            fragment.setArguments(args);
                            Extension.replaceFragment(getFragmentManager(),fragment);
                            ShowMessage(getContext(),"Login success!");

                            FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                            //Fragment frag_signIn = getFragmentManager().findFragmentByTag("SIGN_IN");
                            transaction.replace(R.id.flContainer, fragment,"MAIN");
                            transaction.addToBackStack(null);
                            transaction.commit();


                        } else {
                            //ERROR
                            mLoginProgress.hide();
                            ShowMessage(getContext(),"Login fail!");
                        }
                    }
                });

                request.execute(parameter);
            }
        });
    }

    public static void addFragment(FragmentManager fgManager, Fragment fragment, String tagName) {
        FragmentTransaction transaction = null;
        if (fgManager != null) {
            transaction = fgManager.beginTransaction();
            transaction.add(R.id.flContainer, fragment,tagName);
            transaction.addToBackStack(null);
            transaction.commit();
        }
    }

    @SuppressLint("HardwareIds")
    public String getDeviceID() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.
                return "";
            }
            return  Build.getSerial();
        }
        return Build.SERIAL;
    }


    public void onResume() {
        // TODO Auto-generated method stub
        super.onResume();
        //gọi hàm đọc trạng thái ở đây
        restoringPreferences();
    }

    private void savingPreferences(String token) {
        SharedPreferences pre=getActivity().getSharedPreferences(prefname, MODE_PRIVATE);
        //tạo đối tượng Editor để lưu thay đổi
        SharedPreferences.Editor editor=pre.edit();
        userName = edtUserName.getText().toString();
        boolean ckbRemem=ckbRememberpass.isChecked();
        if(!ckbRemem)
        {
            //xóa mọi lưu trữ trước đó
            editor.clear();
        }
        else
        {
            //lưu vào editor
            editor.putString("token", token);
            editor.putString("username", userName);
            editor.putBoolean("checkAcc", ckbRemem);
        }
        //chấp nhận lưu xuống file
        editor.commit();

    }
    public void restoringPreferences()
    {
        SharedPreferences pre=getActivity().getSharedPreferences(prefname,MODE_PRIVATE);
        //lấy giá trị checked ra, nếu không thấy thì giá trị mặc định là false
        boolean checkAcc=pre.getBoolean("checkAcc", false);
        if(checkAcc)
        {
            //lấy user, pwd, nếu không thấy giá trị mặc định là rỗng
            String user=pre.getString("username", "");
            edtUserName.setText(user);
        }
        ckbRememberpass.setChecked(checkAcc);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.main_menu,menu);
        // return true;
    }

    public boolean checkInvalidData() {
        if (Extension.checkUserName(edtUserName.getText().toString()) == 0 ||
                Extension.checkPassWord(edtPassWord.getText().toString()) == 0) {
            return false;
        }
        return true;
    }

    public static String SHA256(String pass) throws NoSuchAlgorithmException {
        MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");
        messageDigest.update(pass.getBytes());
        byte[] digest = messageDigest.digest();
        return Extension.toHexString(digest);
    }
    public  void ShowMessage(final Context context, final String msg) {
        if (context != null && msg != null) {
            new Handler(Looper.getMainLooper()).post(new Runnable() {

                @Override
                public void run() {
                    Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
                }
            });
        }
    }
}
