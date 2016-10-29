package com.hackmanchester2016.swearjar.engine.managers;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

/**
 * Created by patrickc on 29/10/2016
 */
public class AuthManager {

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

    public void signOut(){
        FirebaseAuth.getInstance().signOut();
    }
}
