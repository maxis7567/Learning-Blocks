package com.nova.maxis7567.learningblocks.blog;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.JsonObject;
import com.hinext.maxis7567.mstools.DisplayMetricsUtils;
import com.hinext.maxis7567.mstools.PostTimeCal;
import com.hinext.maxis7567.mstools.RtlGridLayoutManager;
import com.hinext.maxis7567.mstools.VideoEnabledWebChromeClient;
import com.makeramen.roundedimageview.RoundedImageView;

import com.maxis7567.msvolley.LocalError;
import com.maxis7567.msvolley.RespondError;
import com.maxis7567.msvolley.Response;
import com.maxis7567.msvolley.ResponseError;
import com.nova.maxis7567.learningblocks.R;
import com.nova.maxis7567.learningblocks.globaladapters.TagAdapter;
import com.nova.maxis7567.learningblocks.models.Blog;
import com.nova.maxis7567.learningblocks.services.Api;
import com.nova.maxis7567.learningblocks.tools.Picasso;
import com.nova.maxis7567.learningblocks.tools.dialog.DialogMessage;
import com.nova.maxis7567.learningblocks.tools.dialog.LoadingDialog;


import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.util.Date;

public class SingleBlogActivity extends AppCompatActivity {

    private ViewGroup viewGroup;
    private WebView content;
    private LoadingDialog loading;
    private ImageView bookmark;
    private boolean bookmarkFlag;

    @SuppressLint("SetJavaScriptEnabled")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_blog);
        viewGroup=findViewById(R.id.BlogView);
        loading=new LoadingDialog(this,viewGroup);
        bookmark=findViewById(R.id.blogBookmarkBtn);
        findViewById(R.id.BlogBackBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        getData(getIntent().getIntExtra("id",0));
        bookmark.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (bookmarkFlag){
                    bookmarkHandler(false);
                }else {
                    bookmarkHandler(true);
                }
                bookmark();
            }
        });
    }

    private void bookmark() {
        Api.blogBookmark(this, new Response<JsonObject>() {
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
        },getIntent().getIntExtra("id",0));
    }

    private void getData(final int id) {
        loading.show();
        Api.SingleBlog(this, new Response<Blog>() {
            @Override
            public void respond(Blog respond) {
                bookmarkFlag=respond.isBookmark();
                setUpView(respond);
            }
        }, new ResponseError<JsonObject>() {
            @Override
            public void error(RespondError<JsonObject> error) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {loading.dismiss();
                        DialogMessage dialogMessage= new DialogMessage(SingleBlogActivity.this, viewGroup, true, new DialogMessage.DialogMessageInterface() {
                            @Override
                            public void OnConfirmed() {
                                getData(id);
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
                    public void run() {loading.dismiss();
                        if (message.equals("network not available")){
                            DialogMessage dialogMessage= new DialogMessage(SingleBlogActivity.this, viewGroup, true, new DialogMessage.DialogMessageInterface() {
                                @Override
                                public void OnConfirmed() {
                                    getData(id);
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
                            DialogMessage dialogMessage= new DialogMessage(SingleBlogActivity.this, viewGroup, true, new DialogMessage.DialogMessageInterface() {
                                @Override
                                public void OnConfirmed() {
                                    getData(id);
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
        },id);

    }

    private void setUpView(Blog respond) {
        ((TextView) findViewById(R.id.BlogTitle)).setText(respond.getTitle());
        ((TextView) findViewById(R.id.BlogTime)).setText(getIntent().getStringExtra("time"));
        Picasso.load(getIntent().getStringExtra("image"), ((ImageView) findViewById(R.id.BlogImage)),R.drawable.image_holder_spinner);
        content=findViewById(R.id.BlogWebView);
        bookmarkHandler(bookmarkFlag);
        Document web = Jsoup.parse(respond.getContent());
        web.select("img").attr("width", "100%"); // find all images and set with to 100%
        web.select("video").attr("width", "100%"); // find all videos and set with to 100%
        web.select("figure").attr("style", "width: 100%"); // find all figures and set with to 100%
        web.select("iframe").attr("style", "width: 100%");
        web.select("*").attr("style", "direction: rtl");
        web.select("p").attr("style", "font-family:MyFont");
        web.select("h1").attr("style", "font-family:MyFont");
        web.select("h2").attr("style", "font-family:MyFont");
        web.select("h3").attr("style", "font-family:MyFont");
        RecyclerView recyclerView=findViewById(R.id.BlogRec);
        recyclerView.setAdapter(new TagAdapter(this,respond.getCategoryList()));
        recyclerView.setLayoutManager(new LinearLayoutManager(this,RecyclerView.HORIZONTAL,false));
        content.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                Uri uri = Uri.parse(url);
//                if (uri.getHost() != null && uri.getHost().contains(Api.BASE_URL)) {
//                    return false;
//                }

                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                SingleBlogActivity.this.startActivity(intent);
                return true;
            }

            public void onPageFinished(WebView view, String url) {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        loading.dismiss();
                    }
                },500);

            }
        });
        content.setWebChromeClient(new VideoEnabledWebChromeClient(this));
        WebSettings webSettings=content.getSettings();
        webSettings.setAllowFileAccess(true);
        webSettings.setAppCacheEnabled(true);
        webSettings.setJavaScriptEnabled(true);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            content.getSettings().setLoadWithOverviewMode(true);
            content.getSettings().setUseWideViewPort(true);
        }
        content.getSettings().setDisplayZoomControls(false);
        content.getSettings().setSupportZoom(false);
        webSettings.setDefaultFontSize(new DisplayMetricsUtils(this).convertDIPToPixels(15));
        String a = web.html();
        a = a.replace("</head>",
                "<style type=\"text/css\">\n" +
                        "@font-face {\n" +
                        "    font-family: MyFont;\n" +
                        "    src: url(\"file:///android_asset/font/regular.ttf\")\n" +
                        "}\n" +
                        "body {\n" +
                        "    font-family: MyFont;\n" +
                        "    font-size: " + String.valueOf(new DisplayMetricsUtils(this).convertDIPToPixels(18)) + "px;\n" +
                        "    text-align: justify;\n" +
                        "    line-height: 2;\n" +
                        "    padding: 0 2% 0 2%;\n\n" +
                        "}\n" +
                        "</style></head>");

        content.loadDataWithBaseURL(null, a, "text/html", "UTF-8", null);
    }
    private void bookmarkHandler(boolean b) {
        if (b){
            bookmark.setImageResource(R.drawable.ic_bookmark);
        }else {
            bookmark.setImageResource(R.drawable.ic_bookmark_o);
        }
    }
}
