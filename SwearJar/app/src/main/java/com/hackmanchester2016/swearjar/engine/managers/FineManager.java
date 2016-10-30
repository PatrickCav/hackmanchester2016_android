package com.hackmanchester2016.swearjar.engine.managers;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.TaskStackBuilder;
import android.util.Log;

import com.hackmanchester2016.swearjar.LaunchActivity;
import com.hackmanchester2016.swearjar.R;
import com.hackmanchester2016.swearjar.engine.DateUtils;
import com.hackmanchester2016.swearjar.engine.Engine;
import com.hackmanchester2016.swearjar.engine.comms.models.SwearingStat;
import com.hackmanchester2016.swearjar.engine.comms.models.SwearingStatsResponse;

import java.text.NumberFormat;
import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by tomr on 29/10/2016.
 */

public class FineManager {

    private static final String TAG = "FineManager";

    private Context context;

    private static final String FINE_PREFERENCES = "FINE_PREFERENCES";
    private static final String SWEAR_COUNT = "SWEAR_COUNT";

    private static final int FINE_CONVERSION = 137;

    private FineListener fineListener;

    public FineManager(Context context) {
        this.context = context;
    }

    public void setFineListener(FineListener listener){
        fineListener = listener;
    }

    public void removeListener(){
        fineListener = null;
    }

    public int getFineValue() {
        SharedPreferences sharedPreferences = context.getSharedPreferences(FINE_PREFERENCES, Context.MODE_PRIVATE);
        return sharedPreferences.getInt(SWEAR_COUNT, 0);
    }

    public void setFineValue(int fine) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(FINE_PREFERENCES, Context.MODE_PRIVATE);
        sharedPreferences
                .edit()
                .putInt(SWEAR_COUNT, fine)
                .apply();
    }

    public int calculateFine(int conversion, int totalSwears) {
        return (conversion * totalSwears);
    }

    private void notifyUser(int charge) {
        Log.d(TAG, "Notifying of charge " + charge + "p");

        if(fineListener != null){
            fineListener.fineUpdated();
        }

        NotificationCompat.Builder mBuilder =
                new NotificationCompat.Builder(context)
                        .setSmallIcon(R.drawable.icon)
                        .setContentTitle("Wash your mouth out with soap!")
                        .setContentText("You swore, and it cost you!");

        Intent resultIntent = new Intent(context, LaunchActivity.class);

        TaskStackBuilder stackBuilder = TaskStackBuilder.create(context);

        stackBuilder.addParentStack(LaunchActivity.class);

        stackBuilder.addNextIntent(resultIntent);
        PendingIntent resultPendingIntent =
                stackBuilder.getPendingIntent(
                        0,
                        PendingIntent.FLAG_UPDATE_CURRENT
                );
        mBuilder.setContentIntent(resultPendingIntent);
        NotificationManager mNotificationManager =
                (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        mNotificationManager.notify(1989871987, mBuilder.build());
    }

    public void calculateFineDifference() {
        Engine.getInstance().getRetrofitClient().getApi().getSwearingStats(DateUtils.formatDate(new Date(0)), DateUtils.formatDate(new Date())).enqueue(new Callback<SwearingStatsResponse>() {
            @Override
            public void onResponse(Call<SwearingStatsResponse> call, Response<SwearingStatsResponse> response) {
                List<SwearingStat> stats = response.body().stats;
                int count = 0;

                for(SwearingStat stat : stats) {
                    count += stat.count;
                }

                int oldCount = getFineValue();

                setFineValue(count);

                Log.d(TAG, oldCount + " - " + count);
                if(count > oldCount) {
                    notifyUser(count - oldCount);
                }
            }

            @Override
            public void onFailure(Call<SwearingStatsResponse> call, Throwable t) {
            }
        });
    }

    public interface FineListener{
        void fineUpdated();
    }
}
