package com.hackmanchester2016.swearjar.engine.comms.models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by patrickc on 29/10/2016
 */
public class SwearingStatsResponse {

    @SerializedName("results")
    public List<SwearingStat> stats;

}
