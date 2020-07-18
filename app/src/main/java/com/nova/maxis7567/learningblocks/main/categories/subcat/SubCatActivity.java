package com.nova.maxis7567.learningblocks.main.categories.subcat;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.tabs.TabLayout;
import com.google.gson.JsonObject;
import com.maxis7567.msvolley.LocalError;
import com.maxis7567.msvolley.RespondError;
import com.maxis7567.msvolley.Response;
import com.maxis7567.msvolley.ResponseError;
import com.nova.maxis7567.learningblocks.R;
import com.nova.maxis7567.learningblocks.main.categories.CategoryDescriptionActivity;
import com.nova.maxis7567.learningblocks.models.Category;
import com.nova.maxis7567.learningblocks.models.SubCat;
import com.nova.maxis7567.learningblocks.pricing.PricingActivity;
import com.nova.maxis7567.learningblocks.search.SearchActivity;
import com.nova.maxis7567.learningblocks.services.Api;
import com.nova.maxis7567.learningblocks.tools.Picasso;
import com.nova.maxis7567.learningblocks.tools.dialog.DialogMessage;
import com.nova.maxis7567.learningblocks.tools.dialog.LoadingDialog;

public class SubCatActivity extends AppCompatActivity {
    private ViewGroup viewGroup;
    private ViewPager viewPager;
    private TabLayout tabLayout;
    private LoadingDialog loading;
    private Category category;
    private ImageView bookmark;
    private boolean bookmarkFlag;
    private TextView desc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sub_cat);
        category = (Category) getIntent().getSerializableExtra("cat");
        viewGroup = findViewById(R.id.SubCatView);
        loading = new LoadingDialog(this, viewGroup);
        bookmark=findViewById(R.id.SubCatBookMark);
        findViewById(R.id.SubCatBackBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        findViewById(R.id.SubCatSearchBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SubCatActivity.this, SearchActivity.class));
            }
        });
        viewPager = findViewById(R.id.SubCatViewPager);
        tabLayout = findViewById(R.id.SubCatTabLayout);
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
        Picasso.load(category.getImage(), ((ImageView) findViewById(R.id.SubCatImage)), R.drawable.image_holder_spinner);
        desc = findViewById(R.id.SubCatDesc);
        desc.setText(category.getDesc());
        desc.post(new Runnable() {
            @Override
            public void run() {
                if (desc.getLineCount()>=4){
                    findViewById(R.id.SubCatReadMore).setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent = new Intent(SubCatActivity.this, CategoryDescriptionActivity.class);
                            intent.putExtra("cat", category);
                            startActivity(intent);
                        }
                    });
                }else {
                    findViewById(R.id.SubCatReadMore).setVisibility(View.GONE);
                }
            }
        });

        ((TextView) findViewById(R.id.SubCatCounter)).setText(String.valueOf(category.getCounter()));
        TextView title=findViewById(R.id.SubCatTitle);
        SpannableStringBuilder builder = new SpannableStringBuilder();
        SpannableString str1 = new SpannableString("توضیحات ");
        Typeface font = Typeface.createFromAsset(getAssets(), "font/bold.TTF");
        str1.setSpan (font, 0, str1.length(),Spanned.SPAN_EXCLUSIVE_INCLUSIVE);
        builder.append(str1);
        SpannableString str2 = new SpannableString(category.getName());
        str2.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.textGreen)), 0, str2.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        str2.setSpan (font, 0, str2.length(),Spanned.SPAN_EXCLUSIVE_INCLUSIVE);
        builder.append(str2);
        title.setText(builder, TextView.BufferType.SPANNABLE);
        getData();
    }

    private void getData() {
        loading.show();
        Api.subCategory(this, new Response<SubCat>() {
            @Override
            public void respond(SubCat respond) {
                viewPager.setAdapter(new SubCatViewPager(getSupportFragmentManager(),respond));
                tabLayout.setupWithViewPager(viewPager);
                bookmarkFlag=respond.isBookmark();
                bookmarkHandler(respond.isBookmark());
                loading.dismiss();
            }
        }, new ResponseError<JsonObject>() {
            @Override
            public void error(RespondError<JsonObject> error) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {loading.dismiss();
                        DialogMessage dialogMessage= new DialogMessage(SubCatActivity.this, viewGroup, true, new DialogMessage.DialogMessageInterface() {
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
                });

            }
        }, new LocalError() {
            @Override
            public void error(final String message) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {loading.dismiss();
                        if (message.equals("network not available")){
                            DialogMessage dialogMessage= new DialogMessage(SubCatActivity.this, viewGroup, true, new DialogMessage.DialogMessageInterface() {
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
                            DialogMessage dialogMessage= new DialogMessage(SubCatActivity.this, viewGroup, true, new DialogMessage.DialogMessageInterface() {
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
        },category.getId());
    }

    private void setBookmark() {
        Api.categoryBookmark(this, new Response<JsonObject>() {
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
        },category.getId());
    }

    private void bookmarkHandler(boolean b) {
        if (b){
            bookmark.setImageResource(R.drawable.ic_bookmark);
        }else {
            bookmark.setImageResource(R.drawable.ic_bookmark_o);
        }
    }
}