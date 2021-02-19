package com.nova.maxis7567.learningblocks1.authentication;

import androidx.appcompat.app.AppCompatActivity;

import android.animation.ObjectAnimator;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;


import com.heetch.countrypicker.Country;
import com.heetch.countrypicker.CountryPickerCallbacks;
import com.heetch.countrypicker.CountryPickerDialog;
import com.heetch.countrypicker.Utils;
import com.hinext.maxis7567.mstools.DisplayMetricsUtils;
import com.hinext.maxis7567.mstools.Validation;
import com.nova.maxis7567.learningblocks1.R;
import com.nova.maxis7567.learningblocks1.tools.DataBaseRecentActivities;

import java.util.List;

public class LoginActivity extends AppCompatActivity {
    private Country country;
    private List<Country> countries;
    private TextView countryCode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        DataBaseRecentActivities.Reset(this);
        EditText editText=findViewById(R.id.LoginInput);
        countryCode=findViewById(R.id.LoginCountryCode);
        countries = Utils.parseCountries(Utils.getCountriesJSON(this));
        String locale = getResources().getConfiguration().locale.getCountry();
        for (int i = 0; i < countries.size(); i++) {
            if (locale.equals(countries.get(i).getIsoCode())){
                country=countries.get(i);
                break;
            }
        }
        if (country==null){
            country=countries.get(0);
        }

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
                if (Validation.checkMobileNumber("+"+country.getDialingCode()+tmp)){
                    Intent intent=new Intent(LoginActivity.this,ActiveActivity.class);
                    intent.putExtra("phone","00"+country.getDialingCode()+tmp);
                    startActivity(intent);
                }else {
                    ((TextView) findViewById(R.id.LoginErrorText)).setText(R.string.invalidNumber);
                    ((TextView) findViewById(R.id.LoginErrorText)).setVisibility(View.VISIBLE);
                }
            }
        });
        CountryPickerDialog countryPicker =
                new CountryPickerDialog(this, new CountryPickerCallbacks() {
                    @Override
                    public void onCountrySelected(Country country, int flagResId) {
                        LoginActivity.this.country=country;
                        setCountryCode();
                    }
                });
        setCountryCode();
        countryCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                countryPicker.show();
            }
        });
    }
    private void setCountryCode(){
        countryCode.setText("+"+country.getDialingCode());
    }
}