package com.nova.maxis7567.learningblocks.main.dashboard;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.text.style.RelativeSizeSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
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
import com.nova.maxis7567.learningblocks.authentication.RegisterUserActivity;
import com.nova.maxis7567.learningblocks.globaladapters.BlogAdapter;
import com.nova.maxis7567.learningblocks.main.MainActivity;
import com.nova.maxis7567.learningblocks.models.Dashboard;
import com.nova.maxis7567.learningblocks.pricing.PricingActivity;
import com.nova.maxis7567.learningblocks.search.SearchActivity;
import com.nova.maxis7567.learningblocks.services.Api;
import com.nova.maxis7567.learningblocks.tools.DataBaseRecentActivities;
import com.nova.maxis7567.learningblocks.tools.dialog.DialogMessage;
import com.nova.maxis7567.learningblocks.tools.dialog.LoadingDialog;

import java.util.Date;


public class DashboardFragment extends Fragment {
    private View view;
    private ViewGroup viewGroup;
    private FragmentActivity activity;
    private Context context;
    private RecyclerView recentActRec,blogRec;
    private TextView userName, premiumTitle,premiumDesc,recentActTx;
    private View bookmarkBtn,searchBtn;
    private ConstraintLayout premiumBtn;

    private LoadingDialog loading;

    private Dashboard data;
    public DashboardFragment() {
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
        view=inflater.inflate(R.layout.fragment_dashboard, container, false);
        viewGroup=view.findViewById(R.id.DashboardView);
        loading=new LoadingDialog(context,viewGroup);
        recentActTx=view.findViewById(R.id.DashboardRecentActTx);
        recentActRec=view.findViewById(R.id.DashboardRecentActRec);
        blogRec=view.findViewById(R.id.DashboardBlogRec);
        userName=view.findViewById(R.id.DahsboardUserName);
        premiumTitle=view.findViewById(R.id.DahsboardPremiumTitle);
        premiumDesc=view.findViewById(R.id.DahsboardPremiumDesc);
        premiumBtn=view.findViewById(R.id.DahsboardPremiumeBackground);
        bookmarkBtn=view.findViewById(R.id.DashboardBookMark);
        searchBtn=view.findViewById(R.id.DahsboardSearch);
        BlogAdapter blogAdapter=new BlogAdapter(context,null,null);
        blogRec.setLayoutManager(new LinearLayoutManager(context));
        blogRec.setAdapter(blogAdapter);
        view.findViewById(R.id.DashboordUserBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog a=   new AlertDialog.Builder(context).setMessage("مایل به خروج از حساب کاربری خود هستید؟").setCancelable(true)
                        .setOnCancelListener(new DialogInterface.OnCancelListener() {
                            @Override
                            public void onCancel(DialogInterface dialog) {
                                dialog.dismiss();
                            }
                        })
                        .setPositiveButton("بله", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                DataBaseTokenID.ResetTokenID(context);
                                Intent intent = new Intent(context, SplashActivity.class);
                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                                startActivity(intent);
                                activity.finish();
                            }
                        })
                        .setNegativeButton("خیر", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        })
                        .create();
                a.show();

            }
        });
        if (data==null){
         getData();
        }else {
            setUpView();
        }
        return view;
    }

    private void getData() {
        loading.show();
        Api.dashboard(context, new Response<Dashboard>() {
            @Override
            public void respond(Dashboard respond) {
                loading.dismiss();
                data=respond;
                setUpView();
            }
        }, new ResponseError<JsonObject>() {
            @Override
            public void error(RespondError<JsonObject> error) {
                activity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        loading.dismiss();
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
                    public void run() {
                        loading.dismiss();
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

    private void setUpView() {
        bookmarkBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MainActivity) activity).fragmentSelector(4);
            }
        });
        view.findViewById(R.id.DashboardMoreBlog).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MainActivity) activity).fragmentSelector(3);
            }
        });
        searchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(context, SearchActivity.class);
                intent.putExtra("activity",true);
                startActivity(intent);
            }
        });
        userName.setText(data.getUser().getName());
        if (data.getUser().getAccount().isPremium()){
            premiumBtn.setBackgroundResource(R.drawable.shape_premiume);
            premiumTitle.setText(R.string.activePremium);
            int days=(int)((data.getUser().getAccount().getExpireTime()- new Date().getTime()/1000)/60/60/24);
            premiumDesc.setText(context.getString(R.string.theTime)+days+context.getString(R.string.remainder));
        }else {
            SpannableStringBuilder builder = new SpannableStringBuilder();
            SpannableString str1 = new SpannableString("خرید ");
            Typeface font = Typeface.createFromAsset(context.getAssets(), "font/bold.TTF");
            str1.setSpan(new ForegroundColorSpan(context.getResources().getColor(R.color.textBlack)), 0, str1.length(), 0);
            str1.setSpan (font, 0, str1.length(),Spanned.SPAN_EXCLUSIVE_INCLUSIVE);
            builder.append(str1);
            SpannableString str2 = new SpannableString("اشتراک ویژه");
            str2.setSpan(new ForegroundColorSpan(context.getResources().getColor(R.color.colorPrimaryDark)), 0, str2.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            str2.setSpan (font, 0, str2.length(),Spanned.SPAN_EXCLUSIVE_INCLUSIVE);
            builder.append(str2);
            premiumTitle.setText(builder, TextView.BufferType.SPANNABLE);
            premiumDesc.setText(R.string.ActivePremium);
            premiumBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(new Intent(context,PricingActivity.class));
                }
            });
        }
        if (DataBaseRecentActivities.isEmpty(context)){
            recentActTx.setVisibility(View.GONE);
        }else {
            RecentActivityAdapter recentActivityAdapter=new RecentActivityAdapter(context,DataBaseRecentActivities.Get(context));
            recentActRec.setLayoutManager(new LinearLayoutManager(context));
            recentActRec.setAdapter(recentActivityAdapter);
        }
        BlogAdapter blogAdapter=new BlogAdapter(context,data.getBlogList(),null);
        blogRec.setLayoutManager(new LinearLayoutManager(context));
        blogRec.setAdapter(blogAdapter);
    }
}