package com.hackmanchester2016.swearjar.engine.comms.models;

import com.google.gson.annotations.SerializedName;

/**
 * Created by patrickc on 29/10/2016
 */
public class LocationStat implements Comparable<LocationStat>{

    public static LocationStat testStat(){
        LocationStat stat = new LocationStat();
        stat.count = 3;
        stat.place = "Shittingballs";
        return stat;
    }

    @SerializedName("place")
    public String place;

    @SerializedName("count")
    public int count;


    @Override
    public String toString() {
        return place + " count: " + count;
    }

    @Override
    public int compareTo(LocationStat o) {
        return o.count - count;
    }
}
