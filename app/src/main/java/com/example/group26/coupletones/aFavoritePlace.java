package com.example.group26.coupletones;

import com.google.android.gms.maps.model.LatLng;

/**
 * Created by trungdo on 5/4/16.
 */
public class aFavoritePlace {
    private String name;
    private double latitude;
    private double longitude;
    private boolean visited;
    public aFavoritePlace(){

    }
    public aFavoritePlace(String inpName,double inpLatitude, double inpLongitude, boolean inpVisited){
        latitude = inpLatitude;
        longitude = inpLongitude;
        name = inpName;
        visited = inpVisited;
    }
    public double getLatitude(){ return latitude; }
    public double getLongitude(){
        return longitude;
    }
    public String getName(){
        return name;
    }
    public  boolean isVisited(){
        return visited;
    }


}
