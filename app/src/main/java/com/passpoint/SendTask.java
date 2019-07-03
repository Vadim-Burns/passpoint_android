package com.passpoint;

import android.os.AsyncTask;
import android.util.Log;

import com.google.gson.Gson;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.RequestParams;

import java.io.FileNotFoundException;

public class SendTask extends AsyncTask<Send, Void, Void> {
    static AsyncHttpClient client = new AsyncHttpClient(80);
    static String TAG = "SendLog";
    static final String addr = "http://5.8.180.39/add_note";
//    static final String addr = "http://5.8.180.39/api/add_note";

    @Override
    protected Void doInBackground(Send... sends) {

        Log.w(TAG, "SendTask started");

        Send send = sends[0];
        Gson gson = new Gson();

        Log.w(TAG, gson.toJson(send));


        RequestParams params = new RequestParams();
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


        //starting post request, it will be alive before server got this request, files will be deleted after that
        client.post(addr, params, new CustomAsyncHttpResponseHandler(client, addr, params, send.person.name, send.person.signature));

        return null;
    }
}
