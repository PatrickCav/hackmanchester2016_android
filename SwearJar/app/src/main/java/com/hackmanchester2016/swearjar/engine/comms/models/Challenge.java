package com.hackmanchester2016.swearjar.engine.comms.models;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by dant on 30/10/2016.
 */

public class Challenge implements Serializable{

    @SerializedName("id")
    public String id;

    @SerializedName("challengerId")
    public String challengerId;

    @SerializedName("recipientId")
    public String recipientId;

    @SerializedName("type")
    public String challengeType;

    @SerializedName("forfeit")
    public int forfeit;

    @SerializedName("fromDate")
    public Date fromDate;

    @SerializedName("toDate")
    public Date toDate;

}
