package com.hackmanchester2016.swearjar.engine;

import android.content.Context;

import com.hackmanchester2016.swearjar.engine.comms.RetrofitClient;
import com.hackmanchester2016.swearjar.engine.controllers.SetupController;
import com.hackmanchester2016.swearjar.engine.managers.AuthManager;
import com.hackmanchester2016.swearjar.engine.managers.UserManager;

/**
 * Created by dant on 29/10/2016.
 */

public class Engine {

    private static Engine engine;

    private AuthManager authManager;
    private RetrofitClient retrofitClient;
    private UserManager userManager;
    private SetupController setupController;

    private final Context context;

    private Engine(Context context) {
        this.context = context;
    }

    public static void initialize(Context context) {
        if(engine == null) {
            engine = new Engine(context);
        }
    }

    public static Engine getInstance() {
        if(engine == null) {
            throw new RuntimeException("You must call initialize() before you call getInstance()");
        }
        return engine;
    }

    public AuthManager getAuthManager() {
        if(authManager == null) {
            authManager = new AuthManager();
        }
        return authManager;
    }

    public RetrofitClient getRetrofitClient() {
        if(retrofitClient == null) {
            retrofitClient = new RetrofitClient();
        }
        return retrofitClient;
    }

    public SetupController getSetupController() {
        if(setupController == null) {
            setupController = new SetupController(context);
        }
        return setupController;
    }

    public UserManager getUserManager(){
        if(userManager == null){
            userManager = new UserManager();
        }
        return userManager;
    }
}
