package com.ruksana.jobprostuti;

import android.graphics.Camera;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.Transformation;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.github.barteksc.pdfviewer.PDFView;
import com.github.barteksc.pdfviewer.util.FitPolicy;
import com.google.firebase.FirebaseApp;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.File;


public class DetailsForPracticeActivity extends AppCompatActivity {


    String data, name;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details_for_practice);


        //int action bar
        ActionBar actionBar = getSupportActionBar();
        assert actionBar != null;


        //add back button
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);

        FirebaseApp.initializeApp(this);


        data = getIntent().getStringExtra("data");
        name = getIntent().getStringExtra("name");


        actionBar.setTitle(name);

        // Find the WebView
        WebView webView = findViewById(R.id.detailsWebViewId);

        // Enable JavaScript
        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);

        // Enable loading local assets (optional)
        webSettings.setAllowFileAccess(true);



        // Load HTML with <head> and <body> using loadDataWithBaseURL
        webView.loadDataWithBaseURL(null, data, "text/html", "UTF-8", null);

        // Use a WebViewClient to handle navigation within the WebView
        webView.setWebViewClient(new WebViewClient());


    }//End On create


    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();//go previous activity, when back button of  actionbar clicked
        return super.onSupportNavigateUp();
    }


}

