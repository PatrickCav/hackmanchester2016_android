package com.hackmanchester2016.swearjar.engine.comms.models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by dant on 30/10/2016.
 */

public class ChallengeResponse {

    @SerializedName("results")
    public List<Challenge> results;

    public List<Challenge> getResults() {
        return results;
    }
}
