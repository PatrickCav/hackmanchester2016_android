package com.hackmanchester2016.swearjar.ui.challenges;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import com.hackmanchester2016.swearjar.R;

/**
 * Created by dant on 30/10/2016.
 */

public class ChallengesFragmentAdd extends Fragment {

    public static String TAG = "CHALLENGES_FRAGMENT_ADD";

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_challenges_add, container, false);

        return view;
    }

    @Override
    public Animation onCreateAnimation(int transit, boolean enter, int nextAnim) {
        if(enter) {
            return AnimationUtils.loadAnimation(getContext(), R.anim.slide_in_from_bottom);
        } else {
            return AnimationUtils.loadAnimation(getContext(), R.anim.slide_out_to_bottom);
        }
    }
}
