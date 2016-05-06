package com.example.group26.coupletones;

/**
 * Created by Jeremy on 5/6/2016.
 */
public class Spouse {

    public String spouseName;
    public String spouseID;
    public boolean isOnline;

    // Check firebase to determine if spouse is online
    public boolean spouseIsOnline() {

        //TODO figure out how to check if spouse's phone is connected to firebase
        return true;
    }
}
