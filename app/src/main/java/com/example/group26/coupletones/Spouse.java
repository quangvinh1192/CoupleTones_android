package com.example.group26.coupletones;

import android.app.NotificationManager;
import android.content.Context;
import android.support.v7.app.NotificationCompat;
import android.util.Log;

import com.firebase.client.AuthData;
import com.firebase.client.ChildEventListener;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

/**
 * Created by Jeremy on 5/6/2016.
 */
public class Spouse {
    public String spouseName;
    public String spouseUID;
    public String spouseEmail;
    public Firebase myFirebaseRef;
    public NotificationControl notify;
    private boolean addedListeners;

    /** empty constructor */
    Spouse () {}

    /** sets up my firebase */
    public void setMyFirebaseRef(Firebase FirebaseRef) {
        this.myFirebaseRef = FirebaseRef;
    }

    /**
     * Name: listenToSpouseFavPlaces
     * initializes listener
     */
    public void listenToSpouseFavPlaces() {
        if (myFirebaseRef == null) {
            Log.d ("SPOUSE", "listentoSpouseFavPlacesCould not listen to Firebase");
            return;
        }
        AuthData authData = myFirebaseRef.getAuth();
        String userId = authData.getUid();
        final Firebase tempRef = myFirebaseRef.child("users").child(userId);
        tempRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                /* if spouse exists, create a listener to spouse and save spouse's info */
                if (snapshot.child("spouseUID").exists()){
                    spouseUID = snapshot.child("spouseUID").getValue().toString();
                    spouseEmail = snapshot.child("spouseEmail").getValue().toString();
                    createAListenerToSpouseFavPlaces(spouseUID,spouseEmail);
                }
                else{
                    Log.d("MyApp", "You have no spouse");
                }
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {
                System.out.println("The read failed: " + firebaseError.getMessage());
            }
        });
    }

    /** Name: createAListenerToSpouseFavPlaces
     * creates a listener to see if spouse has visited favorite places. Listens to firebase
     * @param spouseID
     */
    private void createAListenerToSpouseFavPlaces(final String spouseID, final String spouseEmail){
        if (myFirebaseRef == null) {
            Log.d ("SPOUSE", "createAListener: Could not listen to Firebase");
            return;
        }
        Firebase spouseReff = myFirebaseRef.child("users").child(spouseID);
        spouseReff.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                Log.d("MyApp",snapshot.toString());
                 if (snapshot.child("yourEmail").exists()){
                    String a = snapshot.child("yourEmail").getValue().toString();
                    if (a.equals(spouseEmail)) {
                        Log.d("MyApp", "Do you ever go here?");
                        /* only if listeners have not been added, add a listener */
                        if(!addedListeners) {
                            check(spouseID);
                            addedListeners = true;
                        }
                    }
                }
                else{
                    Log.d("MyApp","Your spouse is not using the app yet!");
                }
            }
            @Override
            public void onCancelled(FirebaseError firebaseError) {
                System.out.println("The read failed: " + firebaseError.getMessage());
            }
        });


    }

    // checks to see if a notification can be sent and then calls on it
    public void check(String spouseID){

        final Firebase spouseRef = myFirebaseRef.child("users").child(spouseID).child("favPlaces");

        spouseRef.addChildEventListener(new ChildEventListener() {
            // Retrieve new posts as they are added to the database
            @Override
            public void onChildAdded(DataSnapshot snapshot, String previousChildKey) {}

            @Override
            public void onChildChanged(DataSnapshot snapshot, String previousChildKey) {

                String title = (String) snapshot.child("name").getValue();
                boolean arrived = false;
                /* checks to see whether place has been arrived at or departed from */
                if ((String) snapshot.child("visited").getValue().toString() == "true") {
                    arrived = true;
                } else {
                    arrived = false;
                }

                aFavoritePlace temp = snapshot.getValue(aFavoritePlace.class);
                Log.e("SPOUSE:check", title);

                notify.notify(arrived, temp);
            }

            @Override
            public void onChildRemoved(DataSnapshot snapshot) {}
            @Override
            public void onChildMoved(DataSnapshot snapshot, String previousChildKey) {}
            @Override
            public void onCancelled(FirebaseError e) {}
        });

    }

}
