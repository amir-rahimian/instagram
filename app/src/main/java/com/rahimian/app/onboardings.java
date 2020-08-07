package com.rahimian.app;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

public class onboardings extends AppCompatActivity {
    private ImageView logo , next , pre ;
    private TextView title , text ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_onboardings);
        //ui compenent

        logo = (ImageView) findViewById(R.id.logo);
        next = (ImageView) findViewById(R.id.next);
        pre = (ImageView) findViewById(R.id.pre);
        title = (TextView) findViewById(R.id.title);
        text = (TextView) findViewById(R.id.text);

        title.setAlpha(0f);
        text.setAlpha(0f);
        next.setAlpha(0f);
        pre.setAlpha(0f);

        title.setTranslationY(300);
        text.setTranslationY(500);
        next.setTranslationX(-100);
        pre.setTranslationX(100);


        title.animate().translationY(0).alpha(1f).setDuration(2000);
        text.animate().translationY(0).alpha(1f).setStartDelay(100).setDuration(2000);;
        next.animate().translationX(0).alpha(1f).setStartDelay(200);
//        pre.animate().translationX(0).alpha(1f).setStartDelay(200);

    }
}