package com.hackmanchester2016.swearjar.service;

import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.hackmanchester2016.swearjar.engine.Engine;
import com.hackmanchester2016.swearjar.service.util.MicManager;
import com.hackmanchester2016.swearjar.service.util.PhoneCallReceiver;

import java.util.Date;

/**
 * Created by tomr on 29/10/2016.
 */

public class CallStateListener extends PhoneCallReceiver {
    private static final String TAG = "PhoneCallReceiver";

    @Override
    protected void onIncomingCallReceived(Context ctx, String number, Date start) {
        // Don't care. You're a fool. Fellaini. Moysey. Rooney.
    }

    @Override
    protected void onIncomingCallAnswered(Context ctx, String number, Date start) {
        Log.d(TAG, "We should start listening");
        ctx.startService(new Intent(ctx, CallListeningService.class));
    }

    @Override
    protected void onIncomingCallEnded(Context ctx, String number, Date start, Date end) {
        Log.d(TAG, "We should stop listening");
        ctx.stopService(new Intent(ctx, CallListeningService.class));
        Engine.getInstance().getFineManager().calculateFineDifference();
    }

    @Override
    protected void onOutgoingCallStarted(Context ctx, String number, Date start) {
        Log.d(TAG, "We should start listening");
        ctx.startService(new Intent(ctx, CallListeningService.class));
    }

    @Override
    protected void onOutgoingCallEnded(Context ctx, String number, Date start, Date end) {
        Log.d(TAG, "We should stop listening");
        ctx.stopService(new Intent(ctx, CallListeningService.class));
        Engine.getInstance().getFineManager().calculateFineDifference();
    }

    @Override
    protected void onMissedCall(Context ctx, String number, Date start) {

    }
}
