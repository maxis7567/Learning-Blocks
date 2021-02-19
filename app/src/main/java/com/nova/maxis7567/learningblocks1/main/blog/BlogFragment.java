package com.nova.maxis7567.learningblocks1.main.blog;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import com.hinext.maxis7567.mstools.RecycelerLoadMore;
import com.maxis7567.msvolley.LocalError;
import com.maxis7567.msvolley.RespondError;
import com.maxis7567.msvolley.Response;
import com.maxis7567.msvolley.ResponseError;
import com.nova.maxis7567.learningblocks1.R;
import com.nova.maxis7567.learningblocks1.globaladapters.BlogAdapter;
import com.nova.maxis7567.learningblocks1.main.MainActivity;
import com.nova.maxis7567.learningblocks1.models.Blog;
import com.nova.maxis7567.learningblocks1.services.Api;
import com.nova.maxis7567.learningblocks1.tools.dialog.DialogMessage;
import com.nova.maxis7567.learningblocks1.tools.dialog.LoadingDialog;

import java.util.List;


public class BlogFragment extends Fragment {
    private View view;
    private ViewGroup viewGroup;
    private Context context;
    private FragmentActivity activity;

    private RecyclerView recyclerView;
    private List<Blog> blogList;
    private int page = 1;
    private int lastPage = 1;
    private BlogAdapter adapter;
    private LoadingDialog loading;

    public BlogFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context=getContext();
        activity=getActivity();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view=inflater.inflate(R.layout.fragment_blog, container, false);
        viewGroup=view.findViewById(R.id.BlogView);
        loading=new LoadingDialog(context,viewGroup);
        recyclerView=view.findViewById(R.id.FragBlogRec);
        view.findViewById(R.id.BlogBookMark).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MainActivity) activity).fragmentSelector(4);
            }
        });
        if (blogList==null){
            adapter=new BlogAdapter(context, null,null);
            getData();
        }
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        recyclerView.setAdapter(adapter);
        return view;
    }

    private void getData() {
        loading.show();
        Api.blogList(context, new Response<JsonObject>() {
            @Override
            public void respond(JsonObject respond) {
                lastPage=respond.get("lastPage").getAsInt();
                if (page==1){
                    blogList=new Gson().fromJson(respond.get("data"),new  TypeToken<List<Blog>>(){}.getType());
                    adapter=new BlogAdapter(context, blogList, new RecycelerLoadMore() {
                        @Override
                        public void callBack() {
                            if (lastPage!=page){
                                page++;
                                getData();
                            }
                        }
                    });
                    recyclerView.setLayoutManager(new LinearLayoutManager(context));
                    recyclerView.setAdapter(adapter);
                }else {
                    List<Blog> tmp=new Gson().fromJson(respond.get("data"),new  TypeToken<List<Blog>>(){}.getType());
                    adapter.addAll(tmp);
                }
                loading.dismiss();
            }
        }, new ResponseError<JsonObject>() {
            @Override
            public void error(RespondError<JsonObject> error) {
                if (page==1) {
                    activity.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            loading.dismiss();
                            DialogMessage dialogMessage = new DialogMessage(context, viewGroup, true, new DialogMessage.DialogMessageInterface() {
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
                            dialogMessage.title.setTextColor(context.getResources().getColor(R.color.textBlack));
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
                if (page==1) {
                    activity.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            loading.dismiss();
                            if (message.equals("network not available")) {
                                DialogMessage dialogMessage = new DialogMessage(context, viewGroup, true, new DialogMessage.DialogMessageInterface() {
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
                                dialogMessage.title.setTextColor(context.getResources().getColor(R.color.textBlack));
                                dialogMessage.description.setText(R.string.internetError);
                                dialogMessage.success.setText(R.string.retry);
                                dialogMessage.setCancelable(false);
                                dialogMessage.show();
                            } else {
                                DialogMessage dialogMessage = new DialogMessage(context, viewGroup, true, new DialogMessage.DialogMessageInterface() {
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
                                dialogMessage.title.setTextColor(context.getResources().getColor(R.color.textBlack));
                                dialogMessage.description.setText(R.string.severError);
                                dialogMessage.success.setText(R.string.retry);
                                dialogMessage.setCancelable(false);
                                dialogMessage.show();
                            }
                        }
                    });
                }
            }
        },page);
    }
}