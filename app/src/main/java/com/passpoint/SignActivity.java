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
    private static int nameWidth = 1050;
    private static int nameHeight = 600;
    private static int signWidth = 400;
    private static int signHeight = 300;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //hidding title
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_sign);

        //hidding movebar
        View decorView = getWindow().getDecorView();
        int uiOptions = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY;
        decorView.setSystemUiVisibility(uiOptions);


        //hidding action bar
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        //creating dialog window
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        //getting parts of the layout, where there is a text
        Button sendSign = (Button) findViewById(R.id.sendSign);
        Button back_button = (Button) findViewById(R.id.back_button);
        ImageView clear_button = (ImageView) findViewById(R.id.clear_button);
        TextView hintName = findViewById(R.id.hint_name);
        TextView hintSign = findViewById(R.id.hint_sign);

        //setting language
        if (MainActivity.lang == "RU") {

            sendSign.setText(getResources().getString(R.string.send_sign_rus));
            sendSign.setTypeface(MainActivity.boldFont);

            back_button.setText(getResources().getString(R.string.back_button_rus));
            back_button.setTypeface(MainActivity.boldFont);

            clear_button.setImageResource(R.drawable.clear_rus);

            hintName.setText(getResources().getString(R.string.hint_name_rus));
            hintName.setTypeface(MainActivity.smallFont);

            hintSign.setText(getResources().getString(R.string.hint_sign_rus));
            hintSign.setTypeface(MainActivity.smallFont);

            builder.setMessage(getResources().getString(R.string.hint_rus));
        } else {

            sendSign.setText(getResources().getString(R.string.send_sign_eng));
            sendSign.setTypeface(MainActivity.boldFont);

            back_button.setText(getResources().getString(R.string.back_button_eng));
            back_button.setTypeface(MainActivity.boldFont);

            clear_button.setImageResource(R.drawable.clear_eng);
            hintName.setText(getResources().getString(R.string.hint_name_eng));
            hintName.setTypeface(MainActivity.smallFont);

            hintSign.setText(getResources().getString(R.string.hint_sign_eng));
            hintSign.setTypeface(MainActivity.smallFont);

            builder.setMessage(getResources().getString(R.string.hint_eng));
        }

        //if intent param "Info" is yes, AlertDialog with info will be shown
        //it is created for clearAll function
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

            //setting fontSize of AlertDialog window
            TextView alertDialogTextView = (TextView) dialog.findViewById(android.R.id.message);
            alertDialogTextView.setTextSize(24);
            alertDialogTextView.setTypeface(MainActivity.smallFont);
        }

    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    public void sendSign(View view) {

        Toast toast = null;

        DrawingView nameView = findViewById(R.id.name_drawview);
        DrawingView signView = findViewById(R.id.sign_view);

        //Toast to remember filling name
        String mes;
        if (!nameView.isTouched()) {

            //setting language
            if (MainActivity.lang== "RU") mes = getResources().getString(R.string.noname_rus);
            else mes = getResources().getString(R.string.noname_eng);

            Toast.makeText(this, mes, Toast.LENGTH_LONG).show();
            return;

        }

        //Toast to remember filling signature
        if (!signView.isTouched()) {

            //setting language
            if (MainActivity.lang== "RU") mes = getResources().getString(R.string.nosign_rus);
            else mes = getResources().getString(R.string.nosign_eng);

            Toast.makeText(this, mes, Toast.LENGTH_LONG).show();
            return;
        }


        Log.w(TAG, "Starting SendTask");
        //creating AsyncTask to sent Send
        Send send = new Send(String.valueOf(getMacAddr().hashCode()), "1", nameView.getImage(nameWidth, nameHeight), signView.getImage(signWidth, signHeight));
        new SendTask().doInBackground(send);


        //result of checking access to internet
        if (!isOnline()) {

            //setting language
            if (MainActivity.lang == "RU") mes = getResources().getString(R.string.connection_error_rus);
            else mes = getResources().getString(R.string.connection_error_eng);

        }
        else {

            //setting language
            if (MainActivity.lang == "RU") mes = getResources().getString(R.string.send_ok_rus);
            else mes = getResources().getString(R.string.send_ok_eng);

        }
        Toast.makeText(this, mes, Toast.LENGTH_LONG).show();


        //back to MainActivity is delayed, because user have to look at info toast about result of sending
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


    //stackoverflow :D
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


    //just restarting the activity, but with param, that AlertDialog is disabled
    public void clearAll(View view) {
        Intent intent = new Intent(this, SignActivity.class);
        intent.putExtra("Info", "no");
        startActivity(intent);
    }

    //method to come back to MainActivity
    public void backPressed(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }
}
