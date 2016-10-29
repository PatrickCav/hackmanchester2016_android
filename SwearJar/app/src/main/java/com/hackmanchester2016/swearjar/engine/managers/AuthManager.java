package com.hackmanchester2016.swearjar.engine.managers;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

/**
 * Created by patrickc on 29/10/2016
 */
public class AuthManager {

    private AuthManager(){}

    private static AuthManager instance;

    public static AuthManager getInstance(){
        if(instance == null){
            instance = new AuthManager();
        }
        return instance;
    }

    public boolean isSignedIn(){
        return FirebaseAuth.getInstance().getCurrentUser() != null;
    }

    public String getUserId(){
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if(user != null){
            return user.getUid();
        } else{
            return null;
        }
    }

}
