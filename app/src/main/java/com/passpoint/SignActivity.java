package com.passpoint;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.passpoint.passpoint.R;

import java.io.File;
import java.net.NetworkInterface;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class SignActivity extends AppCompatActivity {

    private static String TAG = "SignLog";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.w(TAG, "onCreate");
        setContentView(R.layout.activity_sign);


        //customize action bar
        ActionBar actionBar = getSupportActionBar();
        actionBar.setLogo(R.mipmap.croc_logo);
        actionBar.setDisplayUseLogoEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setTitle("");
        actionBar.setBackgroundDrawable(new ColorDrawable(0xFF0000));

        //creating dialog window
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        //set language
        final String hint;
        if (MainActivity.lang == "RU") {
            EditText editText = (EditText) findViewById(R.id.name_edittext);
            editText.setHint(getResources().getString(R.string.name_rus));

            Button button = (Button) findViewById(R.id.sendSign);
            button.setText(getResources().getString(R.string.send_sign_rus));
            TextView hintSign = findViewById(R.id.hint_sign);
            hintSign.setText(getResources().getString(R.string.hint_sign_rus));

            builder.setMessage(getResources().getString(R.string.hint_rus));
        } else {
            EditText editText = (EditText) findViewById(R.id.name_edittext);
            editText.setHint(getResources().getString(R.string.name_eng));

            Button button = (Button) findViewById(R.id.sendSign);
            button.setText(getResources().getString(R.string.send_sign_eng));
            TextView hintSign = findViewById(R.id.hint_sign);
            hintSign.setText(getResources().getString(R.string.hint_sign_eng));

            builder.setMessage(getResources().getString(R.string.hint_eng));
        }

        //show dialog window
        builder.setNeutralButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.cancel();
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
        TextView textView = (TextView) dialog.findViewById(android.R.id.message);
        textView.setTextSize(24);

        //showing toast for 5 seconds
//        new CountDownTimer(4000, 900) {
//            @Override
//            public void onTick(long l) {
//                Toast.makeText(getApplicationContext(), hint, Toast.LENGTH_LONG).show();
//            }
//
//            @Override
//            public void onFinish() {
//            }
//        }.start();
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    public void sendSign(View view) {

        EditText editText = findViewById(R.id.name_edittext);
        String[] name = editText.getText().toString().split(" ");

        if (name.length < 2) {
            if (MainActivity.lang == "RU") Toast.makeText(this, "Введите полное ФИО!", Toast.LENGTH_LONG).show();
            else Toast.makeText(this, "Enter full name!", Toast.LENGTH_LONG).show();
            return;
        }

        //if middleName doesn't exists
        if (name.length == 2) {
            name = Arrays.copyOf(name, name.length + 1);
            name[name.length - 1] = "-";
        }

        Log.w(TAG, "Getting Sign");
        DrawingView signView = findViewById(R.id.sign_view);

        Send send = new Send(String.valueOf(getMacAddr().hashCode()), "1", name[1], name[2], name[0], signView.getSign());

        new SendTask().doInBackground(send);


        //result of sending
        String mes = "";
        if (!isOnline()) {
            if (MainActivity.lang == "RU") mes = getResources().getString(R.string.connection_error_rus);
            else mes = getResources().getString(R.string.connection_error_eng);
        }
        else {
            if (MainActivity.lang == "RU") mes = getResources().getString(R.string.send_ok_rus);
            else mes = getResources().getString(R.string.send_ok_eng);
        }
        Toast.makeText(this, mes, Toast.LENGTH_LONG).show();


        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
                finish();
            }
        }, 100);
    }

    protected boolean isOnline() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        if (netInfo != null && netInfo.isConnected()) {
            return true;
        } else {
            return false;
        }
    }

    public static String getMacAddr() {
        try {
            List<NetworkInterface> all = Collections.list(NetworkInterface.getNetworkInterfaces());
            for (NetworkInterface nif : all) {
                if (!nif.getName().equalsIgnoreCase("wlan0")) continue;

                byte[] macBytes = nif.getHardwareAddress();
                if (macBytes == null) {
                    return "";
                }

                StringBuilder res1 = new StringBuilder();
                for (byte b : macBytes) {
                    res1.append(String.format("%02X:",b));
                }

                if (res1.length() > 0) {
                    res1.deleteCharAt(res1.length() - 1);
                }
                return res1.toString();
            }
        } catch (Exception ex) {
        }
        return "02:00:00:00:00:00";
    }
}
