package com.rahimian.app;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.view.GestureDetectorCompat;

import android.annotation.SuppressLint;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.podcopic.animationlib.library.AnimationType;
import com.podcopic.animationlib.library.StartSmartAnimation;

import java.util.HashMap;

public class onboardings extends AppCompatActivity     {
    private ImageView logo , next , pre ;
    private TextView title , text ,skip;
    private Button btnIn;
    private ConstraintLayout back;
    private int id = 0 , count=3;
    private String[] titles = new String[count];
    private String[] texts = new String[count];
    private Drawable[] logos = new Drawable[count];

    private SwipeGestureDetector swipeGestureDetector;
    private GestureDetectorCompat gestureDetectorCompat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_onboardings);


/* datas */
    /*android*/
        logos[0]=getResources().getDrawable(R.drawable.ic_baseline_android_24);
        titles[0]= "Android";
        texts[0] ="Android is a mobile operating system based on a modified version of the Linux kernel and other open source software, designed primarily for touchscreen mobile devices such as smartphones and tablets. Android is developed by a consortium of developers known as the Open Handset Alliance and commercially sponsored by Google.";
    /*git*/
        logos[1]=getResources().getDrawable(R.drawable.ic_git);
        titles[1]= "Git";
        texts[1]="Git is a distributed version-control system for tracking changes in source code during software development.[8] It is designed for coordinating work among programmers, but it can be used to track changes in any set of files. Its goals include speed, data integrity, and support for distributed, non-linear workflow";
    /*php*/
        logos[2]=getResources().getDrawable(R.drawable.ic_php);
        titles[2]= "PHP";
        texts[2]="PHP is a general-purpose scripting language that is especially suited to web development.[6] It was originally created by Danish-Canadian programmer Rasmus Lerdorf in 1994;[7] the PHP reference implementation is now produced by The PHP Group.[8] PHP originally stood for Personal Home Page,[7] but it now stands for the recursive initialise PHP: Hypertext Preprocessor.[9]";

//ui compenent
        logo = (ImageView) findViewById(R.id.logo);
        next = (ImageView) findViewById(R.id.next);
        pre = (ImageView) findViewById(R.id.pre);
        title = (TextView) findViewById(R.id.title);
        text = (TextView) findViewById(R.id.text);
        skip = (TextView) findViewById(R.id.skip);
        btnIn =(Button) findViewById(R.id.btnIn) ;
        back = findViewById(R.id.back);
//set before
        btnIn.setTranslationX(100);
        btnIn.setAlpha(0f);
        title.setAlpha(0f);
        text.setAlpha(0f);
        next.setAlpha(0f);
        pre.setAlpha(0f);
        title.setTranslationY(300);
        text.setTranslationY(500);
        next.setTranslationX(-100);
        pre.setTranslationX(100);
        title.setText(titles[id]);
        text.setText(texts[id]);
        logo.setImageDrawable(logos[id]);
// animate
        title.animate().translationY(0).alpha(1f).setDuration(1000);
        text.animate().translationY(0).alpha(1f).setStartDelay(100).setDuration(1000);
        next.animate().translationX(0).alpha(1f).setStartDelay(100);

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(id<=1) {
                    id++;
                    StartSmartAnimation.startAnimation( logo , AnimationType.FadeIn , 1000 , 0 , false  );
                    StartSmartAnimation.startAnimation( title , AnimationType.FadeIn , 1000 , 0 , false  );
                    StartSmartAnimation.startAnimation( text , AnimationType.FadeIn , 1000 , 0 , false  );
                    if(id>0){ pre.animate().translationX(0).alpha(1f); skip.animate().translationX(-1000).alpha(0f); }
                    if (id==2){ next.animate().translationX(-100).alpha(0f); btnIn.animate().translationX(0).alpha(1f); }
                    title.setText(titles[id]);
                    text.setText(texts[id]);
                    logo.setImageDrawable(logos[id]);
                }else{
                    btnIn.callOnClick();
                }
            }
        });
        pre.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(id>0) {
                    id--;
                    StartSmartAnimation.startAnimation( logo , AnimationType.FadeIn , 1000 , 0 , false );
                    StartSmartAnimation.startAnimation( title , AnimationType.FadeIn , 1000 , 0 , false  );
                    StartSmartAnimation.startAnimation( text , AnimationType.FadeIn , 1000 , 0 , false  );
                    if(id<3){ next.animate().translationX(0).alpha(1f); btnIn.animate().translationX(100).alpha(0f); }
                    if (id==0){ pre.animate().translationX(100).alpha(0f); skip.animate().translationX(0).alpha(1f); }
                    title.setText(titles[id]);
                    text.setText(texts[id]);
                    logo.setImageDrawable(logos[id]);
                }else{
                    Toast.makeText(onboardings.this,"hoom??",Toast.LENGTH_SHORT).show();
                }
            }
        });
        skip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              id=2;
                StartSmartAnimation.startAnimation( logo , AnimationType.FadeIn , 1000 , 0 , false );
                StartSmartAnimation.startAnimation( title , AnimationType.FadeIn , 1000 , 0 , false  );
                StartSmartAnimation.startAnimation( text , AnimationType.FadeIn , 1000 , 0 , false  );
                title.setText(titles[id]);
                text.setText(texts[id]);
                logo.setImageDrawable(logos[id]);
                btnIn.animate().translationX(0).alpha(1f);
                pre.animate().translationX(0).alpha(1f); skip.animate().translationX(-1000).alpha(0f);
                next.animate().translationX(-100).alpha(0f);
            }
        });

        btnIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(onboardings.this,"SIGN",Toast.LENGTH_SHORT).show();

            }
        });


        swipeGestureDetector=new SwipeGestureDetector(new SwipeActions() {
            @Override
            public void onSwipeLeft() {
                //Write The actions need to be performed when Swiped Left here
                next.callOnClick();
            }

            @Override
            public void onSwipeRight() {
                //Write The actions need to be performed when Swiped Right here
                pre.callOnClick();
            }

            @Override
            public void onSwipeUp() {
                //Write The actions need to be performed when Swiped up here
            }

            @Override
            public void onSwipeDown() {
                //Write The actions need to be performed when Swiped down here
            }
        });


        //Assign GestureDetectorCompat instance by passing swipeGestureDetector instance
        gestureDetectorCompat = new GestureDetectorCompat(getApplicationContext(), swipeGestureDetector);

        //Set onTouchListener on rootLayout
        back.setOnTouchListener(new View.OnTouchListener() {
            @SuppressLint("ClickableViewAccessibility")
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                gestureDetectorCompat.onTouchEvent(event);
                //Make sure it returns true because event needs to be consumed in the end
                return true;
            }
        });



    }


}