package com.hackmanchester2016.swearjar.engine;

import com.hackmanchester2016.swearjar.engine.managers.AuthManager;

/**
 * Created by dant on 29/10/2016.
 */

public class Engine {

    private static Engine engine;
    private static AuthManager authManager;

    private Engine() {};

    public static Engine getInstance() {
        if(engine == null) {
            engine = new Engine();
        }
        return engine;
    }

    public AuthManager getAuthManager() {
        if(authManager == null) {
            authManager = AuthManager.getInstance();
        }

        return authManager;
    }
}
