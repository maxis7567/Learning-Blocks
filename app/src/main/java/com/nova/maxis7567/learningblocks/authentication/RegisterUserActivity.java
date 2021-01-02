package com.nova.maxis7567.learningblocks.authentication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
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

public class RegisterUserActivity extends AppCompatActivity {
    private ViewGroup viewGroup;
    private EditText name, lName;
    private LinearLayout nameIc, lNameIc;
    private ImageView agreeBtn, agreeIc;
    private TextView errorText;

    private boolean agree = false;
    private LoadingDialog loading;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_user);
        viewGroup = findViewById(R.id.RegisterView);
        name = findViewById(R.id.RegisterNameEditText);
        lName = findViewById(R.id.RegisterlNameEditText);
        nameIc = findViewById(R.id.RegisterNameIc);
        lNameIc = findViewById(R.id.RegisterlNameIc);
        agreeBtn = findViewById(R.id.RegisterAgreeBtn);
        agreeIc = findViewById(R.id.RegisterAgreeIc);
        errorText = findViewById(R.id.RegisterErrorText);
        loading = new LoadingDialog(this, viewGroup);
        setUpViewAction();
        findViewById(R.id.RegisterReadPolicyBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(Api.DOMAIN+"law/"));
                startActivity(intent);
            }
        });
        findViewById(R.id.RegisterBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (name.length() == 0 || lName.length() == 0) {
                    errorText.setText(R.string.enterNameAndLastName);
                    errorText.setVisibility(View.VISIBLE);
                } else if (agree){
                    register();
                }else {
                    errorText.setText(R.string.policyError);
                    errorText.setVisibility(View.VISIBLE);
                }

            }
        });
        agreeBtnHandler(false);
        agreeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (agree) {
                    agreeBtnHandler(false);
                } else {
                    agreeBtnHandler(true);
                }
            }
        });
    }

    private void register() {
        loading.show();
        Api.registerUser(this, new Response<JsonObject>() {
            @Override
            public void respond(JsonObject respond) {
                DataBaseTokenID.WriteTokenID(RegisterUserActivity.this,getIntent().getStringExtra("token"));
                Intent intent = new Intent(RegisterUserActivity.this, MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                finish();
            }
        }, new ResponseError<JsonObject>() {
            @Override
            public void error(RespondError<JsonObject> error) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        loading.dismiss();
                        DialogMessage dialogMessage = new DialogMessage(RegisterUserActivity.this, viewGroup, true, new DialogMessage.DialogMessageInterface() {
                            @Override
                            public void OnConfirmed() {
                                register();
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
                        if (message.equals("network not available")) {
                            DialogMessage dialogMessage = new DialogMessage(RegisterUserActivity.this, viewGroup, true, new DialogMessage.DialogMessageInterface() {
                                @Override
                                public void OnConfirmed() {
                                    register();
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
                        } else {
                            DialogMessage dialogMessage = new DialogMessage(RegisterUserActivity.this, viewGroup, true, new DialogMessage.DialogMessageInterface() {
                                @Override
                                public void OnConfirmed() {
                                    register();
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
        }, name.getText().toString(), lName.getText().toString(),getIntent().getStringExtra("token"));
    }

    private void setUpViewAction() {
        name.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    nameIc.setVisibility(View.GONE);
                }
                if (lName.length() == 0) {
                    lNameIc.setVisibility(View.VISIBLE);
                } else {
                    lNameIc.setVisibility(View.GONE);
                }
            }
        });
        lName.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    lNameIc.setVisibility(View.GONE);
                }
                if (name.length() == 0) {
                    nameIc.setVisibility(View.VISIBLE);
                } else {
                    nameIc.setVisibility(View.GONE);
                }
            }
        });

    }

    void agreeBtnHandler(boolean selected) {
        agree = selected;
        if (selected) {
            agreeIc.setColorFilter(ContextCompat.getColor(this, R.color.textGreen));
        } else {
            agreeIc.setColorFilter(Color.parseColor("#DFDFDFDF"));
        }
    }

    public void showKeyboard(EditText mEtSearch) {
        mEtSearch.requestFocus();
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0);
    }

    public void hideSoftKeyboard(EditText mEtSearch) {
        mEtSearch.clearFocus();
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(mEtSearch.getWindowToken(), 0);


    }
}