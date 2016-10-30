package com.hackmanchester2016.swearjar.ui.challenges;

import android.graphics.Bitmap;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.BitmapImageViewTarget;
import com.hackmanchester2016.swearjar.R;
import com.hackmanchester2016.swearjar.engine.Engine;
import com.hackmanchester2016.swearjar.engine.comms.models.Challenge;
import com.hackmanchester2016.swearjar.engine.comms.models.User;

import java.text.NumberFormat;
import java.util.List;

/**
 * Created by dant on 30/10/2016.
 */

public class ChallengesRecyclerViewHolder extends RecyclerView.ViewHolder {

    private static final long MILLIS_IN_DAY = 86400000;

    private TextView challengeTitle;
    private TextView challenger;
    private TextView challengedBy;
    private TextView challenged;
    private TextView challengeFine;
    private TextView daysRemaining;
    private ImageView challenegerImage;

    private View clickableForeground;

    public ChallengesRecyclerViewHolder(ViewGroup parent) {
        super(LayoutInflater.from(parent.getContext()).inflate(R.layout.challenge_cell, parent, false));

        challengeTitle = (TextView) itemView.findViewById(R.id.challenge_title);
        challenger = (TextView) itemView.findViewById(R.id.challenger_name);
        challengedBy = (TextView) itemView.findViewById(R.id.challenged_by);
        challenged = (TextView) itemView.findViewById(R.id.challenged);
        challengeFine = (TextView) itemView.findViewById(R.id.challenge_fine);
        daysRemaining = (TextView) itemView.findViewById(R.id.days_remaining);
        challenegerImage = (ImageView) itemView.findViewById(R.id.challenger_image);

        clickableForeground = itemView.findViewById(R.id.selectable_foreground);
    }

    public void setChallengeMy(Challenge challenge) {
        User user = Engine.getInstance().getUserManager().getUser(challenge.challengerId);
        challenger.setText(Engine.getInstance().getUserManager().getUser(challenge.challengerId).displayName);
        challenged.setVisibility(View.GONE);

        setChallenge(user, challenge);
    }

    public void setChallengeFriend(Challenge challenge) {
        User user = Engine.getInstance().getUserManager().getUser(challenge.recipientId);
        challenger.setText(user.displayName);
        challengedBy.setVisibility(View.GONE);

        setChallenge(user, challenge);
    }

    private void setChallenge(User user, Challenge challenge) {
        challengeTitle.setText(getChallengeType(challenge.challengeType));
        challengeFine.setText(NumberFormat.getCurrencyInstance().format((double)challenge.forfeit/100));

        long days = (challenge.toDate.getTime() - System.currentTimeMillis())/MILLIS_IN_DAY;
        daysRemaining.setText(Long.toString(days));

        Glide.with(challenegerImage.getContext()).load(user.displayIcon).asBitmap().centerCrop().into(new BitmapImageViewTarget(challenegerImage) {
            @Override
            protected void setResource(Bitmap resource) {
                RoundedBitmapDrawable circularBitmapDrawable =
                        RoundedBitmapDrawableFactory.create(challenegerImage.getContext().getResources(), resource);
                circularBitmapDrawable.setCircular(true);
                challenegerImage.setImageDrawable(circularBitmapDrawable);
            }
        });
    }

    private String getChallengeType(String type){
        switch (type){
            default:
            case "swearing":
                return "No Swearing!";
            case "pub":
                return "No Pub!";
        }
    }

    public void setCallback(View.OnClickListener clickListener){
        clickableForeground.setOnClickListener(clickListener);
    }
}
