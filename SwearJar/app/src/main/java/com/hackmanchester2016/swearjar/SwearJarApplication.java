package com.hackmanchester2016.swearjar;

import android.app.Application;

import com.hackmanchester2016.swearjar.engine.Engine;

/**
 * Created by dant on 29/10/2016.
 */

public class SwearJarApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        Engine.initialize(getApplicationContext());
    }
}
