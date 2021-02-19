package com.nova.maxis7567.learningblocks1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.ViewGroup;

import com.google.gson.JsonObject;
import com.hinext.maxis7567.mstools.DataBaseTokenID;
import com.maxis7567.msvolley.LocalError;
import com.maxis7567.msvolley.RespondError;
import com.maxis7567.msvolley.Response;
import com.maxis7567.msvolley.ResponseError;
import com.nova.maxis7567.learningblocks1.authentication.LoginActivity;
import com.nova.maxis7567.learningblocks1.main.MainActivity;
import com.nova.maxis7567.learningblocks1.services.Api;
import com.nova.maxis7567.learningblocks1.tools.dialog.DialogMessage;

public class SplashActivity extends AppCompatActivity {

    private ViewGroup viewGroup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        viewGroup=findViewById(R.id.SplashView);
        checkVersion();
    }

    private void checkVersion() {
        Api.checkVersion(this, new Response<JsonObject>() {
            @Override
            public void respond(JsonObject respond) {
                try {
                    switch (respond.get("status").getAsInt()){
                        case 1:
                            if (DataBaseTokenID.thereIsToken(SplashActivity.this)){
                                startActivity(new Intent(SplashActivity.this, MainActivity.class));
                            }else {
                                startActivity(new Intent(SplashActivity.this, LoginActivity.class));
                            }
                            finish();
                            break;
                        case 2:
                            DialogMessage dialogMessage= new DialogMessage(SplashActivity.this, viewGroup, false, new DialogMessage.DialogMessageInterface() {
                                @Override
                                public void OnConfirmed() {
                                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(Api.DOMAIN));
                                    startActivity(intent);
                                    finish();
                                }

                                @Override
                                public void OnCancel() {
                                    if (DataBaseTokenID.thereIsToken(SplashActivity.this)){
                                        startActivity(new Intent(SplashActivity.this, MainActivity.class));
                                    }else
                                        startActivity(new Intent(SplashActivity.this,LoginActivity.class));
                                    finish();
                                }
                            });
                            dialogMessage.title.setText(R.string.update);
                            dialogMessage.title.setTextColor(getResources().getColor(R.color.textGreen));
                            dialogMessage.description.setText(R.string.updateText);
                            dialogMessage.success.setText(R.string.update);
                            dialogMessage.cancel.setText(R.string.cancel);
                            dialogMessage.show();
                            break;
                        case 3:
                            DialogMessage dialogMessage1= new DialogMessage(SplashActivity.this, viewGroup, true, new DialogMessage.DialogMessageInterface() {
                                @Override
                                public void OnConfirmed() {
                                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(Api.DOMAIN));
                                    startActivity(intent);
                                    finish();
                                }

                                @Override
                                public void OnCancel() {
                                    if (DataBaseTokenID.thereIsToken(SplashActivity.this)){
                                        startActivity(new Intent(SplashActivity.this, MainActivity.class));
                                    }else
                                        startActivity(new Intent(SplashActivity.this,LoginActivity.class));
                                    finish();
                                }
                            });
                            dialogMessage1.title.setText(R.string.update);
                            dialogMessage1.title.setTextColor(getResources().getColor(R.color.textGreen));
                            dialogMessage1.description.setText(R.string.updateText);
                            dialogMessage1.success.setText(R.string.update);
                            dialogMessage1.cancel.setText(R.string.cancel);
                            dialogMessage1.setCancelable(false);
                            dialogMessage1.show();
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
                        DialogMessage dialogMessage= new DialogMessage(SplashActivity.this, viewGroup, true, new DialogMessage.DialogMessageInterface() {
                            @Override
                            public void OnConfirmed() {
                                checkVersion();
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
                        if (message.equals("network not available")){
                            DialogMessage dialogMessage= new DialogMessage(SplashActivity.this, viewGroup, true, new DialogMessage.DialogMessageInterface() {
                                @Override
                                public void OnConfirmed() {
                                    checkVersion();
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
                            DialogMessage dialogMessage= new DialogMessage(SplashActivity.this, viewGroup, true, new DialogMessage.DialogMessageInterface() {
                                @Override
                                public void OnConfirmed() {
                                    checkVersion();
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
        });
    }
}