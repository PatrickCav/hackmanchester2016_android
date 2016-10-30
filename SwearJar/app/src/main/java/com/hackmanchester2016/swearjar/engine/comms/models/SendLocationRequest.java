package com.hackmanchester2016.swearjar.engine.comms.models;

import java.util.Calendar;

/**
 * Created by tomr on 30/10/2016.
 */

public class SendLocationRequest {
    public String challengeId;
    public String name;
    public String locationType;
    public String timestamp;

    public SendLocationRequest(String name, String locationType) {
        this.challengeId = "hi";
        this.name = name;
        this.locationType = locationType;
        this.timestamp = Calendar.getInstance().toString();
    }
}
