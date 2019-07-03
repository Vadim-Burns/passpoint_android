package com.passpoint;

import android.Manifest;
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


public class MainActivity extends AppCompatActivity {

    private static String TAG = "MainLog";
    protected static String lang = "RU"; // RU/ENG
    protected static Typeface boldFont;
    protected static Typeface smallFont;


    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);

        //hidding title
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);

        //hidding movebar
        View decorView = getWindow().getDecorView();
        int uiOptions = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY;
        decorView.setSystemUiVisibility(uiOptions);

        //getting fonts
        boldFont = Typeface.createFromAsset(getAssets(), "font/CirceBold.ttf");
        smallFont = Typeface.createFromAsset(getAssets(), "font/CirceLight.ttf");


        //hidding actionbar
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        //getting parts of the layout, where there is a text
        TextView rules_title = (TextView) findViewById(R.id.rules_title);
        rules_title.setTypeface(boldFont);
        TextView rules = (TextView) findViewById(R.id.rules_view);
        rules.setTypeface(smallFont);
        Button button = (Button) findViewById(R.id.sign_activity_button);
        button.setTypeface(boldFont);
        ImageView lang_button = (ImageView) findViewById(R.id.lang_button);


        //setting language
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


        //getting permissions
        //WRITE_EXTERNAL_STORAGE is used to save pictures of name and signature
        if ((ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED)) {
            Log.w(TAG, "Not granted");
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_NETWORK_STATE, Manifest.permission.INTERNET, Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
        }
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_NETWORK_STATE) != PackageManager.PERMISSION_GRANTED) {
            Log.e(TAG, "Finishing because access not granted");
            finish();
        }
    }


    //going to SignActivity
    public void sign(View view) {
        Log.w(TAG, "next_button has been pressed");

        Intent intent = new Intent(this, SignActivity.class);
        intent.putExtra("Info", "yes");
        startActivity(intent);
    }


    public void changeLang(View view) {

        ImageView lang_button = (ImageView) findViewById(R.id.lang_button);
        TextView rules_textview = (TextView) findViewById(R.id.rules_view);
        TextView rules_title_textview = (TextView) findViewById(R.id.rules_title);
        Button button = (Button) findViewById(R.id.sign_activity_button);

        if (lang == "RU") {

            //setting text
            rules_textview.setText(getResources().getText(R.string.rules_eng));
            rules_title_textview.setText(getResources().getString(R.string.rules_title_eng));
            button.setText(getResources().getString(R.string.next_button_eng));
            lang_button.setImageResource(R.drawable.lang_eng);

            lang = "ENG";
        } else {

            //setting text
            rules_textview.setText(getResources().getText(R.string.rules_rus));
            rules_title_textview.setText(getResources().getString(R.string.rules_title_rus));
            button.setText(getResources().getString(R.string.next_button_rus));
            lang_button.setImageResource(R.drawable.lang_ru);

            lang = "RU";
        }

        //scrolling text to start
        ScrollView scrollView = (ScrollView) findViewById(R.id.scrollview);
        scrollView.scrollTo(0, 0);
    }
}


