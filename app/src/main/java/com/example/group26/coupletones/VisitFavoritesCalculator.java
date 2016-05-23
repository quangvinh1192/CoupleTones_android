package com.example.group26.coupletones;
import android.location.Location;
import android.util.Log;

import java.util.*; // for HashSet

import com.google.android.gms.maps.*;

import static android.location.Location.distanceBetween;

/**
 * Created by jkapi on 5/5/2016.
 */

/**
 * Name: VisitFavoritesCalculator
 * calculates distance between two places
 */
public class    VisitFavoritesCalculator {
    float[] results = new float[3];
    private final double MIN_DIST = 160; // 160m = 1/10th mile

    /** Name: distanceBetweenTwoPlaces
     *
     * @param p1
     * @param ourLocation
     * @return double that is the distance between the two locations in meters
     */
    public double distanceBetweenTwoPlaces(aFavoritePlace ourLocation,aFavoritePlace p1){
         double distance = MIN_DIST + 1;
        Log.i("Place Lat",String.valueOf(p1.getLatitude()));
        Log.i("Place Long",String.valueOf(p1.getLongitude()));
        Log.i("Our Lat",String.valueOf(ourLocation.getLatitude()));
        Log.i("Our Long",String.valueOf(ourLocation.getLongitude()));


        distanceBetween(p1.getLatitude(), p1.getLongitude(), ourLocation.getLatitude(),
                                        ourLocation.getLongitude(), results);
        distance = results[0];
        return distance;
    }


    /** name: calculateVisited
     * Parameters:
     *      aFavoritePlace currentLocation- current location based on GPS
     *      HashSet<aFavoritePlace> favoriteLocations - a hashset of all the favoriteLocations
     * Returns: Null if none are visited or aFavoritePlace that has been visited
     * this constantly calculates the distance between current location and favorites location to
     * check if a favorites location has been visited. If the current location is within 1/10th of
     * a mile, then
     */
    public aFavoritePlace calculateVisited (aFavoritePlace currentLocation,
                                               HashMap<String, aFavoritePlace> favoriteLocations) {
        aFavoritePlace visited = null;
        aFavoritePlace temp = null;
        Set setOfPlaces = favoriteLocations.entrySet();
        Iterator itr = setOfPlaces.iterator();
        Log.i("MyApp","CHECKING POSITION");

        //GO through all hashmap and check every entry
        while (itr.hasNext()) {
            Map.Entry me = (Map.Entry)itr.next();
            temp = (aFavoritePlace) me.getValue();
            double a = distanceBetweenTwoPlaces(temp, currentLocation);
            Log.i("Distance",String.valueOf(a));


            if (distanceBetweenTwoPlaces(temp, currentLocation) <= MIN_DIST) {
                Log.i("MyApp",temp.getName() );
                visited = temp;
                break;
            };
            //check locations
        }

        return visited;

    }


}
