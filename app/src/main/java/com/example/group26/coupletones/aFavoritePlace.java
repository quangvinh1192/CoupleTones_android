package com.example.group26.coupletones;

import android.widget.EditText;

import com.firebase.client.AuthData;
import com.firebase.client.Firebase;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;

/**
 * Object for each favorite place
 */
public class aFavoritePlace {
    private String name;
    private double latitude;
    private double longitude;
    private boolean visited;
    private String nameOfPlace;
    private String uniqueVibration;
    private String uniqueSound;

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

    //TODO
    public void playUniqueVibration(){

    }

    //TODO
    public void playUniqueSound(){

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
