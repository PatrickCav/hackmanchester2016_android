package com.hackmanchester2016.swearjar.ui.setup;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hackmanchester2016.swearjar.R;
import com.hackmanchester2016.swearjar.ui.components.MaterialPageIndicator;

/**
 * Created by dant on 29/10/2016.
 */

public class SetupFragment extends Fragment implements ViewPager.OnPageChangeListener, ViewPager.PageTransformer {

    public static String TAG = "SETUP_FRAGMENT";

    private ViewPager viewPager;
    private MaterialPageIndicator viewPagerIndicator;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_setup, container, false);

        viewPager = (ViewPager) view.findViewById(R.id.view_pager);
        viewPager.setAdapter(new SetupFragmentPagerAdapter(getChildFragmentManager()));
        viewPager.addOnPageChangeListener(this);
        viewPager.setPageTransformer(true, this);

        viewPagerIndicator = (MaterialPageIndicator) view.findViewById(R.id.view_pager_indicator);
        viewPagerIndicator.setViewPager(viewPager);

        return view;
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

    // ViewPager.PageTransformer

    @Override
    public void transformPage(View page, float position) {
        int pageWidth = page.getWidth();

        if (position < -1) {
            page.setAlpha(1);
        } else if (position <= 1) {
            page.findViewById(R.id.background).setTranslationX(-position * (pageWidth / 2)); //Half the normal speed
        } else {
            page.setAlpha(1);
        }
    }
}
