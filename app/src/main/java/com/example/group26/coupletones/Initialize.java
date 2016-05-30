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
    private Context context;



    public Initialize () {
        application = this;
        startListeningToMyself();
    }



    public boolean setFirebase (Context appcontext) {
        context = appcontext;
        Firebase.setAndroidContext(appcontext);
        myFirebaseRef = new Firebase("https://coupletonescse100.firebaseio.com");
        return true;
    }

    /**Name:
     * This method initializes spouse and sets its listeners if firebase is running
     * @return
     */
    public boolean setSpouse() {
        spouse = new Spouse(); // automatically starts to listen
        if (myFirebaseRef != null) {
            Log.d("Initialize", "setSpouse");
            spouse.setMyFirebaseRef(myFirebaseRef);
            spouse.listenToSpouseFavPlaces();
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
        spouse.listenToSpouseFavPlaces();
    }


    public void startListeningToMyself() {


    }

    public Spouse getSpouse() {
        return spouse;
    }

    public Firebase getFirebase() {
        return myFirebaseRef;
    }




}
