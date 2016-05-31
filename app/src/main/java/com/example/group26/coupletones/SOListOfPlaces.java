package com.example.group26.coupletones;

import android.util.Log;

import com.firebase.client.AuthData;
import com.firebase.client.ChildEventListener;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Jeremy on 5/22/2016.
 */
public class SOListOfPlaces {

    private Firebase myFireBaseRef;
    public List<aFavoritePlace> favoritePlaceList;
    String spouseID;

    // Initialization for favorite place list class
    SOListOfPlaces(Spouse mySpouse, Firebase thisFirebaseRef) {

        // TODO Initialize list of favorite places
        favoritePlaceList = new ArrayList<aFavoritePlace>();

        this.spouseID = mySpouse.spouseUID;

        myFireBaseRef = thisFirebaseRef.child("users").child(spouseID).child("favPlaces");
        Log.d("SOLISTOFPLACES", "constructor");
        updateList();
    }

    public List<aFavoritePlace> getFavoritesList(){

        Log.d("aFavoritePlace", "Size of list: " + favoritePlaceList.size());
        return this.favoritePlaceList;
    }

    void addToList(aFavoritePlace temp) {
        favoritePlaceList.add(temp);
    }

    private void updateList () {

        Log.d("SOLISTOFPLACES", "updateLIST");
        myFireBaseRef.addChildEventListener(new ChildEventListener() {

            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {

                Log.d("aFavoritePlace", "Favorite place is being added to list" + dataSnapshot.child("name").getValue());

                aFavoritePlace temp = dataSnapshot.getValue(aFavoritePlace.class);

                addToList(temp);
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
        });
    }
}
