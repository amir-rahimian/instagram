package com.rahimian.app;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.tabs.TabLayout;
import com.irozon.sneaker.Sneaker;
import com.parse.LogOutCallback;
import com.parse.ParseException;
import com.parse.ParseUser;

public class Home extends AppCompatActivity {

    private MaterialToolbar toolbar;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private Adapter addapter;
    private boolean closeable;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        toolbar = findViewById(R.id.home_toolbar);
        setTitle("");
        setSupportActionBar(toolbar);
        closeable=false;
        // ui
        tabLayout = findViewById(R.id.home_tablayout);
        viewPager = findViewById(R.id.home_viewpager);

        addapter = new Adapter(getSupportFragmentManager(), 0);
        viewPager.setAdapter(addapter);
        tabLayout.setupWithViewPager(viewPager, true);

        tabLayout.getTabAt(0).setIcon(R.drawable.ic_profile);
        tabLayout.getTabAt(0).getIcon().setColorFilter(getResources().getColor(R.color.colorAccent), PorterDuff.Mode.MULTIPLY);
        tabLayout.getTabAt(1).setIcon(R.drawable.ic_post);
        tabLayout.getTabAt(1).setTabLabelVisibility(TabLayout.TAB_LABEL_VISIBILITY_UNLABELED);
        tabLayout.getTabAt(2).setIcon(R.drawable.ic_users);
        tabLayout.getTabAt(2).setTabLabelVisibility(TabLayout.TAB_LABEL_VISIBILITY_UNLABELED);
        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                tab.setTabLabelVisibility(TabLayout.TAB_LABEL_VISIBILITY_LABELED);
                tab.getIcon().setColorFilter(getResources().getColor(R.color.colorAccent), PorterDuff.Mode.MULTIPLY);
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                tab.setTabLabelVisibility(TabLayout.TAB_LABEL_VISIBILITY_UNLABELED);
                tab.getIcon().setColorFilter(getResources().getColor(R.color.colorAccent), PorterDuff.Mode.MULTIPLY);
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {


            }
        });


    }

    public void logout(View view){
        //
        Sneaker sneaker = Sneaker.with(Home.this);sneaker.autoHide(false);
        View vview = LayoutInflater.from(Home.this).inflate(R.layout.waiting_dialog,  sneaker.getView(), false);
        sneaker.sneakCustom(vview);
        //
        ParseUser.logOutInBackground(new LogOutCallback() {
            @Override
            public void done(ParseException e) {
                if (e==null){
                    Intent intent = new Intent(Home.this,Signin_signup.class);
                    startActivity(intent);
                    finish();
                }else {
                    e.printStackTrace();
                }
            }
        });
    }
    public void nobtnClick(View view){
        Sneaker sneaker = Sneaker.with(Home.this);sneaker.setDuration(1);
        View vview = LayoutInflater.from(Home.this).inflate(R.layout.waiting_dialog,  sneaker.getView(), false);
        sneaker.sneakCustom(vview);

    }
}
