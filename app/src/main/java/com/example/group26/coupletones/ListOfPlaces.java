package com.example.group26.coupletones;

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
public class ListOfPlaces {

    private Firebase myFireBaseRef;
    final List<aFavoritePlace> favoritePlaceList;
    String spouseID;

    // Initialization for favorite place list class
    ListOfPlaces (Spouse mySpouse) {

        // TODO Initialize list of favorite places
        favoritePlaceList = new ArrayList<aFavoritePlace>();

        this.spouseID = mySpouse.spouseID;

        myFireBaseRef = new Firebase ("https://coupletonescse100.firebaseio.com");

        myFireBaseRef = myFireBaseRef.child("users").child(spouseID).child("favPlaces");

        myFireBaseRef.addChildEventListener(new ChildEventListener() {

            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {

                aFavoritePlace temp = new aFavoritePlace(dataSnapshot.child("name").getValue().toString(),
                                                         (double) dataSnapshot.child("latitude").getValue(),
                                                         (double) dataSnapshot.child("longitude").getValue(),
                                                         (boolean) dataSnapshot.child("visted").getValue());

                favoritePlaceList.add(temp);
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

    public List<aFavoritePlace> getFavoritesList(){
        return this.favoritePlaceList;
    }
}
