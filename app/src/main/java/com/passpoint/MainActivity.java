package com.passpoint;

import android.Manifest;
import android.app.DownloadManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.ColorDrawable;
import android.graphics.pdf.PdfRenderer;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Environment;
import android.os.ParcelFileDescriptor;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.passpoint.passpoint.R;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;


public class MainActivity extends AppCompatActivity {

    private static String TAG = "MainLog";
    private static String FileName = "passpoint.pdf";
    private static PdfRenderer pdfRenderer;
    private static String url = "https://firebasestorage.googleapis.com/v0/b/passpointandroid.appspot.com/o/doc.pdf?alt=media&token=71fba9a9-0e4d-44f0-a28b-7120a9bbd1aa";
    private static String path;
    private static String lang = "RU";


    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        getSupportActionBar().hide();

        ActionBar actionBar = getSupportActionBar();
        actionBar.setLogo(R.mipmap.croc_logo);
        actionBar.setDisplayUseLogoEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setTitle("");
        actionBar.setBackgroundDrawable(new ColorDrawable(0xFF0000));



//        if ((ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED)) {
//            Log.w(TAG, "Not granted");
//            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.ACCESS_NETWORK_STATE, Manifest.permission.INTERNET}, 1);
//        }
//        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
//            finish();
//        }
//
//        Log.w(TAG, "Access granted");
//
//        //getting path to our document
//        path = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).getAbsolutePath() + "/" + FileName;
//        Log.w(TAG, "Absolute path to file: " + path);
//
//        try {
//
//            File pdffile = new File(path);
//            pdfRenderer = new PdfRenderer(ParcelFileDescriptor.open(pdffile, ParcelFileDescriptor.MODE_READ_ONLY));
//
//        } catch (FileNotFoundException e) {
//            Log.w(TAG, "File doesn't exists");
//
//            if (isOnline()) Toast.makeText(this, "Документ отсутствует.\nПожалуйста подождите. Когда он будет скачен, перезапустите приложение", Toast.LENGTH_LONG).show();
//            else Toast.makeText(this, "Документ отсутствует.\nБез доступа к сети скачать документ невозможно", Toast.LENGTH_LONG).show();
//
//            getDoc();
//
//            try {
//                //new try to get document
//                File pdffile = new File(path);
//                pdfRenderer = new PdfRenderer(ParcelFileDescriptor.open(pdffile, ParcelFileDescriptor.MODE_READ_ONLY));
//            } catch (FileNotFoundException error) {
//                Log.w(TAG, "File not found the second time");
//            } catch (IOException error) {
//                Log.w(TAG, e.getMessage());
//            }
//        } catch (IOException e) {
//            Log.w(TAG, e.getMessage());
//        }
//
//        try {
//            Bitmap bitmap = Bitmap.createBitmap(this.getResources().getDisplayMetrics().widthPixels + 10, this.getResources().getDisplayMetrics().heightPixels + 10, Bitmap.Config.ARGB_8888);
//
//            pdfRenderer.openPage(0).render(bitmap, null, null, PdfRenderer.Page.RENDER_MODE_FOR_DISPLAY);
//
//            Log.w(TAG, "setting bitmap");
//            ImageView imageView = findViewById(R.id.pdf_view);
//            imageView.setImageBitmap(bitmap);
//        } catch (Exception e) {
//            Log.e(TAG, e.getMessage());
//        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar_button, menu);
        return super.onCreateOptionsMenu(menu);
    }

    // handle button activities
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.mybutton) {
            if (lang == "RU") {
                TextView textView = (TextView) findViewById(R.id.rules_view);
                textView.setText(getResources().getString(R.string.rules_eng));
                Button button = (Button) findViewById(R.id.sign_activity_button);
                button.setText(getResources().getString(R.string.next_button_eng));
                item.setIcon(R.drawable.eng_button);
                lang = "ENG";
            } else {
                TextView textView = (TextView) findViewById(R.id.rules_view);
                textView.setText(getResources().getString(R.string.rules_rus));
                Button button = (Button) findViewById(R.id.sign_activity_button);
                button.setText(getResources().getString(R.string.next_button_rus));
                item.setIcon(R.drawable.ru_button);
                lang = "RU";
            }
            ScrollView scrollView = (ScrollView) findViewById(R.id.scrollview);
            scrollView.scrollTo(0, 0);
        }
        return super.onOptionsItemSelected(item);
    }

    public void sign(View view) {
        Log.w(TAG, "next_button has been pressed");

        Intent intent = new Intent(this, SignActivity.class);
        startActivity(intent);
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
}


