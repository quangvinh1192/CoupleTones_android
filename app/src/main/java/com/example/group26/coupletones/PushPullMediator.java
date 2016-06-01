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
    aFavoritePlace visited;
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
    public boolean checkToSend(aFavoritePlace currentLocation, HashMap<String,aFavoritePlace> favoriteLocations) {
        VisitFavoritesCalculator calculator = new VisitFavoritesCalculator();
        aFavoritePlace newlyVisited = calculator.calculateVisited(currentLocation, favoriteLocations);

        // if we are visiting a favorited location and we haven't already sent a push notification, send one
        if (newlyVisited != null) {
            if (visited != newlyVisited ) {
                visited = newlyVisited;
                Log.i("My App", "YOU JUST VISITED A NEW PLACE");
                //TODO check if spouse is online
                //send push notification
            }
            return true;

        }
        visited = null;
        return false;
    }
    public aFavoritePlace getVisited(){
        return visited;
    }

    public void updateVisitedPlaceFirebase(final String nameOfVisitedPlace) {
        AuthData authData = myFirebaseRef.getAuth();
        String userId = authData.getUid();
        final Firebase tempRef = myFirebaseRef.child("users").child(userId).child("favPlaces");

        tempRef.addChildEventListener(new ChildEventListener() {
            // Retrieve new posts as they are added to the database
            @Override
            public void onChildAdded(DataSnapshot snapshot, String previousChildKey) {
                String temp = snapshot.getKey();
                aFavoritePlace tempPlace = snapshot.getValue(aFavoritePlace.class);
                if (tempPlace.getName().equals(nameOfVisitedPlace)) {
                    Firebase updatePlace = tempRef.child(temp).child("visited");
                    updatePlace.setValue(true);

                    //Firebase updateTime = tempRef.child(temp).child("arrivalTime");
                    //updateTime.push().setValue(System.currentTimeMillis());
                }
                else if (nameOfVisitedPlace.equals("YOU-ARE-NOT-VISITING-ANY-PLACE")){

                    Log.d("PUSHPULL", "not visiting a place");

                    Firebase updatePlace = tempRef.child(temp).child("visited");

                    //if (tempPlace.isVisited() == true) {

//                        Firebase updateTime = tempRef.child(temp).child("departureTime");
                    //                      updateTime.push().setValue(System.currentTimeMillis());
                    //                }

                    updatePlace.setValue(false);
                }

            }

            @Override
            public void onChildChanged(DataSnapshot snapshot, String previousChildKey) {
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
