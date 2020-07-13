package com.nova.maxis7567.tavan.tools;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.nova.maxis7567.tavan.models.Activity;
import com.nova.maxis7567.tavan.models.RecentActivity;

import java.util.List;


public class DataBaseRecentActivities {
    private static final String TOKEN_DB = "R";
    private static final String TOKEN = "Activities";

    public static final String DEFAULT_TOKEN_ID="[]";

    public static void Write(Context context,Activity activity) {
        List<RecentActivity> list=Get(context);
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).getId()==activity.getId()){
                list.remove(i);
            }
        }
        if (list.size()>=5){
            list.remove(0);
        }
        list.add(new RecentActivity(activity));
        SharedPreferences settings;
        SharedPreferences.Editor editor;
        settings = context.getSharedPreferences(TOKEN_DB, Context.MODE_PRIVATE);
        editor = settings.edit();
        editor.putString(TOKEN, new Gson().toJson(list));
        editor.commit();

    }

    public static void Reset(Context context) {
        SharedPreferences settings;
        SharedPreferences.Editor editor;
        settings = context.getSharedPreferences(TOKEN_DB, Context.MODE_PRIVATE);
        editor = settings.edit();
        editor.putString(TOKEN,DEFAULT_TOKEN_ID);
        editor.commit();

    }

    public static List<RecentActivity> Get(Context context) {
        SharedPreferences settings;
        settings = context.getSharedPreferences(TOKEN_DB, Context.MODE_PRIVATE);
        return  new Gson().fromJson(settings.getString(TOKEN, DEFAULT_TOKEN_ID),new TypeToken<List<RecentActivity>>(){}.getType());

    }
    public static boolean isEmpty(Context context) {
        SharedPreferences settings;
        settings = context.getSharedPreferences(TOKEN_DB, Context.MODE_PRIVATE);
        return settings.getString(TOKEN, DEFAULT_TOKEN_ID).equals(DEFAULT_TOKEN_ID);

    }

}