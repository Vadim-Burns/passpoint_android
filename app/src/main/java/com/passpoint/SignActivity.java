package com.passpoint;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.passpoint.passpoint.R;

import java.net.NetworkInterface;
import java.util.Collections;
import java.util.List;

public class SignActivity extends AppCompatActivity {

    private static String TAG = "SignLog";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.w(TAG, "onCreate");
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_sign);

        View decorView = getWindow().getDecorView();
        int uiOptions = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY;
        decorView.setSystemUiVisibility(uiOptions);


        //customize action bar
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        //creating dialog window
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        //set language
        final String hint;
        if (MainActivity.lang == "RU") {

            Button sendSign = (Button) findViewById(R.id.sendSign);
            sendSign.setText(getResources().getString(R.string.send_sign_rus));
            sendSign.setTypeface(MainActivity.boldFont);
            Button back = (Button) findViewById(R.id.back_button);
            back.setText(getResources().getString(R.string.back_button_rus));
            back.setTypeface(MainActivity.boldFont);
            ImageView clear_button = (ImageView) findViewById(R.id.clear_button);
            clear_button.setImageResource(R.drawable.clear_rus);
            TextView hintName = findViewById(R.id.hint_name);
            hintName.setText(getResources().getString(R.string.hint_name_rus));
            hintName.setTypeface(MainActivity.smallFont);
            TextView hintSign = findViewById(R.id.hint_sign);
            hintSign.setText(getResources().getString(R.string.hint_sign_rus));
            hintSign.setTypeface(MainActivity.smallFont);

            builder.setMessage(getResources().getString(R.string.hint_rus));
        } else {

            Button sendSign = (Button) findViewById(R.id.sendSign);
            sendSign.setText(getResources().getString(R.string.send_sign_eng));
            sendSign.setTypeface(MainActivity.boldFont);
            Button back = (Button) findViewById(R.id.back_button);
            back.setText(getResources().getString(R.string.back_button_eng));
            back.setTypeface(MainActivity.boldFont);
            ImageView clear_button = (ImageView) findViewById(R.id.clear_button);
            clear_button.setImageResource(R.drawable.clear_eng);
            TextView hintName = findViewById(R.id.hint_name);
            hintName.setText(getResources().getString(R.string.hint_name_eng));
            hintName.setTypeface(MainActivity.smallFont);
            TextView hintSign = findViewById(R.id.hint_sign);
            hintSign.setText(getResources().getString(R.string.hint_sign_eng));
            hintSign.setTypeface(MainActivity.smallFont);

            builder.setMessage(getResources().getString(R.string.hint_eng));
        }

        if (getIntent().getStringExtra("Info").equals("yes")) {
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
            textView.setTypeface(MainActivity.smallFont);
        }

    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    public void sendSign(View view) {

//        EditText editText = findViewById(R.id.name_edittext);
//        String[] name = editText.getText().toString().split(" ");

        Toast toast = null;
//        if (name.length < 2) {
//            Log.e(TAG, "name is too short");
//            if (MainActivity.lang == "RU") {
//                toast = Toast.makeText(this, "Введите полное ФИО!", Toast.LENGTH_LONG);
//            }
//            else {
//                toast = Toast.makeText(this, "Enter full name!", Toast.LENGTH_LONG);
//            }
//            return;
//        }
//
//        //if middleName doesn't exists
//        if (name.length == 2) {
//            name = Arrays.copyOf(name, name.length + 1);
//            name[name.length - 1] = "-";
//        }


        Log.w(TAG, "Getting name");
        DrawingView nameView = findViewById(R.id.name_drawview);

        Log.w(TAG, "Getting Sign");
        DrawingView signView = findViewById(R.id.sign_view);

        String mes = "";
        if (!nameView.isTouched()) {
            if (MainActivity.lang== "RU") mes = getResources().getString(R.string.noname_rus);
            else mes = getResources().getString(R.string.noname_eng);
            toast = Toast.makeText(this, mes, Toast.LENGTH_LONG);
//            toast.setGravity(Gravity.CENTER,0,0);
            toast.show();
            return;
        }

        if (!signView.isTouched()) {
            if (MainActivity.lang== "RU") mes = getResources().getString(R.string.nosign_rus);
            else mes = getResources().getString(R.string.nosign_eng);
            toast = Toast.makeText(this, mes, Toast.LENGTH_LONG);
//            toast.setGravity(Gravity.CENTER,0,0);
            toast.show();
            return;
        }

//        Send send = new Send(String.valueOf(getMacAddr().hashCode()), "1", name[1], name[2], name[0], signView.getSign());
        Send send = new Send(String.valueOf(getMacAddr().hashCode()), "1", nameView.getImage(1050, 570), signView.getImage(256, 256));
//
        new SendTask().doInBackground(send);


        //result of sending
        mes = "";
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

    public void clearAll(View view) {
        Intent intent = new Intent(this, SignActivity.class);
        intent.putExtra("Info", "no");
        startActivity(intent);
    }

    public void backPressed(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }
}
