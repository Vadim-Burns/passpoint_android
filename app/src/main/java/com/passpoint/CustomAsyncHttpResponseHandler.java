package com.passpoint;

import android.os.Looper;
import android.os.SystemClock;
import android.text.format.Time;
import android.util.Log;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import java.io.File;
import java.io.FileNotFoundException;

import cz.msebera.android.httpclient.Header;

public class CustomAsyncHttpResponseHandler extends AsyncHttpResponseHandler {
    private static String TAG = "CustomHttpHandler";
    private RequestParams params;
    private AsyncHttpClient client;
    private String addr;
    private File name;
    private File signature;

    public CustomAsyncHttpResponseHandler(AsyncHttpClient client, String addr, RequestParams params, File name, File signature) {
        this.client = client;
        this.addr = addr;
        this.params = params;
        this.name = name;
        this.signature = signature;
    }

    public CustomAsyncHttpResponseHandler(Looper looper) {
        super(looper);
    }

    public CustomAsyncHttpResponseHandler(boolean usePoolThread) {
        super(usePoolThread);
    }

    @Override
    public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
        Log.w(TAG, String.valueOf(statusCode));

        Log.w(TAG, "Deleting name....");
        this.name.delete();

        Log.w(TAG, "Deleting signature...");
        this.signature.delete();
    }

    @Override
    public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
        Log.e(TAG, String.valueOf(statusCode));

        if (statusCode == 0) {
            Log.w(TAG, "restart the request in 60 seconds");
            SystemClock.sleep(60000);
            this.client.post(this.addr, this.params, this);
        }

        Log.w(TAG, "Deleting name....");
        this.name.delete();

        Log.w(TAG, "Deleting signature...");
        this.signature.delete();
    }
}
