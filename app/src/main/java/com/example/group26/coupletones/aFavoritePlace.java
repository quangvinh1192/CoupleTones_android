package com.example.group26.coupletones;

import android.widget.EditText;

import com.firebase.client.AuthData;
import com.firebase.client.Firebase;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;

import java.io.Serializable;

/**
 * Object for each favorite place
 */
public class aFavoritePlace implements Serializable {
    private String name;
    private double latitude;
    private double longitude;
    private boolean visited;
    private String nameOfPlace;

    public aFavoritePlace(){

    }



    /**
     * constructor
     * @param inpName
     * @param inpLatitude
     * @param inpLongitude
     * @param inpVisited
     */
    public aFavoritePlace(String inpName, double inpLatitude, double inpLongitude, boolean inpVisited){
        latitude = inpLatitude;
        longitude = inpLongitude;
        name = inpName;
        visited = inpVisited;
    }

    /**
     * Name: getLatitude
     * @return double
     * returns latitude
     */
    public double getLatitude(){
        return latitude;
    }

    /**
     * Name:getLongitude
     * @return longitude
     */
    public double getLongitude(){
        return longitude;
    }

    /**
     * Name: getName()
     * returns name of object
     */
    public String getName(){
        return name;
    }

    /**
     * isVisited
     * @return visited boolean
     */
    public  boolean isVisited(){
        return visited;
    }


    /** name: add placeToServer
     * adds a favoriteplace object ot server and hashmap
     * PRECONDITION: NAME EXISTS
     */
    public void addPlaceToServer(String nameOfPlace, Firebase myFirebaseRef, Marker temporaryMarker) {

        AuthData authData = myFirebaseRef.getAuth();
        String userId = authData.getUid();
        Firebase temp = myFirebaseRef.child("users").child(userId).child("favPlaces");

        double lat = temporaryMarker.getPosition().latitude;
        double longitude = temporaryMarker.getPosition().longitude;
        aFavoritePlace newPlaceToAdd = new aFavoritePlace(nameOfPlace, lat, longitude, false);
        temp.push().setValue(newPlaceToAdd);

    }


}
