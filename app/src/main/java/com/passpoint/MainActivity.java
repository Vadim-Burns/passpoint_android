package com.passpoint;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;

import com.passpoint.passpoint.R;

import java.lang.reflect.Type;


public class MainActivity extends AppCompatActivity {

    private static String TAG = "MainLog";
    protected static String lang = "RU";
    protected static Typeface boldFont;
    protected static Typeface smallFont;
    //Ru/ENG


    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);

        View decorView = getWindow().getDecorView();
        int uiOptions = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY;
        decorView.setSystemUiVisibility(uiOptions);

        boldFont = Typeface.createFromAsset(getAssets(), "font/CirceBold.ttf");
        smallFont = Typeface.createFromAsset(getAssets(), "font/CirceLight.ttf");


        //customize action bar
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
//        actionBar.setLogo(R.drawable.ic_action_croc);
//        actionBar.setDisplayUseLogoEnabled(true);
//        actionBar.setDisplayShowHomeEnabled(true);
//        actionBar.setTitle("");
//        actionBar.setBackgroundDrawable(new ColorDrawable(0xFF0000));

        TextView rules_title = (TextView) findViewById(R.id.rules_title);
        rules_title.setTypeface(boldFont);
        TextView rules = (TextView) findViewById(R.id.rules_view);
        rules.setTypeface(smallFont);
        Button button = (Button) findViewById(R.id.sign_activity_button);
        button.setTypeface(boldFont);
        ImageView lang_button = (ImageView) findViewById(R.id.lang_button);

        if (lang == "RU") {
            lang_button.setImageResource(R.drawable.lang_ru);
            rules_title.setText(getResources().getText(R.string.rules_title_rus));
            rules.setText(getResources().getText(R.string.rules_rus));
            button.setText(getResources().getString(R.string.next_button_rus));
        } else {
            lang_button.setImageResource(R.drawable.lang_eng);
            rules_title.setText(getResources().getString(R.string.rules_title_eng));
            rules.setText(getResources().getText(R.string.rules_eng));
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
        intent.putExtra("Info", "yes");
        startActivity(intent);
    }

    public void changeLang(View view) {

        ImageView lang_button = (ImageView) findViewById(R.id.lang_button);

        if (lang == "RU") {
            TextView rules_textview = (TextView) findViewById(R.id.rules_view);
            rules_textview.setText(getResources().getText(R.string.rules_eng));

            TextView rules_title_textview = (TextView) findViewById(R.id.rules_title);
            rules_title_textview.setText(getResources().getString(R.string.rules_title_eng));

            Button button = (Button) findViewById(R.id.sign_activity_button);
            button.setText(getResources().getString(R.string.next_button_eng));

            lang_button.setImageResource(R.drawable.lang_eng);

            lang = "ENG";
        } else {
            TextView rules_textview = (TextView) findViewById(R.id.rules_view);
            rules_textview.setText(getResources().getText(R.string.rules_rus));

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


