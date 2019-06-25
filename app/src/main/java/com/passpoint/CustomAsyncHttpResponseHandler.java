package com.passpoint;

import android.util.Log;

import com.loopj.android.http.AsyncHttpResponseHandler;

import cz.msebera.android.httpclient.Header;


//Created for getting status code
public class CustomAsyncHttpResponseHandler extends AsyncHttpResponseHandler {
    String TAG = "SendLog";
    int statusCode = 0;

    @Override
    public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
        Log.w(TAG, String.valueOf(statusCode));
    }

    @Override
    public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
        Log.e(TAG, String.valueOf(statusCode));
        this.statusCode = 1;
    }
}
