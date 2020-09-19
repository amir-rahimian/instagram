package com.rahimian.app.fregments;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.rahimian.app.fregments.posts.Posts_fragment;
import com.rahimian.app.fregments.profile.Profile_fragment;
import com.rahimian.app.fregments.users.Users_fragment;

public class FregmentAdapter extends FragmentPagerAdapter {
    public FregmentAdapter(@NonNull FragmentManager fm, int behavior) {
        super(fm, behavior);
    }


    @NonNull
    @Override
    public Fragment getItem(int position) {

        switch (position){
            case 0:
                return new Profile_fragment();
            case 1:
                return new Posts_fragment();
            case 2:
                return new Users_fragment();
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return 3;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        switch (position){
            case 0:
                return  "Profile";
            case 1:
                return  "Posts";
            case 2:
                return  "Users";
            default:
                return null;
        }
    }
}
