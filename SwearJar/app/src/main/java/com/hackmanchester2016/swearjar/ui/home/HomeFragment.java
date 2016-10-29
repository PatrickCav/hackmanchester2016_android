package com.hackmanchester2016.swearjar.ui.home;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hackmanchester2016.swearjar.R;

/**
 * Created by patrickc on 29/10/2016
 */
public class HomeFragment extends Fragment {

    public static final String TAG = "HomeFragment";

    private ViewPager viewPager;
    private TabLayout tabLayout;

    public static HomeFragment newInstance(){
        return new HomeFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        viewPager = (ViewPager) view.findViewById(R.id.home_view_pager);
        tabLayout = (TabLayout) view.findViewById(R.id.tab_bar);

        viewPager.setAdapter(new HomePagerAdapter(getFragmentManager()));
        tabLayout.setupWithViewPager(viewPager);
    }

}
