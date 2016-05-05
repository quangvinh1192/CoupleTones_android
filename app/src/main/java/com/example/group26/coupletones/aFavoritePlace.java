package com.example.group26.coupletones;

import com.google.android.gms.maps.model.LatLng;

/**
 * Created by trungdo on 5/4/16.
 */
public class aFavoritePlace {
    private LatLng position;
    private String name;
    private boolean visited;
    public aFavoritePlace(){

    }
    public aFavoritePlace(LatLng pos,String inpName, boolean inpVisited){
        position = pos;
        name = inpName;
        visited = inpVisited;
    }
    public LatLng getPosition(){
        return position;
    }
    public String getName(){
        return name;
    }
    public  boolean isVisited(){
        return visited;
    }


}
