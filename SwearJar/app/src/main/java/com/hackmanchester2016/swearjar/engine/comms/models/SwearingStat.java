package com.hackmanchester2016.swearjar.engine.comms.models;

import com.google.gson.annotations.SerializedName;

/**
 * Created by patrickc on 29/10/2016
 */
public class SwearingStat implements Comparable<SwearingStat>{

    public static SwearingStat testStat(){
        SwearingStat stat = new SwearingStat();
        stat.count = 3;
        stat.word = "Shittingballs";
        return stat;
    }

    @SerializedName("word")
    public String word;

    @SerializedName("count")
    public int count;


    @Override
    public String toString() {
        return word + " count: " + count;
    }

    @Override
    public int compareTo(SwearingStat o) {
        return o.count - count;
    }
}
