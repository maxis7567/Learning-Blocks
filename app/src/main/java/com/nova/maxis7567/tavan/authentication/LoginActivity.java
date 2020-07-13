package com.nova.maxis7567.tavan.authentication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.hinext.maxis7567.mstools.Validation;
import com.nova.maxis7567.tavan.R;
import com.nova.maxis7567.tavan.tools.DataBaseRecentActivities;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        DataBaseRecentActivities.Reset(this);
        findViewById(R.id.LoginNextBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String tmp= ((EditText) findViewById(R.id.LoginInput)).getText().toString();
                if (Validation.checkMobileNumber(tmp)){
                    Intent intent=new Intent(LoginActivity.this,ActiveActivity.class);
                    intent.putExtra("phone",tmp);
                    startActivity(intent);
                }else {
                    ((TextView) findViewById(R.id.LoginErrorText)).setText("شماره نا درست است");
                    ((TextView) findViewById(R.id.LoginErrorText)).setVisibility(View.VISIBLE);
                }
            }
        });


    }
}