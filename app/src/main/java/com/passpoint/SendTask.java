package com.passpoint;

import android.os.AsyncTask;
import android.util.Log;

import com.google.gson.Gson;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import java.io.ByteArrayInputStream;

import cz.msebera.android.httpclient.Header;

public class SendTask extends AsyncTask<Send, Void, Void> {
    static AsyncHttpClient client = new AsyncHttpClient(8080);
    static String TAG = "SendLog";
    static final String addr = "http://81.23.11.22/api/add_note";

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
        params.put("Place", "0");
        params.put("IdDevice", "1");
        params.put("signature", new ByteArrayInputStream(send.person.signature));

        client.post(addr, params, new AsyncHttpResponseHandler() {

            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                Log.w(TAG, String.valueOf(statusCode));
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] errorResponse, Throwable e) {
                // called when response HTTP status is "4XX" (eg. 401, 403, 404)
                Log.e(TAG, String.valueOf(statusCode));
            }
        });

        return null;
    }
}
