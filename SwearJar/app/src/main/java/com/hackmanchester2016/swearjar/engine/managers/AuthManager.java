package com.hackmanchester2016.swearjar.engine.managers;

import android.app.Activity;
import android.support.annotation.NonNull;

import com.firebase.ui.auth.AuthUI;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.hackmanchester2016.swearjar.engine.comms.models.User;

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

    public User getUser(){
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if(user != null){
            return new User(user.getUid(), user.getDisplayName(), user.getPhotoUrl().toString());
        } else{
            return null;
        }
    }

    public void signOut(Activity activity, OnCompleteListener listener) {
        FirebaseAuth.getInstance().signOut();

        AuthUI.getInstance().signOut(activity).addOnCompleteListener(listener);
    }
}
