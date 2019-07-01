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
    static final String addr = "http://81.23.11.22/api/add_note";
//    static final String addr = "http://5.8.180.39/api/add_note";

    @Override
    protected Void doInBackground(Send... sends) {

        Log.w(TAG, "SendTask started");

        Send send = sends[0];
        Gson gson = new Gson();

        Log.w(TAG, gson.toJson(send));


        RequestParams params = new RequestParams();
//        params.put("firstName", send.person.firstName);
//        params.put("middleName", send.person.middleName);
//        params.put("lastName", send.person.lastName);
        params.put("Place", send.place);
        params.put("IdDevice", send.IdDevice);
        try {
            params.put("name", send.person.name);
        } catch (FileNotFoundException e) {
            Log.e(TAG, "Name not found");
        }

        try {
            params.put("signature", send.person.signature);
        } catch (FileNotFoundException e) {
            Log.e(TAG, "Signature not found");
        }

        client.post(addr, params, new CustomAsyncHttpResponseHandler(client, addr, params, send.person.name, send.person.signature));

        return null;
    }
}
