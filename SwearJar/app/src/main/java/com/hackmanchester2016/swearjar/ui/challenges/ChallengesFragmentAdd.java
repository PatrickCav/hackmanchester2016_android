package com.hackmanchester2016.swearjar.ui.challenges;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.hackmanchester2016.swearjar.R;
import com.hackmanchester2016.swearjar.engine.Engine;
import com.hackmanchester2016.swearjar.engine.comms.models.Challenge;
import com.hackmanchester2016.swearjar.engine.comms.models.User;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

/**
 * Created by dant on 30/10/2016.
 */

public class ChallengesFragmentAdd extends Fragment {

    public static String TAG = "CHALLENGES_FRAGMENT_ADD";
    public static final int MAX_FINE = 10000;

    private View closeButton;
    private View formContainer;
    private Spinner recipientSpinner;
    private Spinner challengeSpinner;

    private TextView forfeitValue;
    private SeekBar forfeitSeekbar;

    private List<User> users;

    private Challenge challenge = new Challenge();
    private NumberFormat numberFormat = NumberFormat.getCurrencyInstance();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_challenges_add, container, false);

        closeButton = view.findViewById(R.id.close_button);
        closeButton.setOnClickListener(closeClickListener);

        view.findViewById(R.id.challenge_complete_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createChallenge();
            }
        });

        recipientSpinner = (Spinner) view.findViewById(R.id.recipient_spinner);
        forfeitSeekbar = (SeekBar) view.findViewById(R.id.forfeit_seekbar);
        forfeitValue = (TextView) view.findViewById(R.id.forfeit_value);

        forfeitSeekbar.setMax(MAX_FINE);
        forfeitSeekbar.setOnSeekBarChangeListener(seekBarChangeListener);
        setForfeitvalue(0);

        ArrayList<String> userNames = new ArrayList<>();
        users = Engine.getInstance().getUserManager().getUsers();
        for(User user : users) {
            userNames.add(user.displayName);
        }
        ArrayAdapter<String> recipientAdapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item, userNames);
        recipientAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        recipientSpinner.setAdapter(recipientAdapter);
        recipientSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                challenge.recipientId = users.get(position).userId;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) { }
        });

        challengeSpinner = (Spinner) view.findViewById(R.id.challenge_spinner);
        final String[] challengeKeys = getContext().getResources().getStringArray(R.array.challenges_key_array);

        ArrayAdapter<CharSequence> challengeAdapter = ArrayAdapter.createFromResource(getContext(),
                R.array.challenges_type_array, android.R.layout.simple_spinner_item);
        challengeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        challengeSpinner.setAdapter(challengeAdapter);
        challengeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                challenge.challengeType = challengeKeys[position];
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) { }
        });

        return view;
    }

    private void createChallenge() {

        if(challenge.challengeType == null ){
            Toast.makeText(getContext(), "Challenge Type pls...", Toast.LENGTH_LONG).show();
            return;
        } else if (challenge.recipientId == null){
            Toast.makeText(getContext(), "Recipient pls...", Toast.LENGTH_LONG).show();
            return;
        }

        challenge.toDate = new Date(System.currentTimeMillis() + 1234567890);
        challenge.forfeit = forfeitSeekbar.getProgress();

        Engine.getInstance().getRetrofitClient().getApi().createChallenge(challenge).enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                Toast.makeText(getContext(), "Challenge Created!", Toast.LENGTH_LONG).show();
                getActivity().onBackPressed();
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {

            }
        });
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

    private SeekBar.OnSeekBarChangeListener seekBarChangeListener = new SeekBar.OnSeekBarChangeListener() {
        @Override
        public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
            setForfeitvalue(progress);
        }

        @Override
        public void onStartTrackingTouch(SeekBar seekBar) {}

        @Override
        public void onStopTrackingTouch(SeekBar seekBar) {}
    };

    private void setForfeitvalue(int value){
        forfeitValue.setText(numberFormat.format((double)value/100));

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
