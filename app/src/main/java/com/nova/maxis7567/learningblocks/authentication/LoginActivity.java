package com.nova.maxis7567.learningblocks.authentication;

import androidx.appcompat.app.AppCompatActivity;

import android.animation.ObjectAnimator;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.animation.BounceInterpolator;
import android.widget.EditText;
import android.widget.TextView;

import com.hinext.maxis7567.mstools.DisplayMetricsUtils;
import com.hinext.maxis7567.mstools.Validation;
import com.nova.maxis7567.learningblocks.R;
import com.nova.maxis7567.learningblocks.tools.DataBaseRecentActivities;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        DataBaseRecentActivities.Reset(this);
        EditText editText=findViewById(R.id.LoginInput);
        editText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus){
                    View view= findViewById(R.id.LoginAnime);
                    ObjectAnimator anim = ObjectAnimator.ofFloat(view, "translationY", 0f, new DisplayMetricsUtils(LoginActivity.this).convertDIPToPixels(27));
                    anim.setDuration(500).start();
                    view.animate().scaleY((float) 0.7).setDuration(500).start();
                    view.animate().scaleX((float) 0.7).setDuration(500).start();
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            editText.setHint(R.string.phoneNumberHint);
                        }
                    },500);
                }
            }
        });
        findViewById(R.id.LoginNextBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String tmp= ((EditText) findViewById(R.id.LoginInput)).getText().toString();
                if (Validation.checkMobileNumber(tmp)){
                    Intent intent=new Intent(LoginActivity.this,ActiveActivity.class);
                    intent.putExtra("phone",tmp);
                    startActivity(intent);
                }else {
                    ((TextView) findViewById(R.id.LoginErrorText)).setText(R.string.invalidNumber);
                    ((TextView) findViewById(R.id.LoginErrorText)).setVisibility(View.VISIBLE);
                }
            }
        });


    }
}