package com.hackmanchester2016.swearjar.engine.comms.models;

import com.google.gson.annotations.SerializedName;

/**
 * Created by patrickc on 29/10/2016
 */
public class SwearingStat {

    public static SwearingStat testStat(){
        SwearingStat stat = new SwearingStat();
        stat.numberOfUses = 3;
        stat.word = "Shittingballs";
        return stat;
    }

    @SerializedName("word")
    public String word;

    @SerializedName("number")
    public int numberOfUses;


}
