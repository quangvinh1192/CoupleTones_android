package com.example.group26.coupletones;

import com.firebase.client.AuthData;
import com.firebase.client.Firebase;
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
    private long arrived;
    private long departure;

    public aFavoritePlace(){

    }

    /** constructor for the visited list */
    public aFavoritePlace(String nameOfPlace, long arrivalTime, long departureTime ) {
        this.name = nameOfPlace;
        this.arrived = arrivalTime;
        this.departure = departureTime;
    }
    public aFavoritePlace(String inpName, double inpLatitude, double inpLongitude){
        name= inpName;
        latitude = inpLatitude;
        longitude = inpLongitude;
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
        //get authority from firebase
        AuthData authData = myFirebaseRef.getAuth();
        String userId = authData.getUid();
        Firebase temp = myFirebaseRef.child("users").child(userId).child("favPlaces");

        //create and push marker
        double lat = temporaryMarker.getPosition().latitude;
        double longitude = temporaryMarker.getPosition().longitude;
        aFavoritePlace newPlaceToAdd = new aFavoritePlace(nameOfPlace, lat, longitude, false);
        temp.push().setValue(newPlaceToAdd);

    }

    /** Name: removeFromServer
     * Removes a place from the server
     * @param myFirebaseRef
     * @param id: name of the place to remove
     */
    public void removeFromServer(Firebase myFirebaseRef,String id){
        //get authority from firebase
        AuthData authData = myFirebaseRef.getAuth();
        String userId = authData.getUid();
        
        //remove from firebase
        Firebase temp = myFirebaseRef.child("users").child(userId).child("favPlaces").child(id);
        temp.removeValue();
    }
    /** Name: editFromServer
     * Removes a place from the server
     * @param myFirebaseRef
     * @param id: name of the place to editFromServer
     */
    public void editFromServer(Firebase myFirebaseRef,String id, String newName){
        //get authority from firebase
        AuthData authData = myFirebaseRef.getAuth();
        String userId = authData.getUid();
        this.name = newName;


        //remove from firebase
        Firebase temp = myFirebaseRef.child("users").child(userId).child("favPlaces").child(id);
        temp.removeValue();
        temp = myFirebaseRef.child("users").child(userId).child("favPlaces");
        temp.push().setValue(this);

    }

}
