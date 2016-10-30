package com.hackmanchester2016.swearjar.ui.challenges;

import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import com.hackmanchester2016.swearjar.engine.comms.models.Challenge;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by dant on 30/10/2016.
 */

public class ChallengesFragmentPagerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<Challenge> challenges;

    public void setChallenges(List<Challenge> challenges) {
        this.challenges = new ArrayList<>(challenges);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ChallengesRecyclerViewHolder(parent);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ((ChallengesRecyclerViewHolder) holder).setChallenge(challenges.get(position));
    }

    @Override
    public int getItemCount() {
        return challenges != null ? challenges.size() : 0;
    }
}
