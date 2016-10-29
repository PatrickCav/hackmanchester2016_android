package com.hackmanchester2016.swearjar.engine.managers;

import android.support.annotation.NonNull;
import android.util.Log;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.hackmanchester2016.swearjar.engine.Engine;
import com.hackmanchester2016.swearjar.engine.comms.models.User;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by patrickc on 30/10/2016
 */
public class UserManager implements ChildEventListener {

    private static final String TAG = "UserManager";

    private DatabaseReference database;

    private String currentUserId;

    private List<User> users = new ArrayList<>();

    public UserManager(){
        initDatabase();
    }

    private void initDatabase(){
        AuthManager manager = Engine.getInstance().getAuthManager();

        if (manager.isSignedIn()) {
            users = new ArrayList<>();
            currentUserId = manager.getUserId();
            database = FirebaseDatabase.getInstance().getReference().child("users");
            database.addChildEventListener(this);
        }
    }

    public void updateCurrentUser(){
        if(database == null){
            initDatabase();
        }
        database.child(currentUserId).setValue(Engine.getInstance().getAuthManager().getUser()).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                Log.d(TAG, "onComplete() called with: " + "task = [" + task + "]");
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.d(TAG, "onFailure() called with: " + "e = [" + e + "]");
            }
        });
    }

    public List<User> getUsers(){
        return users;
    }

    @Override
    public void onChildAdded(DataSnapshot dataSnapshot, String s) {
        if(dataSnapshot != null){
            User user = dataSnapshot.getValue(User.class);
            if(isNotCurrentUser(user)) {
                users.add(user);
            }
        }
    }

    @Override
    public void onChildChanged(DataSnapshot dataSnapshot, String s) {

    }

    @Override
    public void onChildRemoved(DataSnapshot dataSnapshot) {

    }

    @Override
    public void onChildMoved(DataSnapshot dataSnapshot, String s) {

    }

    @Override
    public void onCancelled(DatabaseError databaseError) {

    }

    private boolean isNotCurrentUser(User user){
        return user.userId != null && !user.userId.equals(currentUserId);
    }
}
