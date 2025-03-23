package com.ruksana.view;

import android.os.Bundle;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.widget.TextView;

import com.ruksana.jobprostuti.R;

public class NoticeDetailsActivity extends AppCompatActivity {
    private TextView tv;

    String text,name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notice_details);

        //int action bar
        ActionBar actionBar = getSupportActionBar();
        assert actionBar != null;


        // Get data from Intent
        name = getIntent().getStringExtra("name");
        text = getIntent().getStringExtra("data");

        //add back button
        assert actionBar != null;
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle(name);

        tv = findViewById(R.id.tvId);

        tv.setText(text);


    }
    // onCreate End

    //Back Override method
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();//go previous activity, when back button of  actionbar clicked
        return super.onSupportNavigateUp();
    }
}