package com.maxis7567.msvolley;


import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Handler;
import android.util.Log;
import android.view.Gravity;


import androidx.annotation.Nullable;
import androidx.core.content.IntentCompat;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkResponse;

import com.android.volley.Request;

import com.android.volley.AuthFailureError;
import com.android.volley.VolleyError;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.hinext.maxis7567.mstools.DataBaseTokenID;
import com.onurkaganaldemir.ktoastlib.KToast;


import java.lang.reflect.Type;
import java.nio.charset.StandardCharsets;
import java.util.Map;

public class JsonRequest<T, E> extends Request<T> {

    private final Response<T> responseListener;
    private ResponseError<E> responseError;
    private LocalError localError;
    private Gson gson = new Gson();
    private String body;
    private Map<String, String> header;
    private Type type;
    private Type errType;
    private Context context;

    /**
     * Supported request methods.
     */
    public static int DEPRECATED_GET_OR_POST = -1;
    public static int GET = 0;
    public static int POST = 1;
    public static int PUT = 2;
    public static int DELETE = 3;
    public static int HEAD = 4;
    public static int OPTIONS = 5;
    public static int TRACE = 6;
    public static int PATCH = 7;

    public JsonRequest(Context context, int method, @Nullable RetryPolicy retryPolicy, String url, Type type, Type errType, Response<T> responseListener, ResponseError<E> responseError, LocalError localError) {
        super(method, url, null);
        this.type = type;
        this.errType = errType;
        this.responseListener = responseListener;
        this.responseError = responseError;
        this.localError = localError;
        body = null;
        header = null;
        this.setRetryPolicy(new DefaultRetryPolicy());
        if (retryPolicy != null) {
            this.setRetryPolicy(new DefaultRetryPolicy(retryPolicy.getTimeoutMs(), retryPolicy.getMaxRetries(), retryPolicy.getBackoffMulti()));
        }
        RequestQueueContainer.add(context, this, localError);
        this.context=context;
        Log.d("Api Call", "*********** \n called with: method = [" + method + "]\n, url = [" + url + "]\n, type = [" + type + "]\n, errType = [" + errType + "]");
    }

    public JsonRequest(Context context, int method, @Nullable RetryPolicy retryPolicy, String url, String body, Type type, Type errType, Response<T> responseListener, ResponseError<E> responseError, LocalError localError) {
        super(method, url, null);
        this.body = body;
        this.type = type;
        this.errType = errType;
        this.responseListener = responseListener;
        this.responseError = responseError;
        this.localError = localError;
        header = null;
        this.setRetryPolicy(new DefaultRetryPolicy());
        if (retryPolicy != null) {
            this.setRetryPolicy(new DefaultRetryPolicy(retryPolicy.getTimeoutMs(), retryPolicy.getMaxRetries(), retryPolicy.getBackoffMulti()));
        }
        RequestQueueContainer.add(context, this, localError);
        this.context=context;
        Log.d("Api Call", "*********** \n called with: method = [" + method + "]\n, url = [" + url + "]\n, body = [" + body + "]\n, type = [" + type + "]\n, errType = [" + errType + "]");
    }

    public JsonRequest(Context context, int method, @Nullable RetryPolicy retryPolicy, String url, Map<String, String> header, Type type, Type errType, Response<T> responseListener, ResponseError<E> responseError, LocalError localError) {
        super(method, url, null);
        this.header = header;
        this.type = type;
        this.errType = errType;
        this.responseListener = responseListener;
        this.responseError = responseError;
        this.localError = localError;
        this.setRetryPolicy(new DefaultRetryPolicy());
        if (retryPolicy != null) {
            this.setRetryPolicy(new DefaultRetryPolicy(retryPolicy.getTimeoutMs(), retryPolicy.getMaxRetries(), retryPolicy.getBackoffMulti()));
        }
        RequestQueueContainer.add(context, this, localError);
        this.context=context;
        Log.d("Api Call", "*********** \n called with: method = [" + method + "]\n, url = [" + url + "]\n, header = [" + header + "]\n, type = [" + type + "]\n, errType = [" + errType + "]");
    }

    public JsonRequest(Context context, int method, @Nullable RetryPolicy retryPolicy, String url, String body, Map<String, String> header, Type type, Type errType, Response<T> responseListener, ResponseError<E> responseError, LocalError localError) {
        super(method, url, null);
        this.body = body;
        this.header = header;
        this.type = type;
        this.errType = errType;
        this.responseListener = responseListener;
        this.responseError = responseError;
        this.localError = localError;
        this.setRetryPolicy(new DefaultRetryPolicy());
        if (retryPolicy != null) {
            this.setRetryPolicy(new DefaultRetryPolicy(retryPolicy.getTimeoutMs(), retryPolicy.getMaxRetries(), retryPolicy.getBackoffMulti()));
        }
        RequestQueueContainer.add(context, this, localError);
        this.context=context;
        Log.d("Api Call", "*********** \n called with: method = [" + method + "]\n, url = [" + url + "]\n, body = [" + body + "]\n, header = [" + header + "]\n, type = [" + type + "]\n, errType = [" + errType + "]");
    }

    @Override
    protected com.android.volley.Response<T> parseNetworkResponse(NetworkResponse response) {

        try {
            String stringResponse = new String(response.data, StandardCharsets.UTF_8);
            Log.d("Api Response", "***********\n" + stringResponse);
            T respond;
            if (type == String.class) {
                respond = (T) stringResponse;
            } else {
                respond = gson.fromJson(stringResponse, type);
            }
            return com.android.volley.Response.success(respond, null);

        } catch (JsonParseException e) {
            String stringResponse = new String(response.data, StandardCharsets.UTF_8);
            Log.d("Api JsonParseException", "***********\n" + stringResponse);
            localError.error(e.toString());
            return null;
        }
    }

    @Override
    protected VolleyError parseNetworkError(VolleyError volleyError) {
        if (volleyError.networkResponse != null) {
            if (volleyError.networkResponse.statusCode==403){
                String stringResponse = new String(volleyError.networkResponse.data, StandardCharsets.UTF_8);
                JsonObject jsonObject=new Gson().fromJson(stringResponse, JsonObject.class);
                if (jsonObject.get("message").getAsString().equals("not package is active")){
                    E respond = gson.fromJson(stringResponse, errType);
                    RespondError<E> error = new RespondError<>(volleyError.getMessage(), volleyError.networkResponse.statusCode, respond);
                    responseError.error(error);
                }else {
                    DataBaseTokenID.ResetTokenID(context);

                    ((Activity) context).runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            KToast.errorToast(((Activity) context), "There is a problem with your authentication. Please log in again.", Gravity.BOTTOM, KToast.LENGTH_AUTO, 1);
                            new Handler().postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    final PackageManager pm = ((Activity) context).getPackageManager();
                                    final Intent intent = pm.getLaunchIntentForPackage(((Activity) context).getPackageName());
                                    ((Activity) context).finishAffinity(); // Finishes all activities.
                                    ((Activity) context).startActivity(intent);
                                    System.exit(0);
                                }
                            }, 3000);
                        }
                    });
                }

            }else {
                String stringResponse;
                Log.d("Api Response", "***********\n" + new String(volleyError.networkResponse.data));
                try {
                    stringResponse = new String(volleyError.networkResponse.data, StandardCharsets.UTF_8);
                    E respond;
                    if (errType == String.class) {
                        respond = (E) stringResponse;
                        RespondError<E> error = new RespondError<>(volleyError.getMessage(), volleyError.networkResponse.statusCode, respond);
                        responseError.error(error);
                    } else {
                        respond = gson.fromJson(stringResponse, errType);
                        RespondError<E> error = new RespondError<>(volleyError.getMessage(), volleyError.networkResponse.statusCode, respond);
                        responseError.error(error);
                    }
                } catch (JsonParseException e) {
                    localError.error(e.toString());
                }
            }
        } else {
            if (volleyError.getMessage() != null) {
                localError.error(volleyError.getMessage());
            } else
                localError.error("network not available");
        }
        return null;
    }

    @Override
    protected void deliverResponse(T response) {
        if (response != null) {
            responseListener.respond(response);
        }
    }

    @Override
    public Map<String, String> getHeaders() throws AuthFailureError {
        if (header == null) {
            return super.getHeaders();
        } else
            return header;
    }

    @Override
    public byte[] getBody() throws AuthFailureError {
        if (body == null) {
            return super.getBody();
        } else
            return body.getBytes();

    }


}
