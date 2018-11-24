package com.ambientdigitalgroup.ambchat.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import com.ambientdigitalgroup.ambchat.R;
import com.ambientdigitalgroup.ambchat.networks.SeverRequest;
import com.ambientdigitalgroup.ambchat.networks.SignUpRequest;
import com.ambientdigitalgroup.ambchat.utils.Extension;

import java.util.HashMap;
import java.util.Map;

import okhttp3.MediaType;

public class SignUpFragment extends Fragment {

    private EditText edtFullName, edtEmail, edtUserName, edtPassWord, edtRepeatPass;
    private RadioButton radMale, radFemail;
    private Button btnRegister;
    public static final MediaType MEDIA_TYPE = MediaType.parse("application/json");
    public static final String USERNAME = null;
    public static final String PASSWORD = null;
    private final String USER_NAME = "username";
    private final String PASSWORD_KEY = "password";


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup root = ( ViewGroup ) inflater.inflate(R.layout.signup, container, false);
        addControls(root);
        return root;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        addEvents();
    }

    /*addControls get controls to process*/
    private void addControls(ViewGroup root) {
        edtEmail = root.findViewById(R.id.edtEmail);
        edtFullName = root.findViewById(R.id.edtFullName);
        edtUserName = root.findViewById(R.id.edtUserName);
        edtPassWord = root.findViewById(R.id.edtPassWord);
        edtRepeatPass = root.findViewById(R.id.edtRepeatPass);
        radMale = root.findViewById(R.id.radMale);
        radFemail = root.findViewById(R.id.radFemail);
        btnRegister = root.findViewById(R.id.btnRegister);
    }


    private void addEvents() {
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final String username = edtUserName.getText().toString();
                String fullname = edtFullName.getText().toString();
                String email = edtEmail.getText().toString();
                final String password = edtPassWord.getText().toString();
                String rePassword = edtRepeatPass.getText().toString();
                int gender = radMale.isChecked() ? 1 : 0;

                boolean checkData = isEmptyField(username, fullname, email, password, rePassword);
                if (checkData) return;
                //-------------------------------su ly dang ky------------------------//
                Map<String, String> parameter = new HashMap<>();
                parameter.put("username", username);
                parameter.put("fullname", fullname);
                parameter.put("email", email);
                parameter.put("password", password);
                parameter.put("gender", String.valueOf(gender));

                SignUpRequest request = new SignUpRequest(new SeverRequest.SeverRequestListener() {
                    @Override
                    public void completed(Object obj) {
                        if (obj != null) {

                            int error = ( int ) obj;
                            /*Toast.makeText(getBaseContext(),mes,Toast.LENGTH_SHORT).show();*/

                            if (error == 0) {
                                Fragment fragment = new SignInFragment();
                                Bundle args = new Bundle();
                                args.putString(USER_NAME,username);
                                args.putString(PASSWORD_KEY,password);
                                fragment.setArguments(args);
                                Extension.replaceFragment(getFragmentManager(),fragment);
                            }
                            if (error == 1) {
                                showMessage("User invalid");
                            }
                            if (error == 2) {
                                showMessage("Password invalid");
                            }
                            if (error == 3) {
                                showMessage("User is exist");
                            }
                            if (error == 4) {
                                showMessage("Email is already registered by another account");
                            }
                        } else {
                            //ERROR
                            Toast.makeText(getContext(), "Error Register!", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
                //thuc hien sau ????
                request.execute(parameter);

            }
        });
    }

    /**
     * isEmptyField
     *
     * @param username   is email entered
     * @param fullname   is full name entered
     * @param email      is user name entered
     * @param password   is password entered
     * @param rePassword is repassword entered
     */
    private boolean isEmptyField(String username, String fullname, String email, String password, String rePassword) {
        if (email.equals("")) {
            Toast.makeText(getContext(), "Email không được để trống", Toast.LENGTH_LONG).show();
            return true;
        }
        if (fullname.equals("")) {
            Toast.makeText(getContext(), "Full name không được để trống", Toast.LENGTH_LONG).show();
            return true;
        }

        if (password.equals("")) {
            Toast.makeText(getContext(), "Password không được để trống", Toast.LENGTH_LONG).show();
            return true;
        }

        if (username.equals("")) {
            Toast.makeText(getContext(), "UserName không được để trống", Toast.LENGTH_LONG).show();
            return true;
        }

        if (!rePassword.equals(password)) {
            Toast.makeText(getContext(), "Nhập lại password không khớp", Toast.LENGTH_LONG).show();
            return true;
        }

        return false;
    }

    //function show message
    private void showMessage(String mess) {
        Toast.makeText(getContext(), mess, Toast.LENGTH_SHORT).show();
    }

}
