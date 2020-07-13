package com.nova.maxis7567.tavan.services;

import android.content.Context;
import android.util.ArrayMap;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import com.hinext.maxis7567.mstools.DataBaseTokenID;
import com.maxis7567.msvolley.JsonRequest;
import com.maxis7567.msvolley.LocalError;
import com.maxis7567.msvolley.Response;
import com.maxis7567.msvolley.ResponseError;
import com.maxis7567.msvolley.RetryPolicy;
import com.nova.maxis7567.tavan.BuildConfig;
import com.nova.maxis7567.tavan.models.Activity;
import com.nova.maxis7567.tavan.models.Blog;
import com.nova.maxis7567.tavan.models.Bookmark;
import com.nova.maxis7567.tavan.models.Category;
import com.nova.maxis7567.tavan.models.Dashboard;
import com.nova.maxis7567.tavan.models.Package;
import com.nova.maxis7567.tavan.models.SubCat;


import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static com.maxis7567.msvolley.RequestQueueContainer.add;

public class Api {
    public static final String BASE_URL = "https://tavanyaar.ir/wp-json/api/v1/";
    public static final String DOMAIN = "https://tavanyaar.ir/";
    static Gson gson = new Gson();
    private static RetryPolicy retryPolicy=new RetryPolicy().setMaxRetries(2).setTimeoutMs(5000);

    public static void checkVersion(Context context, Response<JsonObject> respond, ResponseError<JsonObject> responseError, LocalError localError) {
        JsonRequest<JsonObject, JsonObject> a = new JsonRequest<>(context,
                JsonRequest.GET, retryPolicy,
                BASE_URL + "checkversion?appVersion=" + BuildConfig.VERSION_CODE,
                JsonObject.class,
                JsonObject.class,
                respond,
                responseError,
                localError
        );

    }

    public static void sendNumber(Context context, Response<JsonObject> respond, ResponseError<JsonObject> responseError, LocalError localError, String number) {
        Map<String, String> header = new ArrayMap<>();
        header.put("Accept", "application/json");
        header.put("Content-Type", "application/json");
        JsonObject body = new JsonObject();
        body.addProperty("phone", number);
        JsonRequest<JsonObject, JsonObject> a = new JsonRequest<>(context,
                JsonRequest.POST, new RetryPolicy().setMaxRetries(0).setTimeoutMs(10000),
                BASE_URL + "sendcodesms",
                body.toString(),
                header,
                JsonObject.class,
                JsonObject.class,
                respond,
                responseError,
                localError
        );

    }

    public static void login(Context context, Response<JsonObject> respond, ResponseError<JsonObject> responseError, LocalError localError, String phone, String code) {
        Map<String, String> header = new ArrayMap<>();
        header.put("Accept", "application/json");
        header.put("Content-Type", "application/json");
        JsonObject body = new JsonObject();
        body.addProperty("phone", phone);
        body.addProperty("code", code);
        JsonRequest<JsonObject, JsonObject> a = new JsonRequest<>(context,
                JsonRequest.POST, retryPolicy,
                BASE_URL + "login",
                body.toString(),
                header,
                JsonObject.class,
                JsonObject.class,
                respond,
                responseError,
                localError
        );

    }

    public static void registerUser(Context context, Response<JsonObject> respond, ResponseError<JsonObject> responseError, LocalError localError, String name, String lastName, String token) {
        Map<String, String> header = new ArrayMap<>();
        header.put("Accept", "application/json");
        header.put("Content-Type", "application/json");
        header.put("Authorization", "Bearer " + token);
        JsonObject body = new JsonObject();
        body.addProperty("name", name);
        body.addProperty("lName", lastName);
        JsonRequest<JsonObject, JsonObject> a = new JsonRequest<>(context,
                JsonRequest.POST, retryPolicy,
                BASE_URL + "activeuser",
                body.toString(),
                header,
                JsonObject.class,
                JsonObject.class,
                respond,
                responseError,
                localError
        );

    }

    public static void dashboard(Context context, Response<Dashboard> respond, ResponseError<JsonObject> responseError, LocalError localError) {
        Map<String, String> header = new ArrayMap<>();
        header.put("Accept", "application/json");
        header.put("Content-Type", "application/json");
        header.put("Authorization", "Bearer " + DataBaseTokenID.GetTokenID(context));
        JsonRequest<Dashboard, JsonObject> a = new JsonRequest<>(context,
                JsonRequest.GET, retryPolicy,
                BASE_URL + "dashboard",
                header,
                Dashboard.class,
                JsonObject.class,
                respond,
                responseError,
                localError
        );
    }

    public static void blogList(Context context, Response<JsonObject> respond, ResponseError<JsonObject> responseError, LocalError localError, int page) {
        Map<String, String> header = new ArrayMap<>();
        header.put("Accept", "application/json");
        header.put("Content-Type", "application/json");
        header.put("Authorization", "Bearer " + DataBaseTokenID.GetTokenID(context));
        JsonRequest<JsonObject, JsonObject> a = new JsonRequest<>(context,
                JsonRequest.GET, retryPolicy,
                BASE_URL + "bloglist?page=" + page,
                header,
                JsonObject.class,
                JsonObject.class,
                respond,
                responseError,
                localError
        );
    }

    public static void category0(Context context, Response<List<Category>> respond, ResponseError<JsonObject> responseError, LocalError localError) {
        Map<String, String> header = new ArrayMap<>();
        header.put("Accept", "application/json");
        header.put("Content-Type", "application/json");
        header.put("Authorization", "Bearer " + DataBaseTokenID.GetTokenID(context));
        JsonRequest<List<Category>, JsonObject> a = new JsonRequest<>(context,
                JsonRequest.GET, retryPolicy,
                BASE_URL + "catlevel0",
                header,
                new TypeToken<List<Category>>() {
                }.getType(),
                JsonObject.class,
                respond,
                responseError,
                localError
        );
    }

    public static void bookmark(Context context, Response<Bookmark> respond, ResponseError<JsonObject> responseError, LocalError localError) {
        Map<String, String> header = new ArrayMap<>();
        header.put("Accept", "application/json");
        header.put("Content-Type", "application/json");
        header.put("Authorization", "Bearer " + DataBaseTokenID.GetTokenID(context));
        JsonRequest<Bookmark, JsonObject> a = new JsonRequest<>(context,
                JsonRequest.GET, retryPolicy,
                BASE_URL + "listbookmark",
                header,
                Bookmark.class,
                JsonObject.class,
                respond,
                responseError,
                localError
        );
    }

    public static void SingleBlog(Context context, Response<Blog> respond, ResponseError<JsonObject> responseError, LocalError localError, int id) {
        Map<String, String> header = new ArrayMap<>();
        header.put("Accept", "application/json");
        header.put("Content-Type", "application/json");
        header.put("Authorization", "Bearer " + DataBaseTokenID.GetTokenID(context));
        JsonRequest<Blog, JsonObject> a = new JsonRequest<>(context,
                JsonRequest.GET, retryPolicy,
                BASE_URL + "singleblog?id=" + id,
                header,
                Blog.class,
                JsonObject.class,
                respond,
                responseError,
                localError
        );
    }

    public static void subCategory(Context context, Response<SubCat> respond, ResponseError<JsonObject> responseError, LocalError localError, int id) {
        Map<String, String> header = new ArrayMap<>();
        header.put("Accept", "application/json");
        header.put("Content-Type", "application/json");
        header.put("Authorization", "Bearer " + DataBaseTokenID.GetTokenID(context));
        JsonRequest<SubCat, JsonObject> a = new JsonRequest<>(context,
                JsonRequest.GET, retryPolicy,
                BASE_URL + "catlevel1?catId=" + id,
                header,
                SubCat.class,
                JsonObject.class,
                respond,
                responseError,
                localError
        );
    }

    public static void categoryBookmark(Context context, Response<JsonObject> respond, ResponseError<JsonObject> responseError, LocalError localError, int id) {
        Map<String, String> header = new ArrayMap<>();
        header.put("Accept", "application/json");
        header.put("Content-Type", "application/json");
        header.put("Authorization", "Bearer " + DataBaseTokenID.GetTokenID(context));
        JsonRequest<JsonObject, JsonObject> a = new JsonRequest<>(context,
                JsonRequest.POST, retryPolicy,
                BASE_URL + "bookmarkcategory?id=" + id,
                header,
                JsonObject.class,
                JsonObject.class,
                respond,
                responseError,
                localError
        );
    }

    public static void blogBookmark(Context context, Response<JsonObject> respond, ResponseError<JsonObject> responseError, LocalError localError, int id) {
        Map<String, String> header = new ArrayMap<>();
        header.put("Accept", "application/json");
        header.put("Content-Type", "application/json");
        header.put("Authorization", "Bearer " + DataBaseTokenID.GetTokenID(context));
        JsonRequest<JsonObject, JsonObject> a = new JsonRequest<>(context,
                JsonRequest.POST, retryPolicy,
                BASE_URL + "bookmarkblog?id=" + id,
                header,
                JsonObject.class,
                JsonObject.class,
                respond,
                responseError,
                localError
        );
    }

    public static void activityBookmark(Context context, Response<JsonObject> respond, ResponseError<JsonObject> responseError, LocalError localError, int id) {
        Map<String, String> header = new ArrayMap<>();
        header.put("Accept", "application/json");
        header.put("Content-Type", "application/json");
        header.put("Authorization", "Bearer " + DataBaseTokenID.GetTokenID(context));
        JsonRequest<JsonObject, JsonObject> a = new JsonRequest<>(context,
                JsonRequest.POST, retryPolicy,
                BASE_URL + "bookmarkactivity?id=" + id,
                header,
                JsonObject.class,
                JsonObject.class,
                respond,
                responseError,
                localError
        );
    }

    public static void packages(Context context, Response<List<Package>> respond, ResponseError<JsonObject> responseError, LocalError localError) {
        Map<String, String> header = new ArrayMap<>();
        header.put("Accept", "application/json");
        header.put("Content-Type", "application/json");
        header.put("Authorization", "Bearer " + DataBaseTokenID.GetTokenID(context));
        JsonRequest<List<Package>, JsonObject> a = new JsonRequest<>(context,
                JsonRequest.GET, retryPolicy,
                BASE_URL + "packages",
                header,
                new TypeToken<List<Package>>() {
                }.getType(),
                JsonObject.class,
                respond,
                responseError,
                localError
        );
    }

    public static void buy(Context context, Response<JsonObject> respond, ResponseError<JsonObject> responseError, LocalError localError, int id) {
        Map<String, String> header = new ArrayMap<>();
        header.put("Accept", "application/json");
        header.put("Content-Type", "application/json");
        JsonObject body = new JsonObject();
        body.addProperty("package_id", id);
        header.put("Authorization", "Bearer " + DataBaseTokenID.GetTokenID(context));
        JsonRequest<JsonObject, JsonObject> a = new JsonRequest<>(context,
                JsonRequest.POST, new RetryPolicy().setMaxRetries(0).setTimeoutMs(10000),
                BASE_URL + "package/buy",
                body.toString(),
                header,
                JsonObject.class,
                JsonObject.class,
                respond,
                responseError,
                localError
        );
    }

    public static void activitySearch(Context context, Response<JsonObject> respond, ResponseError<JsonObject> responseError, LocalError localError, String q, int page) {
        if (q.length() > 0) {
            Map<String, String> header = new ArrayMap<>();
            header.put("Accept", "application/json");
            header.put("Content-Type", "application/json");
            header.put("Authorization", "Bearer " + DataBaseTokenID.GetTokenID(context));
            JsonRequest<JsonObject, JsonObject> a = new JsonRequest<>(context,
                    JsonRequest.GET, retryPolicy,
                    BASE_URL + "searchactivity?q=" + q + "&page=" + page,
                    header,
                    JsonObject.class,
                    JsonObject.class,
                    respond,
                    responseError,
                    localError
            );
        }
    }

    public static void catSearch(Context context, Response<JsonObject> respond, ResponseError<JsonObject> responseError, LocalError localError, String q, int page) {
        if (q.length() > 0) {
            Map<String, String> header = new ArrayMap<>();
            header.put("Accept", "application/json");
            header.put("Content-Type", "application/json");
            header.put("Authorization", "Bearer " + DataBaseTokenID.GetTokenID(context));
            JsonRequest<JsonObject, JsonObject> a = new JsonRequest<>(context,
                    JsonRequest.GET, retryPolicy,
                    BASE_URL + "searchcategory?q=" + q + "&page=" + page,
                    header,
                    JsonObject.class,
                    JsonObject.class,
                    respond,
                    responseError,
                    localError
            );
        }
    }

    public static void activity(Context context, Response<Activity> respond, ResponseError<JsonObject> responseError, LocalError localError, int id) {
        Map<String, String> header = new ArrayMap<>();
        header.put("Accept", "application/json");
        header.put("Content-Type", "application/json");
        header.put("Authorization", "Bearer " + DataBaseTokenID.GetTokenID(context));
        JsonRequest<Activity, JsonObject> a = new JsonRequest<>(context,
                JsonRequest.GET, retryPolicy,
                BASE_URL + "singleactivity?id=" + id,
                header,
                Activity.class,
                JsonObject.class,
                respond,
                responseError,
                localError
        );
    }
}