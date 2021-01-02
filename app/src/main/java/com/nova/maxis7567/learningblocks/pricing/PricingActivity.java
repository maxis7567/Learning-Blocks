package com.nova.maxis7567.learningblocks.pricing;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.google.gson.JsonObject;
import com.hinext.maxis7567.mstools.DisplayMetricsUtils;
import com.hinext.maxis7567.mstools.PriceConvertor;
import com.maxis7567.msvolley.LocalError;
import com.maxis7567.msvolley.RespondError;
import com.maxis7567.msvolley.Response;
import com.maxis7567.msvolley.ResponseError;
import com.nova.maxis7567.learningblocks.R;
import com.nova.maxis7567.learningblocks.models.Package;
import com.nova.maxis7567.learningblocks.pricing.PriceCardsPagerAdapter;
import com.nova.maxis7567.learningblocks.services.Api;
import com.nova.maxis7567.learningblocks.tools.dialog.DialogMessage;
import com.nova.maxis7567.learningblocks.tools.dialog.LoadingDialog;
import com.onurkaganaldemir.ktoastlib.KToast;


import java.util.List;

import me.relex.circleindicator.CircleIndicator;

public class PricingActivity extends AppCompatActivity {
    private PriceCardsPagerAdapter adapter;
    private ViewPager viewPager;
    private CircleIndicator circleIndicator;
    private TextView totalPrice, totalName;
    private LoadingDialog loading;
    private List<Package> data;
    private ViewGroup viewGroup;
    private EditText discount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pricing);
        viewGroup=findViewById(R.id.PricingView);
        viewPager = findViewById(R.id.PricingViewPager);
        discount=findViewById(R.id.PricDiscount);
        circleIndicator = findViewById(R.id.PricingIndicator);
        totalName = findViewById(R.id.PricingTotalName);
        totalPrice = findViewById(R.id.PricingTotalPrice);
        loading = new LoadingDialog(this, viewGroup);
        getData();

    }

    private void getData() {
        loading.show();
        Api.packages(this, new Response<List<Package>>() {
            @Override
            public void respond(final List<Package> respond) {
                if (respond.size()==0){
                    KToast.errorToast(PricingActivity.this,"Package list is empty call administrator", Gravity.BOTTOM,KToast.LENGTH_SHORT,111);
                    finish();
                }
                data=respond;
                setUpView();

            }
        }, new ResponseError<JsonObject>() {
            @Override
            public void error(RespondError<JsonObject> error) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {loading.dismiss();
                        DialogMessage dialogMessage= new DialogMessage(PricingActivity.this, viewGroup, true, new DialogMessage.DialogMessageInterface() {
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
                            DialogMessage dialogMessage= new DialogMessage(PricingActivity.this, viewGroup, true, new DialogMessage.DialogMessageInterface() {
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
                            DialogMessage dialogMessage= new DialogMessage(PricingActivity.this, viewGroup, true, new DialogMessage.DialogMessageInterface() {
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
        });
    }

    private void buy() {
        loading.show();
        Api.buy(this, new Response<JsonObject>() {
            @Override
            public void respond(JsonObject respond) {
                String tmp=respond.get("url").getAsString();
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(tmp));
                startActivity(i);
                finish();
            }
        }, new ResponseError<JsonObject>() {
            @Override
            public void error(RespondError<JsonObject> error) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {loading.dismiss();
                        DialogMessage dialogMessage= new DialogMessage(PricingActivity.this, viewGroup, true, new DialogMessage.DialogMessageInterface() {
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
                            DialogMessage dialogMessage= new DialogMessage(PricingActivity.this, viewGroup, true, new DialogMessage.DialogMessageInterface() {
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
                            DialogMessage dialogMessage= new DialogMessage(PricingActivity.this, viewGroup, true, new DialogMessage.DialogMessageInterface() {
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
        }, data.get(viewPager.getCurrentItem()).getId(),discount.getText().toString());
    }

    private void setUpView() {
        adapter = new PriceCardsPagerAdapter(PricingActivity.this, PricingActivity.this.getSupportFragmentManager(), data);
        viewPager.setAdapter(adapter);
        viewPager.setPageTransformer(false, adapter);
        // Set current item to the middle page so we can fling to both
        // directions left and right

        // Necessary or the mViewPager will only have one extra page to show
        // make this at least however many pages you can see
        viewPager.setOffscreenPageLimit(5);

        // Set margin for pages as a negative number, so a part of next and
        // previous pages will be showed
        viewPager.setPageMargin(0 - (new DisplayMetricsUtils(PricingActivity.this).convertDIPToPixels(100)));

        circleIndicator.setViewPager(viewPager);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                Package tmp = data.get(position);
                setTotals(tmp.getSalePrice(), tmp.getTitle());
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        viewPager.setCurrentItem(1);
        setTotals(data.get(0).getSalePrice(), data.get(0).getTitle());
        loading.dismiss();
        findViewById(R.id.PricingBuyBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buy();
            }
        });
    }

    public void setTotals(float price, String name) {
        totalPrice.setText(PriceConvertor.Convert(price) + " USD");
        totalName.setText(name);
    }
}
