package com.hackmanchester2016.swearjar.ui.challenges;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.hackmanchester2016.swearjar.engine.comms.models.Challenge;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by dant on 30/10/2016.
 */

public class ChallengesAdapter extends RecyclerView.Adapter<ChallengesRecyclerViewHolder> {

    private List<Challenge> challenges;
    private ChallengesCallback callback;

    public ChallengesAdapter(ChallengesCallback callback){
        this.callback = callback;
    }

    public void setChallenges(List<Challenge> challenges) {
        if(challenges != null) {
            this.challenges = new ArrayList<>(challenges);
        }
    }

    @Override
    public ChallengesRecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ChallengesRecyclerViewHolder(parent);
    }

    @Override
    public void onBindViewHolder(final ChallengesRecyclerViewHolder holder, int position) {
        holder.setChallenge(challenges.get(position));
        holder.setCallback(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = holder.getAdapterPosition();
                if(position != RecyclerView.NO_POSITION) {
                    callback.viewChallengeDetails(challenges.get(position));
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return challenges != null ? challenges.size() : 0;
    }

    public interface ChallengesCallback{
        void viewChallengeDetails(Challenge challenge);
    }

}
