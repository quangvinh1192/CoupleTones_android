package com.example.group26.coupletones;

import com.google.android.gms.maps.model.LatLng;

/**
 * Object for each favorite place
 */
public class aFavoritePlace {
    private String name;
    private double latitude;
    private double longitude;
    private boolean visited;
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


}
