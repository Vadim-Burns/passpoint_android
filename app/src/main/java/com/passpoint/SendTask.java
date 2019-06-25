package com.passpoint;

import android.os.AsyncTask;
import android.util.Log;

import com.google.gson.Gson;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import java.io.FileNotFoundException;
import java.util.Arrays;

import cz.msebera.android.httpclient.Header;

public class SendTask extends AsyncTask<Send, Void, Void> {
    static AsyncHttpClient client = new AsyncHttpClient(8080);
    static String TAG = "SendLog";
    static final String addr = "http://10.10.6.124/api/add_note";

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
            public void onFailure(int statusCode, Header[] headers, byte[] errorResponse, Throwable e) {
                Log.e(TAG, String.valueOf(statusCode));
            }
        });

        return null;
    }
}
