package com.example.group26.coupletones;
import android.location.Location;

import java.util.*; // for HashSet

import com.google.android.gms.maps.*;

import static android.location.Location.distanceBetween;

/**
 * Created by jkapi on 5/5/2016.
 */

public class VisitFavoritesCalculator {
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
     * a mile, then //TODO S.O. should be notified
     */
    public aFavoritePlace calculateVisited (aFavoritePlace currentLocation,
                                               HashSet<aFavoritePlace> favoriteLocations) {
        aFavoritePlace visited = null;
        aFavoritePlace temp = null;
        Iterator itr = favoriteLocations.iterator();

        while (itr.hasNext()) {
            temp = (aFavoritePlace) itr.next();
            if (distanceBetweenTwoPlaces(temp, currentLocation) <= MIN_DIST) {
                visited = temp;
                break;
            }
            //check locations
        }

        return visited;

    }


}
