package com.hackmanchester2016.swearjar.ui.challenges;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.hackmanchester2016.swearjar.R;
import com.hackmanchester2016.swearjar.engine.Engine;
import com.hackmanchester2016.swearjar.engine.comms.models.Challenge;

import java.text.NumberFormat;

/**
 * Created by dant on 30/10/2016.
 */

public class ChallengesRecyclerViewHolder extends RecyclerView.ViewHolder {

    private static final long MILLIS_IN_DAY = 86400000;

    private TextView challengeTitle;
    private TextView challenger;
    private TextView challengeFine;
    private TextView daysRemaining;

    private View clickableForeground;

    public ChallengesRecyclerViewHolder(ViewGroup parent) {
        super(LayoutInflater.from(parent.getContext()).inflate(R.layout.challenge_cell, parent, false));

        challengeTitle = (TextView) itemView.findViewById(R.id.challenge_title);
        challenger = (TextView) itemView.findViewById(R.id.challenger_name);
        challengeFine = (TextView) itemView.findViewById(R.id.challenge_fine);
        daysRemaining = (TextView) itemView.findViewById(R.id.days_remaining);

        clickableForeground = itemView.findViewById(R.id.selectable_foreground);
    }

    public void setChallenge(Challenge challenge) {
        challengeTitle.setText(challenge.challengeType);
        challenger.setText(Engine.getInstance().getUserManager().getUser(challenge.challengerId).displayName);

        challengeFine.setText(NumberFormat.getCurrencyInstance().format((double)challenge.forfeit/100));

        long days = (challenge.toDate.getTime() - System.currentTimeMillis())/MILLIS_IN_DAY;
        daysRemaining.setText(Long.toString(days));
    }

    public void setCallback(View.OnClickListener clickListener){
        clickableForeground.setOnClickListener(clickListener);
    }
}
