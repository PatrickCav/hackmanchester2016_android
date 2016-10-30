package com.hackmanchester2016.swearjar.engine.comms.models;

import com.hackmanchester2016.swearjar.engine.DateUtils;

import java.util.Date;

/**
 * Created by tomr on 30/10/2016.
 */

public class SendLocationRequest {
    public String name;
    public String locationType;
    public String timestamp;

    public SendLocationRequest(String name, String locationType) {
        this.name = name;
        this.locationType = locationType;
        this.timestamp = DateUtils.formatDate(new Date());
    }
}
