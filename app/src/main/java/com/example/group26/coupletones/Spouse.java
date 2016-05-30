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
    private Firebase myFirebaseRef;


    // go to my account -> spouseIDfield

    Spouse () {
    }

    public void setMyFirebaseRef(Firebase myFirebaseRef) {
        myFirebaseRef = myFirebaseRef;
    }

    Spouse(String name, String ID) {
        spouseName = name;
        spouseUID = ID;
    }


    /** name: createANotification
     * sends a notification to you saying that your S.O. visited a page
     * @param title
     */
    void createANotification(String title){
        //create a notification object and call notify()
    }

    /**
     * Name: listenToSpouseFavPlaces
     * initializes listener
     */
    public void listenToSpouseFavPlaces(Firebase myFirebaseRef) {
        if (myFirebaseRef == null) {
            Log.d ("SPOUSE", "Could not listen to Firebase");
            return;
        }
        AuthData authData = myFirebaseRef.getAuth();
        String userId = authData.getUid();
        final Firebase tempRef = myFirebaseRef.child("users").child(userId);
        tempRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                if (snapshot.child("spouseUID").exists()){
                    Log.d("MyApp", snapshot.child("spouseUID").getValue().toString());
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

        Log.d("LoginPage","OnCreate, initialized firebase");

        if (myFirebaseRef == null) {
            Log.d ("SPOUSE", "Could not listen to Firebase");
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
                        check(spouseID);
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


    public void check(String spouseID){
        Log.d("I WANT TO SEE HiM", spouseID);

        final Firebase spouseRef = myFirebaseRef.child("users").child(spouseID).child("favPlaces");
        Log.d("SpouseID", spouseID);
        spouseRef.addChildEventListener(new ChildEventListener() {
            // Retrieve new posts as they are added to the database
            @Override
            public void onChildAdded(DataSnapshot snapshot, String previousChildKey) {

            }

            @Override
            public void onChildChanged(DataSnapshot snapshot, String previousChildKey) {
                String title = (String) snapshot.child("name").getValue();
                Log.e("Count ", title);

                createANotification(title);
            }

            @Override
            public void onChildRemoved(DataSnapshot snapshot) {
            }

            @Override
            public void onChildMoved(DataSnapshot snapshot, String previousChildKey) {
            }

            @Override
            public void onCancelled(FirebaseError e) {
            }
            //... ChildEventListener also defines onChildChanged, onChildRemoved,
            //    onChildMoved and onCanceled, covered in later sections.
        });

    }

}
