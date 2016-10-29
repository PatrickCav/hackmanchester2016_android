package com.hackmanchester2016.swearjar.ui.home;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

/**
 * Created by patrickc on 29/10/2016
 */
public class HomePagerAdapter extends FragmentPagerAdapter{

    public HomePagerAdapter(FragmentManager fragmentManager){
        super(fragmentManager);
    }

    @Override
    public CharSequence getPageTitle(int position) {
        switch (position){
            case 0:
                return "Stats";
            default:
                return null;
        }
    }

    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:
                return SwearingStatsFragment.newInstance();
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return 1;
    }
}
