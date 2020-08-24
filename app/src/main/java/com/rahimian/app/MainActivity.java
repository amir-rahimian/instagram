package com.rahimian.app;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Pair;
import android.view.View;
import android.widget.ImageView;

import com.parse.ParseInstallation;

public class MainActivity extends AppCompatActivity {
    private ImageView logo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // parse server
        ParseInstallation.getCurrentInstallation().saveInBackground();
        // UI
        logo = (ImageView) findViewById(R.id.logo) ;
        logo.setAlpha(0f);
        logo.animate().alpha(1f).setDuration(2000);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent;
                intent = new Intent(MainActivity.this,onboardings.class);
                ActivityOptions activityOptions = ActivityOptions.makeSceneTransitionAnimation(MainActivity.this, Pair.<View, String>create(logo,"logo"));
                startActivity(intent,activityOptions.toBundle());
                finish();
            }
        },3000);

    }
}