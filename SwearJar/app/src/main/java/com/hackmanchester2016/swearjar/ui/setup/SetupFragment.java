package com.hackmanchester2016.swearjar.ui.setup;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hackmanchester2016.swearjar.R;

/**
 * Created by dant on 29/10/2016.
 */

public class SetupFragment extends Fragment implements ViewPager.OnPageChangeListener {

    private ViewPager viewPager;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_setup, container, false);

        viewPager = (ViewPager) view.findViewById(R.id.view_pager);
        viewPager.setAdapter(new SetupFragmentPagerAdapter(getChildFragmentManager()));
        viewPager.addOnPageChangeListener(this);

        return super.onCreateView(inflater, container, savedInstanceState);
    }

    // ViewPager.OnPageChangeListener

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {

    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }
}
