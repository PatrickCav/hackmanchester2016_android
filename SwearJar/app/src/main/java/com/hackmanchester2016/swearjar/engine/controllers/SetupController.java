package com.hackmanchester2016.swearjar.engine.controllers;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by dant on 29/10/2016.
 */

public class SetupController {

    private static String SETUP_PREFERENCES = "SETUP_PREFERENCES";
    private static String SETUP_COMPLETE = "SETUP_COMPLETE";

    private final Context context;

    private boolean setupComplete;

    public SetupController(Context context){
        this.context = context;
    }

    public void setSetupComplete(boolean complete) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(SETUP_PREFERENCES, Context.MODE_PRIVATE);
        sharedPreferences
                .edit()
                .putBoolean(SETUP_COMPLETE, complete)
                .apply();
    }

    public boolean setupComplete() {
        SharedPreferences sharedPreferences = context.getSharedPreferences(SETUP_PREFERENCES, Context.MODE_PRIVATE);
        return sharedPreferences.getBoolean(SETUP_COMPLETE, false);
    }
}


