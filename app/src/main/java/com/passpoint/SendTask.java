package com.passpoint;

import android.os.AsyncTask;
import android.util.Log;

import com.google.gson.Gson;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestHandle;
import com.loopj.android.http.RequestParams;

import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.concurrent.TimeUnit;

import cz.msebera.android.httpclient.Header;

public class SendTask extends AsyncTask<Send, Void, Void> {
    static AsyncHttpClient client = new AsyncHttpClient(8080);
    static String TAG = "SendLog";
    static final String addr = "http://5.8.180.39/api/add_note";

    @Override
    protected Void doInBackground(Send... sends) {

        Log.w(TAG, "SendTask started");

        Send send = sends[0];
        Gson gson = new Gson();

        Log.w(TAG, gson.toJson(send));


        RequestParams params = new RequestParams();
        params.put("firstName", send.person.firstName);
        params.put("middleName", send.person.middleName);
        params.put("lastName", send.person.lastName);
        params.put("Place", send.place);
        params.put("IdDevice", send.IdDevice);
        try {
            params.put("signature", send.person.signature);
        } catch (FileNotFoundException e) {
            Log.e(TAG, "FileNotFound");
        }

        client.post(addr, params, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                Log.w(TAG, String.valueOf(statusCode));
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                Log.e(TAG, String.valueOf(statusCode));
            }
        });
//        while(true) {
//            CustomAsyncHttpResponseHandler customAsyncHttpResponseHandler = new CustomAsyncHttpResponseHandler();
//            client.post(addr, params, customAsyncHttpResponseHandler);
//            break;
////            try {
////                TimeUnit.SECONDS.sleep(30);
////            } catch (InterruptedException e) {
////                e.printStackTrace();
////            }
////            if (customAsyncHttpResponseHandler.statusCode == 1) {
////                Log.e(TAG, "Trying again");
////                break;
////            } else {
////                Log.w(TAG, "It's okay");
////            }
//        }

        return null;
    }
}
