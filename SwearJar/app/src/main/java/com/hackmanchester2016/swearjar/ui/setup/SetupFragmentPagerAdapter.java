package com.hackmanchester2016.swearjar.ui.setup;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

/**
 * Created by dant on 29/10/2016.
 */

public class SetupFragmentPagerAdapter extends FragmentPagerAdapter {

    public enum Pages {
        TEXT,
        VOICE,
        LOCATION,
        COUNT
    }

    public SetupFragmentPagerAdapter(FragmentManager fragmentManager) {
        super(fragmentManager);
    }

    @Override
    public Fragment getItem(int position) {
        switch (Pages.values()[position]) {
            case TEXT:
                return new SetupFragmentText();
            case VOICE:
                return new SetupFragmentVoice();
            case LOCATION:
                return new SetupFragmentLocation();
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return Pages.COUNT.ordinal();
    }
}
