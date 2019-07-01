package com.passpoint;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;

import com.passpoint.passpoint.R;


public class MainActivity extends AppCompatActivity {

    private static String TAG = "MainLog";
    protected static String lang = "RU";


    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        //customize action bar
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
//        actionBar.setLogo(R.drawable.ic_action_croc);
//        actionBar.setDisplayUseLogoEnabled(true);
//        actionBar.setDisplayShowHomeEnabled(true);
//        actionBar.setTitle("");
//        actionBar.setBackgroundDrawable(new ColorDrawable(0xFF0000));

        TextView rules_title = (TextView) findViewById(R.id.rules_title);
        TextView rules = (TextView) findViewById(R.id.rules_view);
        Button button = (Button) findViewById(R.id.sign_activity_button);
        ImageView lang_button = (ImageView) findViewById(R.id.lang_button);

        if (lang == "RU") {
            lang_button.setImageResource(R.drawable.lang_ru);
            rules_title.setText(getResources().getString(R.string.rules_title_rus));
            rules.setText(getResources().getString(R.string.rules_rus));
            button.setText(getResources().getString(R.string.next_button_rus));
        } else {
            lang_button.setImageResource(R.drawable.lang_eng);
            rules_title.setText(getResources().getString(R.string.rules_title_eng));
            rules.setText(getResources().getString(R.string.rules_eng));
            button.setText(getResources().getString(R.string.next_button_eng));
        }


        if ((ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED)) {
            Log.w(TAG, "Not granted");
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_NETWORK_STATE, Manifest.permission.INTERNET, Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
        }
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_NETWORK_STATE) != PackageManager.PERMISSION_GRANTED) {
            Log.e(TAG, "Finishing because access not granted");
            finish();
        }
    }


    public void sign(View view) {
        Log.w(TAG, "next_button has been pressed");

        Intent intent = new Intent(this, SignActivity.class);
        startActivity(intent);
    }

    public void changeLang(View view) {

        ImageView lang_button = (ImageView) findViewById(R.id.lang_button);

        if (lang == "RU") {
            TextView rules_textview = (TextView) findViewById(R.id.rules_view);
            rules_textview.setText(getResources().getString(R.string.rules_eng));

            TextView rules_title_textview = (TextView) findViewById(R.id.rules_title);
            rules_title_textview.setText(getResources().getString(R.string.rules_title_eng));

            Button button = (Button) findViewById(R.id.sign_activity_button);
            button.setText(getResources().getString(R.string.next_button_eng));

            lang_button.setImageResource(R.drawable.lang_eng);

            lang = "ENG";
        } else {
            TextView rules_textview = (TextView) findViewById(R.id.rules_view);
            rules_textview.setText(getResources().getString(R.string.rules_rus));

            TextView rules_title_textview = (TextView) findViewById(R.id.rules_title);
            rules_title_textview.setText(getResources().getString(R.string.rules_title_rus));

            Button button = (Button) findViewById(R.id.sign_activity_button);
            button.setText(getResources().getString(R.string.next_button_rus));

            lang_button.setImageResource(R.drawable.lang_ru);

            lang = "RU";
        }
        ScrollView scrollView = (ScrollView) findViewById(R.id.scrollview);
        scrollView.scrollTo(0, 0);
    }
}


