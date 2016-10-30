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
import com.hackmanchester2016.swearjar.engine.Engine;
import com.hackmanchester2016.swearjar.engine.comms.models.SwearingStat;
import com.hackmanchester2016.swearjar.engine.comms.models.SwearingStatsResponse;

import java.text.NumberFormat;
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
    private static final String FINE_AMOUNT = "FINE_AMOUNT";

    private static final int FINE_CONVERSION = 137;

    public FineManager(Context context) {
        this.context = context;
    }

    public int getFineValue() {
        SharedPreferences sharedPreferences = context.getSharedPreferences(FINE_PREFERENCES, Context.MODE_PRIVATE);
        return sharedPreferences.getInt(FINE_AMOUNT, 0);
    }

    public void setFineValue(int fine) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(FINE_PREFERENCES, Context.MODE_PRIVATE);
        sharedPreferences
                .edit()
                .putInt(FINE_AMOUNT, fine)
                .apply();
    }

    public int getFineConversion() {
        return FINE_CONVERSION;
    }

    public int calculateFine(int totalSwears) {
        return (FINE_CONVERSION * totalSwears);
    }

    public String getFormattedFine(int fine) {
        NumberFormat formatter = NumberFormat.getCurrencyInstance();

        return formatter.format((double)fine/100);
    }

    private void notifyUser(int charge) {
        Log.d(TAG, "Notifying of charge " + charge + "p");

        NotificationCompat.Builder mBuilder =
                new NotificationCompat.Builder(context)
                        .setSmallIcon(R.drawable.icon)
                        .setContentTitle("Wash your mouth out with soap!")
                        .setContentText("You swore! And it cost you " + getFormattedFine(charge));

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
        Engine.getInstance().getRetrofitClient().getApi().getStats().enqueue(new Callback<SwearingStatsResponse>() {
            @Override
            public void onResponse(Call<SwearingStatsResponse> call, Response<SwearingStatsResponse> response) {
                List<SwearingStat> stats = response.body().stats;
                int count = 0;

                for(SwearingStat stat : stats) {
                    count += stat.count;
                }

                int newFine = calculateFine(count);
                int oldFine = getFineValue();

                setFineValue(newFine);

                Log.d(TAG, oldFine + " - " + newFine);
                if(newFine > oldFine) {
                    notifyUser(newFine - oldFine);
                }
            }

            @Override
            public void onFailure(Call<SwearingStatsResponse> call, Throwable t) {
            }
        });
    }
}
