package com.example.group26.coupletones;

import android.app.Application;

import com.firebase.client.Firebase; // for myFirebaseRef

/**
 * To use this global class/object, do:
 * ((Initialize) this.getApplication()).whatevermethodyouneed();
 */
public class Initialize extends android.app.Application{
    private Spouse spouse;
    private Firebase myFirebaseRef;



    public Initialize() {
        spouse = new Spouse(); // automatically starts to listen
        myFirebaseRef = new Firebase("https://coupletonescse100.firebaseio.com");

    }


    public Spouse getSpouse() {
        return spouse;
    }

    public Firebase getFirebase() {
        return myFirebaseRef;
    }

    public void setSpouseForAllClasses() {

    }

    public void setFirebaseForAllClass() {

    }

}
