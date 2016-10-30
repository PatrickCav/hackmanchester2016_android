package com.hackmanchester2016.swearjar.ui.challenges;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.hackmanchester2016.swearjar.R;
import com.hackmanchester2016.swearjar.engine.Engine;
import com.hackmanchester2016.swearjar.engine.comms.models.User;

import java.util.ArrayList;

/**
 * Created by dant on 30/10/2016.
 */

public class ChallengesFragmentAdd extends Fragment {

    public static String TAG = "CHALLENGES_FRAGMENT_ADD";

    private View closeButton;
    private View formContainer;
    private Spinner recipientSpinner;
    private Spinner challengeSpinner;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_challenges_add, container, false);

        closeButton = view.findViewById(R.id.close_button);
        closeButton.setOnClickListener(closeClickListener);

        recipientSpinner = (Spinner) view.findViewById(R.id.recipient_spinner);

        ArrayList<String> userNames = new ArrayList<>();
        for(User user : Engine.getInstance().getUserManager().getUsers()) {
            userNames.add(user.displayName);
        }
        ArrayAdapter<String> recipientAdapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item, userNames);
        recipientAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        recipientSpinner.setAdapter(recipientAdapter);

        challengeSpinner = (Spinner) view.findViewById(R.id.challenge_spinner);
        ArrayAdapter<CharSequence> challengeAdapter = ArrayAdapter.createFromResource(getContext(),
                R.array.challenges_type_array, android.R.layout.simple_spinner_item);
        challengeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        challengeSpinner.setAdapter(challengeAdapter);

        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    private View.OnClickListener closeClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            getActivity().onBackPressed();
        }
    };

    @Override
    public Animation onCreateAnimation(int transit, boolean enter, int nextAnim) {
        if(enter) {
            return AnimationUtils.loadAnimation(getContext(), R.anim.slide_in_from_bottom);
        } else {
            return AnimationUtils.loadAnimation(getContext(), R.anim.slide_out_to_bottom);
        }
    }
}
