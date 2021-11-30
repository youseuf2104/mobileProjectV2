package com.example.musicplayer;

import android.content.Context;
import android.content.Intent;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

public class LoginAdapter extends FragmentPagerAdapter {
    //context and total number of pages
    private Context context;
    int totalTabs;

    public LoginAdapter(FragmentManager fragmentManager, Context context, int totalTabs)
    {
        super(fragmentManager);
        this.context = context;
        this.totalTabs = totalTabs;
    }

    @Override
    public int getCount() {
        return totalTabs;
    }

    public Fragment getItem(int position)
    {
        switch (position) {
            //Case 1 is used as default, case 0 doesn't work even when tab clicked
            case 1:
                LoginTabFragment loginTabFragment  = new LoginTabFragment();
                return loginTabFragment;
            case 0:
                SignupTabFragment signupTabFragment = new SignupTabFragment();
                return signupTabFragment;

            default:
                return null;

        }
    }
}
