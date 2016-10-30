package com.hackmanchester2016.swearjar.ui.home;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.hackmanchester2016.swearjar.MainActivity;
import com.hackmanchester2016.swearjar.R;
import com.hackmanchester2016.swearjar.engine.Engine;

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

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);

        Engine.getInstance().getUserManager().updateCurrentUser();
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

        viewPager.setAdapter(new HomePagerAdapter(getChildFragmentManager()));
        tabLayout.setupWithViewPager(viewPager);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.home_menu, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.sign_out:
                ((MainActivity) getActivity()).logout();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
