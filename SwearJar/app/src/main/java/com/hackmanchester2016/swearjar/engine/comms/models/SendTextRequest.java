package com.hackmanchester2016.swearjar.engine.comms.models;

/**
 * Created by tomr on 29/10/2016.
 */

public class SendTextRequest {
    String text;
    String challengeId;

    public SendTextRequest(String text) {
        this.text = text.toLowerCase();
        this.challengeId = "2b09ec28-71ae-4f66-8163-268cb0f9b7b0";
    }
}
