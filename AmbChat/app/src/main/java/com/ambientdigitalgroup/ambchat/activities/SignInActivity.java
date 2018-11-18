package com.ambientdigitalgroup.ambchat.activities;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.ambientdigitalgroup.ambchat.R;
import com.ambientdigitalgroup.ambchat.fragments.RequestFragment;
import com.ambientdigitalgroup.ambchat.networks.SeverRequest;
import com.ambientdigitalgroup.ambchat.networks.SignInRequest;
import com.ambientdigitalgroup.ambchat.utils.Extension;
import com.ambientdigitalgroup.ambchat.utils.ProfileUser;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;


public class SignInActivity extends AppCompatActivity {

    private EditText edtUserName, edtPassWord;
    private Button btnSigIn;
    private CheckBox ckbRememberpass;
    private TextView txtForgotPassword, txtRegisterAcount;
    String prefname="my_data";
    public static final String urlSignIn = "https://ambchat.herokuapp.com/api/sign_in.php";
    private ProgressDialog mLoginProgress;
    public static final String USERNAME=null ;
    public static final String FULLNAME = null;
    public static final String USERID=null;
    public static final String EMAIL=null;
    String userName;
    String password;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signin);
        addControls();
        // if(CheckInvalidData()==true){
        addEvent();
        // }-
        //  else{
        //  Toast.makeText(getBaseContext(),"User name or password Invalid",Toast.LENGTH_SHORT).show();
        //   return;
        //  }
    }

    public void addControls() {
        edtUserName = (EditText) findViewById(R.id.edtUserName);
        edtPassWord = (EditText) findViewById(R.id.edtPass);
        txtForgotPassword = (TextView) findViewById(R.id.txtForgotPassword);
        txtRegisterAcount = (TextView) findViewById(R.id.txtRegisterAcount);
        ckbRememberpass = (CheckBox) findViewById(R.id.ckbRemmemberPass);
        btnSigIn = (Button) findViewById(R.id.btnLogIn);
        String pass = edtPassWord.getText().toString();
        mLoginProgress = new ProgressDialog(this);


      /*  if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
            String sss = Context.APPWIDGET_SERVICE;
        }*/
        //Test encode SHA256

       /* try {
            String encodeSHA256=SHA256(pass);
            Toast.makeText(SignInActivity.this,encodeSHA256,Toast.LENGTH_LONG).show();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }*/
    }

    public void addEvent() {
        final Intent intent = getIntent();

                    /*userName= intent.getStringExtra(SignUpActivity.USERNAME);
                    password= intent.getStringExtra(SignUpActivity.PASSWORD);*/
                    if(intent!=null){
                        edtUserName.setText(intent.getStringExtra(SignUpActivity.USERNAME));
                        edtPassWord.setText(intent.getStringExtra(SignUpActivity.PASSWORD));
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

                            Intent main_activity = new Intent(SignInActivity.this, MainActivity.class);
                            main_activity.putExtra(USERNAME,profileUser.getUser_name());
                            main_activity.putExtra(FULLNAME,profileUser.getFull_name());
                            main_activity.putExtra(USERID,profileUser.getUser_id());
                            main_activity.putExtra(EMAIL,profileUser.getEmail());
                            startActivity(main_activity);

                        } else {
                            //ERROR
                            mLoginProgress.hide();
                            ShowMessage(getBaseContext(),"Login fail!");
                        }
                    }
                });
                request.execute(parameter);
                doLogin();

            }
        });
    }
    public void doLogin(){
        finish();//đóng màn hình hiện tại"
        Intent i=new Intent(this, RequestFragment.class);
        //truyền dữ liệu qua màn hình mới
        i.putExtra("userName", edtUserName.getText().toString());
        startActivity(i);//mở màn hình mới
    }

    @Override
    protected void onPause() {
        super.onPause();
        savingPreferences();
    }
    protected void onResume() {
        // TODO Auto-generated method stub
        super.onResume();
        //gọi hàm đọc trạng thái ở đây
        restoringPreferences();
    }

    private void savingPreferences() {
        SharedPreferences pre=getSharedPreferences(prefname, MODE_PRIVATE);
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
        SharedPreferences pre=getSharedPreferences
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
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu,menu);
        return true;
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

}
