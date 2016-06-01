package com.example.group26.coupletones;

import android.util.Log;

import com.firebase.client.AuthData;
import com.firebase.client.ChildEventListener;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.Query;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.HashMap;
import java.util.HashSet;

/**
 * Created by jkapi on 5/4/2016.
 * This class checks to see whether to push changes to Firebase and whether any information that is
 * passed in should cause a notification.
 */
public class PushPullMediator {
    aFavoritePlace currentLocation;
    aFavoritePlace lastPlace;
    private Firebase myFirebaseRef;

    PushPullMediator(){

    }

    void setMyFirebaseRef(Firebase myFirebaseRef) {
        this.myFirebaseRef = myFirebaseRef;
    }

    Firebase getMyFirebaseRef() {
        return myFirebaseRef;
    }

    /** checks ot see if need ot send a message by calling calculator*/
    public void updateCurrentLocation(aFavoritePlace currentLocation, HashMap<String,aFavoritePlace> favoriteLocations) {
        VisitFavoritesCalculator calculator = new VisitFavoritesCalculator();
        aFavoritePlace newlyVisited = calculator.calculateVisited(currentLocation, favoriteLocations);

        lastPlace = this.currentLocation;
        this.currentLocation = newlyVisited;

    }
    public aFavoritePlace getVisited(){
        return currentLocation;
    }

    public void sendInfoToFirebase() {

        // Arrival
        if (lastPlace == null && currentLocation != null) {

            arrived (currentLocation.getName());
        }

        // Departure
        else if (lastPlace != null && currentLocation == null) {

            departed (lastPlace.getName());
        }

        // Departure + Arrival

        else if (lastPlace != null && currentLocation != null) {

            arrived (currentLocation.getName());
            departed(lastPlace.getName());
        }

    }


    // Helper function to update Firebase when we arrive at a place
    private void arrived (final String nameOfPlace) {

        AuthData authData = myFirebaseRef.getAuth();
        String userId = authData.getUid();
        final Firebase tempRef = myFirebaseRef.child("users").child(userId).child("favPlaces");

        ChildEventListener addListener = new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {

                String temp = dataSnapshot.getKey();
                aFavoritePlace tempPlace = dataSnapshot.getValue(aFavoritePlace.class);
                if (tempPlace.getName().equals(nameOfPlace)) {
                    Firebase updatePlace = tempRef.child(temp).child("visited");
                    updatePlace.setValue(true);

                    Firebase updateTime = tempRef.getParent().child("history").child(tempPlace.getName().toString()).child("arrive");
                    updateTime.push().setValue(System.currentTimeMillis());
                }
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        };

        //TODO IF THERE'S A PROBLEM IT'S PROBABLY HERE
        tempRef.addChildEventListener(addListener);

        //tempRef.removeEventListener(addListener);
    }

    // Helper function for when we depart from a place
    private void departed (final String nameOfPlace) {

        AuthData authData = myFirebaseRef.getAuth();
        String userId = authData.getUid();
        final Firebase tempRef = myFirebaseRef.child("users").child(userId).child("favPlaces");

        ChildEventListener addListener = new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {

                String temp = dataSnapshot.getKey();
                aFavoritePlace tempPlace = dataSnapshot.getValue(aFavoritePlace.class);

                if (tempPlace.getName().equals(nameOfPlace)) {
                    Firebase updatePlace = tempRef.child(temp).child("visited");
                    updatePlace.setValue(false);

                    Firebase updateTime = tempRef.getParent().child("history").child(tempPlace.getName().toString()).child("depart");
                    updateTime.push().setValue(System.currentTimeMillis());
                }
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        };

        //TODO IF THERE'S A PROBLEM IT'S PROBABLY HERE
        tempRef.addChildEventListener(addListener);

        //tempRef.removeEventListener(addListener);
    }

//    public void updateVisitedPlaceFirebase(final String nameOfVisitedPlace) {
//        AuthData authData = myFirebaseRef.getAuth();
//        String userId = authData.getUid();
//        final Firebase tempRef = myFirebaseRef.child("users").child(userId).child("favPlaces");
//
//        tempRef.addChildEventListener(new ChildEventListener() {
//            // Retrieve new posts as they are added to the database
//            @Override
//            public void onChildAdded(DataSnapshot snapshot, String previousChildKey) {
//                String temp = snapshot.getKey();
//                aFavoritePlace tempPlace = snapshot.getValue(aFavoritePlace.class);
//                if (tempPlace.getName().equals(nameOfVisitedPlace)) {
//                    Firebase updatePlace = tempRef.child(temp).child("visited");
//                    updatePlace.setValue(true);
//
//                    Firebase updateTime = tempRef.getParent().child("history").child(tempPlace.getName().toString()).child("arrive");
//                    updateTime.push().setValue(System.currentTimeMillis());
//                }
//                else if (nameOfVisitedPlace.equals("YOU-ARE-NOT-VISITING-ANY-PLACE")){
//
//                    Log.d("PUSHPULL", "not visiting a place");
//
//                    Firebase updatePlace = tempRef.child(temp).child("visited");
//
//                    if (tempPlace.isVisited() == true) {
//
//                          Firebase updateTime = tempRef.getParent().child("history").child(tempPlace.getName().toString()).child("depart");
//                          updateTime.push().setValue(System.currentTimeMillis());
//                    }
//
//                    updatePlace.setValue(false);
//                }
//
//            }
//
//            @Override
//            public void onChildChanged(DataSnapshot snapshot, String previousChildKey) {
//            }
//
//            @Override
//            public void onChildRemoved(DataSnapshot snapshot) {
//            }
//
//            @Override
//            public void onChildMoved(DataSnapshot snapshot, String previousChildKey) {
//            }
//
//            @Override
//            public void onCancelled(FirebaseError e) {
//            }
//            //... ChildEventListener also defines onChildChanged, onChildRemoved,
//            //    onChildMoved and onCanceled, covered in later sections.
//        });
//    }
}
