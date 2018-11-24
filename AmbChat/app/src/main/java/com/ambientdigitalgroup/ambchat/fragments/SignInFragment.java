package com.ambientdigitalgroup.ambchat.fragments;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.util.Log;
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


      /**  if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
            String sss = Context.APPWIDGET_SERVICE;
        }
        //Test encode SHA256

        */
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
            edtPassWord.setText(args.getString("password"));
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
                Map<String, String> parameter = new HashMap<>();
                parameter.put("username", userName);
                parameter.put("password", password);
                SignInRequest request = new SignInRequest(new SeverRequest.SeverRequestListener() {
                    @Override
                    public void completed(Object obj) {
                        if (obj != null) {
                            mLoginProgress.dismiss();
                            ProfileUser profileUser = (ProfileUser) obj;
//                            Intent main_activity = new Intent(getActivity(), MainFragment.class);
//                            main_activity.putExtra(USERNAME,profileUser.getUser_name());
//                            main_activity.putExtra(FULLNAME,profileUser.getFull_name());
//                            main_activity.putExtra(USERID,profileUser.getUser_id());
//                            main_activity.putExtra(EMAIL,profileUser.getEmail());
//                            startActivity(main_activity);
                            Fragment fragment = new MainFragment();
                            Bundle args = new Bundle();
                            args.putString(USERNAME,profileUser.getUser_name());
                            args.putString(FULLNAME,profileUser.getFull_name());
                            args.putInt(USERID,profileUser.getUser_id());
                            args.putString(EMAIL,profileUser.getEmail());
                            fragment.setArguments(args);

                            Extension.replaceFragment(getFragmentManager(),fragment);

                        } else {
                            //ERROR
                            mLoginProgress.hide();
                            ShowMessage(getContext(),"Login fail!");
                        }
                    }
                });
                request.execute(parameter);
                //doLogin();
                switch (view.getId()){
                    case R.id.ckbRemmemberPass:
                        saveDataDefault();
                        break;
                    default:
                        break;
                }

            }
        });
    }
    public void doLogin(){
        //TODO change fragment
        Fragment fragment = new HomeFragment();
        Bundle args = new Bundle();
        args.putString("userName",edtUserName.getText().toString());
        fragment.setArguments(args);
        Extension.replaceFragment(getFragmentManager(),fragment);

    }

  /*  @Override
    protected void onPause() {
        super.onPause();
        savingPreferences();
    }
    protected void onResume() {
        // TODO Auto-generated method stub
        super.onResume();
        //gọi hàm đọc trạng thái ở đây
        restoringPreferences();
    }*/

    private void savingPreferences() {
        SharedPreferences pre=getActivity().getSharedPreferences(prefname, MODE_PRIVATE);
        //tạo đối tượng Editor để lưu thay đổi
        SharedPreferences.Editor editor=pre.edit();
        String userName=edtUserName.getText().toString();
        String passWord=edtPassWord.getText().toString();
        boolean ckbRemem=ckbRememberpass.isChecked();
        if(!ckbRemem)
        {
            //xóa mọi lưu trữ trước đó
            editor.clear();
        }
        else
        {
            //lưu vào editor
            editor.putString("userName", userName);
            editor.putString("passWord", passWord);
            editor.putBoolean("checkAcc", ckbRemem);
        }
        //chấp nhận lưu xuống file
        editor.commit();
    }
    public void restoringPreferences()
    {
        SharedPreferences pre=getActivity().getSharedPreferences
                (prefname,MODE_PRIVATE);
        //lấy giá trị checked ra, nếu không thấy thì giá trị mặc định là false
        boolean checkAcc=pre.getBoolean("checkAcc", false);
        if(checkAcc)
        {
            //lấy user, pwd, nếu không thấy giá trị mặc định là rỗng
            String user=pre.getString("userName", "");
            String pwd=pre.getString("passWord", "");
            edtUserName.setText(user);
            edtPassWord.setText(pwd);
        }
        ckbRememberpass.setChecked(checkAcc);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.main_menu,menu);
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
        return android.util.Base64.encodeToString(digest, android.util.Base64.DEFAULT);
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

    private void readData() {
        try {
            FileInputStream in = getActivity().openFileInput("infoUser");
            BufferedReader br= new BufferedReader(new InputStreamReader(in));
            StringBuffer buffer = new StringBuffer();
            String line = null;
            while((line= br.readLine())!= null)  {
                buffer.append(line).append("\n");
            }
            Log.d("read-data:",buffer.toString());

        } catch (Exception e) {
            Toast.makeText(getContext(),"Error:"+e.getMessage(),Toast.LENGTH_SHORT).show();
        }
    }

    public void saveDataDefault() {
        String fileName = "profileUser";
        String content = "Thong tin user";

        FileOutputStream outputStream = null;
        try {
            outputStream = getActivity().openFileOutput(fileName, MODE_PRIVATE);
            outputStream.write(content.getBytes());
            outputStream.close();
            Toast.makeText(getContext(), "Saved successfully", Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
