package com.rahimian.app.starter;

import android.annotation.SuppressLint;
import android.app.ActivityOptions;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Pair;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.view.GestureDetectorCompat;

import com.parse.ParseUser;
import com.podcopic.animationlib.library.AnimationType;
import com.podcopic.animationlib.library.StartSmartAnimation;
import com.rahimian.app.Home;
import com.rahimian.app.R;
import com.rahimian.app.swipeInterface.SwipeActions;
import com.rahimian.app.swipeInterface.SwipeGestureDetector;

public class onboardings extends AppCompatActivity     {
    private ImageView logo , next , pre ;
    private TextView title , text ,skip;
    private Button btnIn;
    private int id = 0 , count=3;
    private String[] titles = new String[count];
    private String[] texts = new String[count];
    private Drawable[] logos = new Drawable[count];

    private GestureDetectorCompat gestureDetectorCompat;

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_onboardings);
        if (ParseUser.getCurrentUser()!=null){
            Intent intent = new Intent(this, Home.class);
            startActivity(intent);
            finish();
        }

/* datas */
    /*android*/
        logos[0]=getResources().getDrawable(R.drawable.logo);
        titles[0]= "Instagram";
        texts[0] ="Instagram (commonly abbreviated to IG or Insta)[10] is an American photo and video sharing social networking service owned by Facebook, created by Kevin Systrom and Mike Krieger and originally launched on iOS in October 2010.";
    /*git*/
        logos[1]=getResources().getDrawable(R.drawable.ob_post);
        titles[1]= "Post";
        texts[1]="You can Post inorder to share your time with your friends :)  ";
    /*php*/
        logos[2]=getResources().getDrawable(R.drawable.ob_protnro);
        titles[2]= "Profile";
        texts[2]="its time to create an account and complete it to start ";

//ui compenent
        logo =  findViewById(R.id.logo);
        next =  findViewById(R.id.next);
        pre =  findViewById(R.id.pre);
        title =  findViewById(R.id.title);
        text =  findViewById(R.id.text);
        skip =  findViewById(R.id.skip);
        btnIn = findViewById(R.id.btnIn) ;
        ConstraintLayout back = findViewById(R.id.back);
//set before
        btnIn.setTranslationY(1000);
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
                    if (id==2){ next.animate().translationX(-100).alpha(0f); btnIn.animate().translationY(0).alpha(1f); }
                    title.setText(titles[id]);
                    text.setText(texts[id]);
                    logo.setImageDrawable(logos[id]);
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
                    if(id<3){ next.animate().translationX(0).alpha(1f); btnIn.animate().translationY(1000).alpha(0f); }
                    if (id==0){ pre.animate().translationX(100).alpha(0f); skip.animate().translationX(0).alpha(1f); }
                    title.setText(titles[id]);
                    text.setText(texts[id]);
                    logo.setImageDrawable(logos[id]);
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
                btnIn.animate().translationY(0).alpha(1f);
                pre.animate().translationX(0).alpha(1f); skip.animate().translationX(-1000).alpha(0f);
                next.animate().translationX(-100).alpha(0f);
            }
        });

        btnIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(onboardings.this, Signin_signup.class);
                Pair[] pairs = new Pair[] {
                        new Pair(logo, "logo"),
                        new Pair(btnIn, "btn")
                };
                ActivityOptions activityOptions = ActivityOptions.makeSceneTransitionAnimation(onboardings.this, pairs);
                startActivity(intent,activityOptions.toBundle());
                finish();
            }
        });

        //swipe implementation
        SwipeGestureDetector swipeGestureDetector = new SwipeGestureDetector(new SwipeActions() {
            @Override
            public void onSwipeLeft() {
                next.callOnClick();
            }

            @Override
            public void onSwipeRight() {
                pre.callOnClick();
            }

            @Override
            public void onSwipeUp() {
            }

            @Override
            public void onSwipeDown() {
            }
        });
        gestureDetectorCompat = new GestureDetectorCompat(getApplicationContext(), swipeGestureDetector);
        back.setOnTouchListener(new View.OnTouchListener() {
            @SuppressLint("ClickableViewAccessibility")
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                gestureDetectorCompat.onTouchEvent(event);
                return true;
            }
        });



    }

    @Override
    public void onBackPressed() {
        logo.setVisibility(View.INVISIBLE);
        btnIn.setVisibility(View.INVISIBLE);
        super.onBackPressed();
    }
}