package com.nova.maxis7567.tavan.search;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import com.hinext.maxis7567.mstools.RecycelerLoadMore;
import com.maxis7567.msvolley.LocalError;
import com.maxis7567.msvolley.RespondError;
import com.maxis7567.msvolley.Response;
import com.maxis7567.msvolley.ResponseError;
import com.nova.maxis7567.tavan.R;
import com.nova.maxis7567.tavan.SplashActivity;
import com.nova.maxis7567.tavan.models.Activity;
import com.nova.maxis7567.tavan.models.Category;
import com.nova.maxis7567.tavan.services.Api;
import com.nova.maxis7567.tavan.tools.dialog.DialogMessage;

import java.util.List;

public class SearchActivity extends AppCompatActivity {
    private TextView title;
    private RecyclerView recyclerView;
    private EditText q;
    private int page = 1;
    private SearchAdapter adapter;
    private ViewGroup viewGroup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        viewGroup = findViewById(R.id.SearchView);
        findViewById(R.id.SearchBackBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        title = findViewById(R.id.SearchTitle);
        if (getIntent().getBooleanExtra("activity", false)) {
            title.setText("جستجو در فعالیت ها");
        } else {
            title.setText("جستجو در دسته ها");
        }

        recyclerView = findViewById(R.id.SearchRec);
        q = findViewById(R.id.SearchEditbox);
        q.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                page = 1;
                search();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    private void search() {
        if (getIntent().getBooleanExtra("activity", false)) {
            Api.activitySearch(this, new Response<JsonObject>() {
                @Override
                public void respond(JsonObject respond) {
                    if (page == 1) {
                        setUpResult(respond, true);
                    } else {
                        adapter.addActivity((List<Activity>) new Gson().fromJson(respond.get("data"), new TypeToken<List<Activity>>() {
                        }.getType()));
                    }
                }
            }, new ResponseError<JsonObject>() {
                @Override
                public void error(RespondError<JsonObject> error) {

                }
            }, new LocalError() {
                @Override
                public void error(String message) {

                }
            }, q.getText().toString(), page);
        } else {
            Api.catSearch(this, new Response<JsonObject>() {
                @Override
                public void respond(JsonObject respond) {
                    if (page == 1) {
                        setUpResult(respond, false);
                    } else {
                        adapter.addCategory((List<Category>) new Gson().fromJson(respond.get("data"), new TypeToken<List<Category>>() {
                        }.getType()));
                    }
                }
            }, new ResponseError<JsonObject>() {
                @Override
                public void error(RespondError<JsonObject> error) {
                    if (page == 1) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                DialogMessage dialogMessage = new DialogMessage(SearchActivity.this, viewGroup, true, new DialogMessage.DialogMessageInterface() {
                                    @Override
                                    public void OnConfirmed() {
                                        search();
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
                }
            }, new LocalError() {
                @Override
                public void error(final String message) {
                    if (page == 1) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                if (message.equals("network not available")) {
                                    DialogMessage dialogMessage = new DialogMessage(SearchActivity.this, viewGroup, true, new DialogMessage.DialogMessageInterface() {
                                        @Override
                                        public void OnConfirmed() {
                                            search();
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
                                    DialogMessage dialogMessage = new DialogMessage(SearchActivity.this, viewGroup, true, new DialogMessage.DialogMessageInterface() {
                                        @Override
                                        public void OnConfirmed() {
                                            search();
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
                }
            }, q.getText().toString(), page);
        }
    }

    private void setUpResult(JsonObject respond, boolean activity) {
        if (activity) {
            adapter = new SearchAdapter(this, (List<Activity>) new Gson().fromJson(respond.get("data"), new TypeToken<List<Activity>>() {
            }.getType()), new RecycelerLoadMore() {
                @Override
                public void callBack() {
                    page++;
                    search();
                }
            });
        } else {
            adapter = new SearchAdapter((List<Category>) new Gson().fromJson(respond.get("data"), new TypeToken<List<Category>>() {
            }.getType()), this, new RecycelerLoadMore() {
                @Override
                public void callBack() {
                    page++;
                    search();
                }
            });
        }
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }
}