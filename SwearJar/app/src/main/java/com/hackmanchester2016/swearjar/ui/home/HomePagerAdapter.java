package com.hackmanchester2016.swearjar.ui.home;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.hackmanchester2016.swearjar.ui.challenges.ChallengesFragment;

/**
 * Created by patrickc on 29/10/2016
 */
public class HomePagerAdapter extends FragmentPagerAdapter {

    public enum Pages {
//        STATS,
        CHALLENGES,
        COUNT
    }

    public HomePagerAdapter(FragmentManager fragmentManager){
        super(fragmentManager);
    }

    @Override
    public CharSequence getPageTitle(int position) {
        switch (Pages.values()[position]){
//            case STATS:
//                return "Stats";
            case CHALLENGES:
                return "Challenges";
            default:
                return null;
        }
    }

    @Override
    public Fragment getItem(int position) {
        switch (Pages.values()[position]){
//            case STATS:
//                return SwearingStatsFragment.newInstance();
            case CHALLENGES:
                return new ChallengesFragment();
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return Pages.COUNT.ordinal();
    }
}
