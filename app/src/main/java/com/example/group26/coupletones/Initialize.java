package com.example.group26.coupletones;

import android.app.Application;
import android.content.Context;
import android.util.Log;

import com.firebase.client.Firebase; // for myFirebaseRef


/**
 * To use this global class/object, do:
 * ((Initialize) this.getApplication()).whatevermethodyouneed();
 */
public class Initialize extends android.app.Application{
    private static Initialize application;
    private Spouse spouse;
    private Firebase myFirebaseRef;



    public Initialize(Context appcontext) {
        Log.d("INITIALIZE", "constructor");
        super.onCreate();
        application = this;
        myFirebaseRef = new Firebase("https://coupletonescse100.firebaseio.com");
        Firebase.setAndroidContext(appcontext);
        spouse = new Spouse(myFirebaseRef); // automatically starts to listen


    }

    public void startListeningToMyself() {

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
