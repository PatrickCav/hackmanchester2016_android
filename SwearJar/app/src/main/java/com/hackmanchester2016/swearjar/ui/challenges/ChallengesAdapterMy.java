package com.hackmanchester2016.swearjar.ui.challenges;

import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.hackmanchester2016.swearjar.engine.comms.models.Challenge;

/**
 * Created by dant on 30/10/2016.
 */

public class ChallengesAdapterMy extends ChallengesAdapterBase {

    public ChallengesAdapterMy(ChallengesCallback callback) {
        super(callback);
    }

    @Override
    protected void bindContentView(final ChallengesRecyclerViewHolder holder, int position,
                                   final Challenge challenge, final ChallengesCallback callback) {
        holder.setChallengeMy(challenge);
        holder.setCallback(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = holder.getAdapterPosition();
                if(position != RecyclerView.NO_POSITION) {
                    callback.viewChallengeDetails(challenge);
                }
            }
        });
    }
}
