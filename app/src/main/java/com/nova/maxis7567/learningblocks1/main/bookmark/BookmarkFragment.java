package com.nova.maxis7567.learningblocks1.main.bookmark;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.viewpager.widget.ViewPager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.tabs.TabLayout;
import com.google.gson.JsonObject;
import com.maxis7567.msvolley.LocalError;
import com.maxis7567.msvolley.RespondError;
import com.maxis7567.msvolley.Response;
import com.maxis7567.msvolley.ResponseError;
import com.nova.maxis7567.learningblocks1.R;
import com.nova.maxis7567.learningblocks1.models.Bookmark;
import com.nova.maxis7567.learningblocks1.services.Api;
import com.nova.maxis7567.learningblocks1.tools.dialog.DialogMessage;
import com.nova.maxis7567.learningblocks1.tools.dialog.LoadingDialog;


public class BookmarkFragment extends Fragment {
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private View view;
    private ViewGroup viewGroup;
    private LoadingDialog loading;

    private Context context;
    private FragmentActivity activity;
    private FragmentManager fragmentManager;

    public BookmarkFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context=getContext();
        activity=getActivity();
        fragmentManager=getChildFragmentManager();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_bookmark, container, false);
        viewGroup=view.findViewById(R.id.BookmarkView);
        tabLayout=view.findViewById(R.id.BookmarkTabLayout);
        viewPager=view.findViewById(R.id.BookmarkViewPager);
        loading=new LoadingDialog(context,viewGroup);
        tabLayout.setTabIndicatorFullWidth(true);
        getData();
        return view;
    }

    private void getData() {
        loading.show();
        Api.bookmark(context, new Response<Bookmark>() {
            @Override
            public void respond(Bookmark respond) {
            loading.dismiss();
            viewPager.setAdapter(new ViewPagerAdapter(fragmentManager,respond));
            tabLayout.setupWithViewPager(viewPager);

            }
        }, new ResponseError<JsonObject>() {
            @Override
            public void error(RespondError<JsonObject> error) {
               activity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {loading.dismiss();
                        DialogMessage dialogMessage= new DialogMessage(context, viewGroup, true, new DialogMessage.DialogMessageInterface() {
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
        }, new LocalError() {
            @Override
            public void error(final String message) {
               activity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {loading.dismiss();
                        if (message.equals("network not available")){
                            DialogMessage dialogMessage= new DialogMessage(context, viewGroup, true, new DialogMessage.DialogMessageInterface() {
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
                        }else {
                            DialogMessage dialogMessage= new DialogMessage(context, viewGroup, true, new DialogMessage.DialogMessageInterface() {
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
        });
    }
}