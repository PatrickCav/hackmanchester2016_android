package com.hackmanchester2016.swearjar.service;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

import com.hackmanchester2016.swearjar.service.util.MicManager;

/**
 * Created by tomr on 29/10/2016.
 */

public class CallListeningService extends Service {

    private static final String TAG = "CallListeningService";

    private MicManager micManager;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        micManager = new MicManager(this, new MicManager.VoiceDetectionResultListener() {
            @Override
            public void onResult(String result) {
                Log.d(TAG, result);
                // Server request here
            }

            @Override
            public void onPartialResult(String result) {

            }
        });

        micManager.startListening();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        micManager.stopListening();
    }
}
