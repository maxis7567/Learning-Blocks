package com.nova.maxis7567.learningblocks1.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.JsonObject;
import com.maxis7567.msvolley.LocalError;
import com.maxis7567.msvolley.RespondError;
import com.maxis7567.msvolley.Response;
import com.maxis7567.msvolley.ResponseError;
import com.nova.maxis7567.learningblocks1.R;
import com.nova.maxis7567.learningblocks1.globaladapters.TagAdapter;
import com.nova.maxis7567.learningblocks1.models.Activity;
import com.nova.maxis7567.learningblocks1.pricing.PricingActivity;
import com.nova.maxis7567.learningblocks1.services.Api;
import com.nova.maxis7567.learningblocks1.tools.DataBaseRecentActivities;
import com.nova.maxis7567.learningblocks1.tools.Picasso;
import com.nova.maxis7567.learningblocks1.tools.dialog.DialogMessage;
import com.nova.maxis7567.learningblocks1.tools.dialog.LoadingDialog;

public class SingleActivity extends AppCompatActivity {
    private ViewGroup viewGroup;
    private TextView title,desc;
    private ImageView image,backBtn,bookmark;
    private RecyclerView tagRec,stepRec,tipRec;
    private LoadingDialog loading;
    private Activity data;
    private boolean bookmarkFlag;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single);
        viewGroup=findViewById(R.id.SingleActivityView);
        loading=new LoadingDialog(this,viewGroup);
        findViewById(R.id.SingleActivityBackBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        title=findViewById(R.id.SingleActivityTitle);
        desc=findViewById(R.id.SingleActivityDesc);
        image=findViewById(R.id.SingleActivityImage);
        backBtn=findViewById(R.id.SingleActivityBackBtn);
        bookmark=findViewById(R.id.SingleActivityBookmark);
        tagRec=findViewById(R.id.SingleActivityTagRec);
        stepRec=findViewById(R.id.SingleActivityTrainRec);
        tipRec=findViewById(R.id.SingleActivityTipRec);
        getData();
    }

    private void getData() {
        loading.show();
        Api.activity(this, new Response<Activity>() {
            @Override
            public void respond(Activity respond) {
                data=respond;
                setUpView();
            }
        }, new ResponseError<JsonObject>() {
            @Override
            public void error(final RespondError<JsonObject> error) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {loading.dismiss();
                        if (error.getResCode()==404){
                            DialogMessage dialogMessage = new DialogMessage(SingleActivity.this, viewGroup, true, new DialogMessage.DialogMessageInterface() {
                                @Override
                                public void OnConfirmed() {
                                    onBackPressed();
                                }

                                @Override
                                public void OnCancel() {

                                }
                            });
                            dialogMessage.title.setText(R.string.notFound404);
                            dialogMessage.title.setTextColor(getResources().getColor(R.color.textBlack));
                            dialogMessage.description.setText(R.string.activityNotFound);
                            dialogMessage.success.setText(R.string.returnBack);
                            dialogMessage.setCancelable(false);
                            dialogMessage.show();
                        }else if (error.getRespond().get("message").getAsString().equals("not package is active")){
                            DialogMessage dialogMessage= new DialogMessage(SingleActivity.this, viewGroup, false, new DialogMessage.DialogMessageInterface() {
                                @Override
                                public void OnConfirmed() {
                                    startActivity(new Intent(SingleActivity.this, PricingActivity.class));
                                    finish();
                                }

                                @Override
                                public void OnCancel() {
                                    onBackPressed();
                                }
                            });
                            dialogMessage.title.setText(R.string.forbiddenAccess);
                            dialogMessage.title.setTextColor(getResources().getColor(R.color.textBlack));
                            dialogMessage.description.setText(R.string.ActivityForbiddenAccess);
                            dialogMessage.success.setText(R.string.buyPremium);
                            dialogMessage.cancel.setText(R.string.cancel);
                            dialogMessage.setCancelable(false);
                            dialogMessage.show();
                        }else {
                            DialogMessage dialogMessage = new DialogMessage(SingleActivity.this, viewGroup, true, new DialogMessage.DialogMessageInterface() {
                                @Override
                                public void OnConfirmed() {
                                    getData();
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
        }, new LocalError() {
            @Override
            public void error(final String message) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        loading.dismiss();
                        if (message.equals("network not available")){
                            DialogMessage dialogMessage= new DialogMessage(SingleActivity.this, viewGroup, true, new DialogMessage.DialogMessageInterface() {
                                @Override
                                public void OnConfirmed() {
                                    getData();
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
                            DialogMessage dialogMessage= new DialogMessage(SingleActivity.this, viewGroup, true, new DialogMessage.DialogMessageInterface() {
                                @Override
                                public void OnConfirmed() {
                                    getData();
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
        },getIntent().getIntExtra("id",0));
    }

    private void setUpView() {
        DataBaseRecentActivities.Write(this,data);
        title.setText(data.getTitle());
        desc.setText(data.getDesc());
        desc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                desc.setMaxLines(Integer.MAX_VALUE);
            }
        });
        Picasso.load(data.getImage(),image,R.drawable.image_holder_spinner);
        tagRec.setAdapter(new TagAdapter(this,data.getTagList()));
        tagRec.setLayoutManager(new LinearLayoutManager(this,RecyclerView.HORIZONTAL,false));
        stepRec.setAdapter(new TrainAdapter(this,data.getStepList()));
        stepRec.setLayoutManager(new LinearLayoutManager(this));
        bookmarkHandler(data.isBookmark());
        bookmarkFlag=data.isBookmark();
        bookmark.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (bookmarkFlag){
                    bookmarkHandler(false);
                }else {
                    bookmarkHandler(true);
                }
                setBookmark();
            }
        });
        tipRec.setAdapter(new TipAdapter(this,data.getTipList()));
        tipRec.setLayoutManager(new LinearLayoutManager(this));
        loading.dismiss();
    }

    private void setBookmark() {
        Api.activityBookmark(this, new Response<JsonObject>() {
            @Override
            public void respond(JsonObject respond) {
                switch (respond.get("status").getAsInt()){
                    case 1:
                        bookmarkFlag=true;
                        bookmarkHandler(true);
                        break;
                    case 2:
                        bookmarkFlag=false;
                        bookmarkHandler(false);
                        break;
                }
            }
        }, new ResponseError<JsonObject>() {
            @Override
            public void error(RespondError<JsonObject> error) {
                bookmarkHandler(bookmarkFlag);
            }
        }, new LocalError() {
            @Override
            public void error(String message) {
                bookmarkHandler(bookmarkFlag);
            }
        },data.getId());
    }

    private void bookmarkHandler(boolean b) {
        if (b){
            bookmark.setImageResource(R.drawable.ic_bookmark);
        }else {
            bookmark.setImageResource(R.drawable.ic_bookmark_o);
        }
    }
}