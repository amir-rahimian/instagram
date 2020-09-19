package com.rahimian.app.settings;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.view.GestureDetectorCompat;

import com.irozon.sneaker.Sneaker;
import com.parse.LogOutCallback;
import com.parse.ParseException;
import com.parse.ParseUser;
import com.rahimian.app.R;
import com.rahimian.app.starter.Signin_signup;
import com.rahimian.app.swipeInterface.SwipeActions;
import com.rahimian.app.swipeInterface.SwipeGestureDetector;

public class Setting extends AppCompatActivity {

    private ConstraintLayout root;
    private GestureDetectorCompat gestureDetectorCompat;
    private ImageView btn_back;
    private LinearLayout logout, changePass;
    private TextView passOnOrOff;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sttring);

        root = findViewById(R.id.setttingroot);
        passOnOrOff = findViewById(R.id.passOnOrOff);
        getdata();
        logout = findViewById(R.id.logout);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Sneaker sneaker = Sneaker.with(Setting.this)
                        .autoHide(false);
                View view = LayoutInflater.from(Setting.this).inflate(R.layout.logout_dialog, sneaker.getView(), false);
                sneaker.sneakCustom(view);

            }
        });

        changePass = findViewById(R.id.changePass);
        changePass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Setting.this, PassChangeActivity.class);
                startActivity(intent);
            }
        });


        btn_back = findViewById(R.id.btn_back);
        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        //swipe implementation
        SwipeGestureDetector swipeGestureDetector = new SwipeGestureDetector(new SwipeActions() {
            @Override
            public void onSwipeLeft() {
            }

            @Override
            public void onSwipeRight() {
                onBackPressed();
            }

            @Override
            public void onSwipeUp() {
            }

            @Override
            public void onSwipeDown() {
            }
        });
        gestureDetectorCompat = new GestureDetectorCompat(getApplicationContext(), swipeGestureDetector);
        root.setOnTouchListener(new View.OnTouchListener() {
            @SuppressLint("ClickableViewAccessibility")
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                gestureDetectorCompat.onTouchEvent(event);
                return true;
            }
        });
    }

    private void getdata() {
        if (ParseUser.getCurrentUser().getBoolean("ispasschange")) {
            passOnOrOff.setText("ON");
        } else {
            passOnOrOff.setText("OFF");
        }
    }

    @Override
    protected void onResume() {
        getdata();
        super.onResume();
    }

    public void logout(View view) {
        //
        Sneaker sneaker = Sneaker.with(Setting.this);
        sneaker.autoHide(false);
        View vview = LayoutInflater.from(Setting.this).inflate(R.layout.waiting_dialog, sneaker.getView(), false);
        sneaker.sneakCustom(vview);
        //
        ParseUser.logOutInBackground(new LogOutCallback() {
            @Override
            public void done(ParseException e) {
                if (e == null) {
                    Intent intent = new Intent(Setting.this, Signin_signup.class);
                    startActivity(intent);
                    finish();
                } else {
                    e.printStackTrace();
                }
            }
        });
    }

    public void nobtnClick(View view) {
        Sneaker sneaker = Sneaker.with(Setting.this);
        sneaker.setDuration(1);
        View vview = LayoutInflater.from(Setting.this).inflate(R.layout.waiting_dialog, sneaker.getView(), false);
        sneaker.sneakCustom(vview);

    }
}