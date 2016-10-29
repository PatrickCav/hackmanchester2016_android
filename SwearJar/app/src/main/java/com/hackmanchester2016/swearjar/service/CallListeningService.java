package com.hackmanchester2016.swearjar.service;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

import com.hackmanchester2016.swearjar.engine.Engine;
import com.hackmanchester2016.swearjar.engine.comms.RetrofitClient;
import com.hackmanchester2016.swearjar.engine.comms.models.SendTextRequest;
import com.hackmanchester2016.swearjar.service.util.MicManager;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

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
                Engine.getInstance().getRetrofitClient().getApi().sendText(Engine.getInstance().getAuthManager().getUserId(), new SendTextRequest(result)).enqueue(new Callback<Void>() {
                    @Override
                    public void onResponse(Call<Void> call, Response<Void> response) {

                    }

                    @Override
                    public void onFailure(Call<Void> call, Throwable t) {

                    }
                });
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
