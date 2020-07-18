package com.nova.maxis7567.learningblocks.authentication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.Editable;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.TextWatcher;
import android.text.style.ForegroundColorSpan;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.google.gson.JsonObject;
import com.hinext.maxis7567.mstools.DataBaseTokenID;
import com.maxis7567.msvolley.LocalError;
import com.maxis7567.msvolley.RespondError;
import com.maxis7567.msvolley.Response;
import com.maxis7567.msvolley.ResponseError;
import com.nova.maxis7567.learningblocks.R;
import com.nova.maxis7567.learningblocks.SplashActivity;
import com.nova.maxis7567.learningblocks.main.MainActivity;
import com.nova.maxis7567.learningblocks.services.Api;
import com.nova.maxis7567.learningblocks.tools.dialog.DialogMessage;
import com.nova.maxis7567.learningblocks.tools.dialog.LoadingDialog;
import com.onurkaganaldemir.ktoastlib.KToast;

public class ActiveActivity extends AppCompatActivity {

    private ViewGroup viewGroup;
    private LoadingDialog loading;

    private EditText code1,code2,code3,code4;
    private TextView errorText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_active);
        viewGroup=findViewById(R.id.ActiveView);
        code1=findViewById(R.id.ActvieCode1);
        code2=findViewById(R.id.ActvieCode2);
        code3=findViewById(R.id.ActvieCode3);
        code4=findViewById(R.id.ActvieCode4);
        errorText=findViewById(R.id.ActiveErrorTExt);
        loading=new LoadingDialog(this,viewGroup);
        SpannableStringBuilder builder = new SpannableStringBuilder();
        String str1="Activation code\n";
        String str2="Send to ";
        SpannableString str3=new SpannableString(getIntent().getStringExtra("phone")+" ");
        str3.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.colorAccent)), 0, str3.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        builder.append(str1);
        builder.append(str2);
        builder.append(str3);
        ((TextView) findViewById(R.id.ActiveNumber)).setText(builder);
        findViewById(R.id.ActiveSendCode).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (readCode().length()==4) {
                    sendCode();
                }else {
                    errorText.setText(R.string.wrongCode);
                    errorText.setVisibility(View.VISIBLE);
                }
            }
        });
        setUpCodes();
        sendNumber();
    }

    private void sendCode() {
        loading.show();
        Api.login(this, new Response<JsonObject>() {
            @Override
            public void respond(JsonObject respond) {
                loading.dismiss();
                try {
                    switch (respond.get("status").getAsInt()){
                        case 1:
                        case 2:
                            Intent intent=new Intent(ActiveActivity.this,RegisterUserActivity.class);
                            intent.putExtra("token",respond.get("token").getAsString());
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(intent);
                            break;
                        case 3:
                            DataBaseTokenID.WriteTokenID(ActiveActivity.this,respond.get("token").getAsString());
                            Intent intent1=new Intent(ActiveActivity.this,MainActivity.class);
                            intent1.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(intent1);

                            break;
                        default:
                            errorText.setText(R.string.wrongCode);
                            errorText.setVisibility(View.VISIBLE);
                            break;
                    }
                }catch (Exception ignore){}
            }
        }, new ResponseError<JsonObject>() {
            @Override
            public void error(RespondError<JsonObject> error) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        loading.dismiss();
                        DialogMessage dialogMessage= new DialogMessage(ActiveActivity.this, viewGroup, true, new DialogMessage.DialogMessageInterface() {
                            @Override
                            public void OnConfirmed() {
                                sendNumber();
                            }

                            @Override
                            public void OnCancel() {

                            }
                        });
                        dialogMessage.image.setImageResource(R.drawable.ic_server_error);
                        dialogMessage.title.setText(R.string.serverErrorTitle);
                        dialogMessage.title.setTextColor(getResources().getColor(R.color.textBlack));
                        dialogMessage.description.setText(R.string.severError);
                        dialogMessage.success.setText(R.string.retry);
                        dialogMessage.setCancelable(false);
                        dialogMessage.show();
                    }
                });


            }
        }, new LocalError() {
            @Override
            public void error(final String message) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        loading.dismiss();
                        if (message.equals("network not available")){
                            DialogMessage dialogMessage= new DialogMessage(ActiveActivity.this, viewGroup, true, new DialogMessage.DialogMessageInterface() {
                                @Override
                                public void OnConfirmed() {
                                    sendNumber();
                                }

                                @Override
                                public void OnCancel() {

                                }
                            });
                            dialogMessage.image.setImageResource(R.drawable.ic_internet_error);
                            dialogMessage.title.setText(R.string.internetErrorTitle);
                            dialogMessage.title.setTextColor(getResources().getColor(R.color.textBlack));
                            dialogMessage.description.setText(R.string.internetError);
                            dialogMessage.success.setText(R.string.retry);
                            dialogMessage.setCancelable(false);
                            dialogMessage.show();
                        }else {
                            DialogMessage dialogMessage= new DialogMessage(ActiveActivity.this, viewGroup, true, new DialogMessage.DialogMessageInterface() {
                                @Override
                                public void OnConfirmed() {
                                    sendNumber();
                                }

                                @Override
                                public void OnCancel() {

                                }
                            });
                            dialogMessage.image.setImageResource(R.drawable.ic_server_error);
                            dialogMessage.title.setText(R.string.serverErrorTitle);
                            dialogMessage.title.setTextColor(getResources().getColor(R.color.textBlack));
                            dialogMessage.description.setText(R.string.severError);
                            dialogMessage.success.setText(R.string.retry);
                            dialogMessage.setCancelable(false);
                            dialogMessage.show();
                        }
                    }
                });

            }
        },getIntent().getStringExtra("phone"),readCode());
    }
    String readCode(){
        String tmp="";
        tmp=tmp.concat(code1.getText().toString());
        tmp=tmp.concat(code2.getText().toString());
        tmp=tmp.concat(code3.getText().toString());
        tmp=tmp.concat(code4.getText().toString());
        return tmp;
    }
    private void sendNumber() {
        loading.show();
        Api.sendNumber(this, new Response<JsonObject>() {
            @Override
            public void respond(JsonObject respond) {
                loading.dismiss();
                KToast.successToast(ActiveActivity.this, getString(R.string.waiteForSms), Gravity.BOTTOM, KToast.LENGTH_AUTO, 10);
            }
        }, new ResponseError<JsonObject>() {
            @Override
            public void error(RespondError<JsonObject> error) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        loading.dismiss();
                        DialogMessage dialogMessage= new DialogMessage(ActiveActivity.this, viewGroup, true, new DialogMessage.DialogMessageInterface() {
                            @Override
                            public void OnConfirmed() {
                                sendNumber();
                            }

                            @Override
                            public void OnCancel() {

                            }
                        });
                        dialogMessage.image.setImageResource(R.drawable.ic_server_error);
                        dialogMessage.title.setText(R.string.serverErrorTitle);
                        dialogMessage.title.setTextColor(getResources().getColor(R.color.textBlack));
                        dialogMessage.description.setText(R.string.severError);
                        dialogMessage.success.setText(R.string.retry);
                        dialogMessage.setCancelable(false);
                        dialogMessage.show();
                    }
                });

            }
        }, new LocalError() {
            @Override
            public void error(final String message) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        loading.dismiss();
                        if (message.equals("network not available")){
                            DialogMessage dialogMessage= new DialogMessage(ActiveActivity.this, viewGroup, true, new DialogMessage.DialogMessageInterface() {
                                @Override
                                public void OnConfirmed() {
                                    sendNumber();
                                }

                                @Override
                                public void OnCancel() {

                                }
                            });
                            dialogMessage.image.setImageResource(R.drawable.ic_internet_error);
                            dialogMessage.title.setText(R.string.internetErrorTitle);
                            dialogMessage.title.setTextColor(getResources().getColor(R.color.textBlack));
                            dialogMessage.description.setText(R.string.internetError);
                            dialogMessage.success.setText(R.string.retry);
                            dialogMessage.setCancelable(false);
                            dialogMessage.show();
                        }else {
                            DialogMessage dialogMessage= new DialogMessage(ActiveActivity.this, viewGroup, true, new DialogMessage.DialogMessageInterface() {
                                @Override
                                public void OnConfirmed() {
                                    sendNumber();
                                }

                                @Override
                                public void OnCancel() {

                                }
                            });
                            dialogMessage.image.setImageResource(R.drawable.ic_server_error);
                            dialogMessage.title.setText(R.string.serverErrorTitle);
                            dialogMessage.title.setTextColor(getResources().getColor(R.color.textBlack));
                            dialogMessage.description.setText(R.string.severError);
                            dialogMessage.success.setText(R.string.retry);
                            dialogMessage.setCancelable(false);
                            dialogMessage.show();
                        }
                    }
                });

            }
        },getIntent().getStringExtra("phone"));
    }
    void setUpCodes(){
        code1.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (count == 1) {
                    code2.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        code2.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (count == 1) {
                    code3.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        code3.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (count == 1) {
                    code4.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        code4.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

}