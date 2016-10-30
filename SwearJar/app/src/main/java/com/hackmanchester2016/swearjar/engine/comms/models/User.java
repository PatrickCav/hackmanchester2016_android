package com.hackmanchester2016.swearjar.engine.comms.models;

/**
 * Created by patrickc on 30/10/2016
 */
public class User {

    public User(){}

    public User(String userId, String displayName, String displayIcon){
        this.userId = userId;
        this.displayName = displayName;
        this.displayIcon = displayIcon;
    }

    public String displayIcon;
    public String userId;
    public String displayName;

}
