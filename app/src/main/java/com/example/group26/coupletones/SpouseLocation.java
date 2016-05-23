package com.example.group26.coupletones;

import android.location.Location;
import android.os.Vibrator;
import android.support.v7.app.NotificationCompat;

import java.sql.Date;

/**
 * Created by gagan on 5/22/16.
 */
public class SpouseLocation {
    public Location location;
    public String name;
    public String vibrationLevel;
    public String soundType;
    public boolean visited;
    public Date dateVisited;

    /* Constructor for a SpouseLocation
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

    //checks if this location was visited or not
    public boolean isVisited() {
        return visited;
    }

    //checks what time the location was visited
    public Date getTimeVisited() {
        return dateVisited;
    }

    //when you visit a place make sure to call this method and pass in true, Date()
    //Date() will create an object with the current date and time
    public void setVisited(boolean visited, Date dateAndTime){
        this.visited = visited;
        dateVisited = dateAndTime;
    }
}
