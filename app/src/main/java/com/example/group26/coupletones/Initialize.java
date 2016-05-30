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



    public Initialize () {
        application = this;
    }


    public boolean initBoth(Context context) {
        boolean firebaseSet = setFirebase(context);
        boolean spouseSet = setSpouse();
        return firebaseSet || spouseSet;
    }
    public boolean setFirebase (Context appcontext) {
        Firebase.setAndroidContext(appcontext);
        myFirebaseRef = new Firebase("https://coupletonescse100.firebaseio.com");
        return true;
    }

    public boolean setSpouse() {
        spouse = new Spouse(); // automatically starts to listen
        if (myFirebaseRef != null) {
            spouse.setMyFirebaseRef(myFirebaseRef);
            spouse.listenToSpouseFavPlaces(myFirebaseRef);
            return true;
        } else {
            //throw error
            Log.d("Initialize", "No Firebase for spouse to come from. use setFirebase");
            return false;
        }
    }

    public Initialize(Context appcontext) {
        Log.d("INITIALIZE", "constructor");
        super.onCreate();
        application = this;
        myFirebaseRef = new Firebase("https://coupletonescse100.firebaseio.com");
        Firebase.setAndroidContext(appcontext);
        spouse = new Spouse(); // automatically starts to listen
        spouse.setMyFirebaseRef(myFirebaseRef);
        spouse.listenToSpouseFavPlaces(myFirebaseRef);


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
