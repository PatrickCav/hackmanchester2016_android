package com.hackmanchester2016.swearjar.ui.home;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.hackmanchester2016.swearjar.ui.challenges.ChallengesFragmentBase;
import com.hackmanchester2016.swearjar.ui.challenges.ChallengesFragmentFriends;
import com.hackmanchester2016.swearjar.ui.challenges.ChallengesFragmentMy;

/**
 * Created by patrickc on 29/10/2016
 */
public class HomePagerAdapter extends FragmentPagerAdapter {

    public enum Pages {
        CHALLENGES_MY,
        CHALLENGES_FRIENDS,
        COUNT
    }

    public HomePagerAdapter(FragmentManager fragmentManager){
        super(fragmentManager);
    }

    @Override
    public CharSequence getPageTitle(int position) {
        switch (Pages.values()[position]){
            case CHALLENGES_MY:
                return ChallengesFragmentMy.TITLE;
            case CHALLENGES_FRIENDS:
                return ChallengesFragmentFriends.TITLE;
            default:
                return null;
        }
    }

    @Override
    public Fragment getItem(int position) {
        switch (Pages.values()[position]){
            case CHALLENGES_MY:
                return new ChallengesFragmentMy();
            case CHALLENGES_FRIENDS:
                return new ChallengesFragmentFriends();
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return Pages.COUNT.ordinal();
    }
}
