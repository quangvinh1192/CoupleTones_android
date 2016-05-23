package com.example.group26.coupletones;

import android.location.Location;
import android.os.Vibrator;
import android.support.v7.app.NotificationCompat;

/**
 * Created by gagan on 5/22/16.
 */
public class SpouseLocation {
    public Location location;
    public String name;
    public String vibrationLevel;
    public String soundType;

    /* Constructor for a FavLocation
     * Parameters: Location and a String which holds the vibration type
     * initializes Notification builder with a vibration pattern and a sound
     * that is passed into the constructor
     */
    public SpouseLocation(String name, Location loc, String vibrationLevel, String soundType){
        //initialize location
        location = loc;
        //initialize name
        this.name = name;
        //initialize vibrationLevel
        this.vibrationLevel = vibrationLevel;
        //initialize soundType
        this.soundType = soundType;
    }

    //get location
    public Location getLocation(){
        return location;
    }

    //getSoundType either "low medium or high"
    public String getSoundType() {
        return soundType;
    }

    //gets the name of the location
    public String getName() {
        return name;
    }

    //gets the vibrationLevel "either low medium or high"
    public String getVibrationLevel(){
        return vibrationLevel;
    }
}
