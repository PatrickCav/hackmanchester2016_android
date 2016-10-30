package com.hackmanchester2016.swearjar.engine.comms.models;

import com.google.gson.annotations.SerializedName;

import java.util.Date;

/**
 * Created by dant on 30/10/2016.
 */

public class Challenge {

    @SerializedName("id")
    private String challengerId;

    @SerializedName("recipientId")
    public String recipientId;

    @SerializedName("forfeit")
    public int forfeit;

    @SerializedName("fromDate")
    public Date fromDate;

    @SerializedName("toDate")
    public Date toDate;

}
