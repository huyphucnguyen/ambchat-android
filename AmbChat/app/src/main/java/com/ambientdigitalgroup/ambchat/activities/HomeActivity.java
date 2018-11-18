package com.ambientdigitalgroup.ambchat.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.ambientdigitalgroup.ambchat.R;

public class HomeActivity extends Activity {

        TextView txtMsg;
        Button btnThoat;
        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.fragment_request);
            txtMsg=(TextView) findViewById(R.id.txtmsg);
            btnThoat=(Button) findViewById(R.id.btnThoat);
            btnThoat.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View arg0) {
                    // TODO Auto-generated method stub
                    finish();
                }
            });
            Intent i=getIntent();
            txtMsg.setText("Hello : "+i.getStringExtra("userName"));
        }

        @Override
        public boolean onCreateOptionsMenu(Menu menu) {
            // Inflate the menu; this adds items to the action bar if it is present.
            getMenuInflater().inflate(R.menu.main_menu, menu);
            return true;
        }

    }
