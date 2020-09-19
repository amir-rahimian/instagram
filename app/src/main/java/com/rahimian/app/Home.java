package com.rahimian.app;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.PorterDuff;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;
import com.rahimian.app.fregments.FregmentAdapter;
import com.rahimian.app.fregments.posts.MakePost;

import gun0912.tedbottompicker.TedBottomPicker;
import gun0912.tedbottompicker.TedBottomSheetDialogFragment;

public class Home extends AppCompatActivity {

    private MaterialToolbar toolbar;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private FregmentAdapter addapter;
    private FloatingActionButton btnaddpost;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        toolbar = findViewById(R.id.addpost_toolbar);
        setTitle("");
        setSupportActionBar(toolbar);
        // ui
        tabLayout = findViewById(R.id.home_tablayout);
        viewPager = findViewById(R.id.home_viewpager);

        btnaddpost = findViewById(R.id.addpost);
        btnaddpost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Build.VERSION.SDK_INT >= 23 && ActivityCompat.checkSelfPermission(Home.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(Home.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1000);
                } else {
                    TedBottomPicker.with(Home.this)
                            .setPreviewMaxCount(85)
                            .setCameraTileBackgroundResId(R.color.colorPrimaryDark)
                            .setGalleryTileBackgroundResId(R.color.colorPrimaryDark)
                            .showTitle(false)
                            .setTitleBackgroundResId(R.color.colorPrimary)
                            .show(new TedBottomSheetDialogFragment.OnImageSelectedListener() {
                                @Override
                                public void onImageSelected(final Uri uri) {

                                            Intent intent = new Intent(Home.this, MakePost.class);
                                                intent.putExtra("uri",uri);

                                            startActivity(intent);

                                }
                            });

                }
            }
        });

        addapter = new FregmentAdapter(getSupportFragmentManager(), 0);
        viewPager.setAdapter(addapter);
        viewPager.setCurrentItem(1);
        tabLayout.setupWithViewPager(viewPager, true);

        tabLayout.getTabAt(1).setIcon(R.drawable.ic_post);
        tabLayout.getTabAt(1).getIcon().setColorFilter(getResources().getColor(R.color.colorAccent), PorterDuff.Mode.MULTIPLY);
        tabLayout.getTabAt(0).setIcon(R.drawable.ic_profile);
        tabLayout.getTabAt(0).setTabLabelVisibility(TabLayout.TAB_LABEL_VISIBILITY_UNLABELED);
        tabLayout.getTabAt(2).setIcon(R.drawable.ic_users);
        tabLayout.getTabAt(2).setTabLabelVisibility(TabLayout.TAB_LABEL_VISIBILITY_UNLABELED);
        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                tab.setTabLabelVisibility(TabLayout.TAB_LABEL_VISIBILITY_LABELED);
                if (tab.getPosition() == 1) {
                    btnaddpost.animate().translationY(0);
                } else {
                    btnaddpost.animate().translationY(2000);
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                tab.setTabLabelVisibility(TabLayout.TAB_LABEL_VISIBILITY_UNLABELED);


            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {


            }
        });


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (resultCode == RESULT_OK) {
            btnaddpost.callOnClick();
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}
