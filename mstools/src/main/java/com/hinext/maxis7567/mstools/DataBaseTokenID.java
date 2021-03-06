package com.hinext.maxis7567.mstools;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;


public class DataBaseTokenID {
    private static final String TOKEN_DB = "T";
    private static final String TOKEN = "Token";

    public static final String DEFAULT_TOKEN_ID="DefaultTokenID";

    public static void WriteTokenID(Context context, String active) {
        SharedPreferences settings;
        SharedPreferences.Editor editor;
        settings = context.getSharedPreferences(TOKEN_DB, Context.MODE_PRIVATE);
        editor = settings.edit();
        editor.putString(TOKEN, active);
        editor.commit();
        Log.d("TokenDataBase", "WriteTokenID: "+active);
    }

    public static void ResetTokenID(Context context) {
        SharedPreferences settings;
        SharedPreferences.Editor editor;
        settings = context.getSharedPreferences(TOKEN_DB, Context.MODE_PRIVATE);
        editor = settings.edit();
        editor.putString(TOKEN,DEFAULT_TOKEN_ID);
        editor.commit();
        Log.d("TokenDataBase", "ResetToken!!!");
    }

    public static String GetTokenID(Context context) {
        SharedPreferences settings;
        settings = context.getSharedPreferences(TOKEN_DB, Context.MODE_PRIVATE);
        return  settings.getString(TOKEN, DEFAULT_TOKEN_ID);

    }
    public static boolean thereIsToken(Context context) {
        SharedPreferences settings;
        settings = context.getSharedPreferences(TOKEN_DB, Context.MODE_PRIVATE);
        return !settings.getString(TOKEN, DEFAULT_TOKEN_ID).equals(DEFAULT_TOKEN_ID);

    }

}