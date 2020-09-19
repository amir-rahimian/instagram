package com.rahimian.app.starter;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Pair;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.parse.ParseInstallation;
import com.rahimian.app.R;

public class MainActivity extends AppCompatActivity {
    private ImageView logo;
    private int Duration = 500;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // parse server
        ParseInstallation.getCurrentInstallation().saveInBackground();
        setTitle(" ");
        // UI
        logo = (ImageView) findViewById(R.id.logo) ;
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent;
                intent = new Intent(MainActivity.this,onboardings.class);
                ActivityOptions activityOptions = ActivityOptions.makeSceneTransitionAnimation(MainActivity.this, Pair.<View, String>create(logo,"logo"));
                startActivity(intent,activityOptions.toBundle());
                finish();
            }
        },Duration);

    }

}