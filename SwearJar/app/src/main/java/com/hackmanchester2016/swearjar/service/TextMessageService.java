package com.hackmanchester2016.swearjar.service;

import android.app.Service;
import android.content.Intent;
import android.database.ContentObserver;
import android.database.Cursor;
import android.net.Uri;
import android.os.Handler;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

import com.hackmanchester2016.swearjar.engine.Engine;
import com.hackmanchester2016.swearjar.engine.comms.models.SendTextRequest;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by tomr on 29/10/2016.
 */

public class TextMessageService extends Service {

    private static final String TAG = "MessagingIntentService";
    private static final String SMS_CONTENT = "content://sms";

    private ContentObserver mObserver = new SMSObserver(new Handler());

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        getContentResolver().registerContentObserver(Uri.parse(SMS_CONTENT), true, mObserver);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        getContentResolver().unregisterContentObserver(mObserver);
    }

    private void textDetected(String body) {
        // do things here
        Engine.getInstance().getRetrofitClient().getApi().sendText(Engine.getInstance().getAuthManager().getUserId(), new SendTextRequest(body)).enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                Log.d(TAG, "SUCCESS");
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Log.d(TAG, "FAIL");
            }
        });
    }

    private class SMSObserver extends ContentObserver {

        SMSObserver(Handler handler){
            super(handler);
        }

        @Override
        public void onChange(boolean selfChange) {
            try (Cursor cur = getContentResolver().query(Uri.parse(SMS_CONTENT), null, null, null, null)) {
                cur.moveToNext(); // Assume they have some messages haha banter

                String protocol = cur.getString(cur.getColumnIndex("protocol"));

                if (protocol == null) {
                    String body = cur.getString(cur.getColumnIndex("body"));
                    textDetected(body);
                }

                cur.close();
            } catch (Exception ex) {
                Log.d(TAG, "Fuck you");
            }
        }
    }
}
