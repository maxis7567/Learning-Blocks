package com.nova.maxis7567.learningblocks1.tools;

import android.util.Log;
import android.widget.ImageView;

import com.nova.maxis7567.learningblocks1.R;
import com.squareup.picasso.Callback;
import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.NetworkPolicy;

public class Picasso {
    public static void load(final String url, final ImageView imageView, final int placeHolder){
        if (url==null){
            com.squareup.picasso.Picasso.get().load(R.drawable.template_image1);
        }else
        com.squareup.picasso.Picasso.get()
                .load(url)
                .networkPolicy(NetworkPolicy.OFFLINE)
                .placeholder(placeHolder)
                .into(imageView, new Callback() {
                    @Override
                    public void onSuccess() {
                        Log.e("Picasso", "fetch image success in first time.");

                    }

                    @Override
                    public void onError(Exception e) {
                        com.squareup.picasso.Picasso.get().load(url)
                                .networkPolicy(NetworkPolicy.NO_CACHE)
                                .memoryPolicy(MemoryPolicy.NO_CACHE, MemoryPolicy.NO_STORE)
                                .placeholder(placeHolder)
                                .into(imageView, new Callback() {

                                    @Override
                                    public void onSuccess() {
                                        Log.e("Picasso", "fetch image success in try again.");
                                    }

                                    @Override
                                    public void onError(Exception e) {
                                        Log.e("Picasso", e.getMessage());
                                    }

                                });

                    }
                });
    }
}
