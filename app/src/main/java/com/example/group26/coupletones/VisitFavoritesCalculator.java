package com.example.group26.coupletones;
import java.util.*; // for HashSet

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

/**
 * Created by jkapi on 5/5/2016.
 */
public class VisitFavoritesCalculator {

    //TODO when logging in to firebase, if this hashSet is empty, check to see if there is info in

    // firebase and pull that to add it to the local favoriteLocations
    private HashSet<aFavoritePlace> favoriteLocations;//TODO check to see if this is accurate with firebase
    private aFavoritePlace currentLocation;

    /** this constantly calculates the distance between current location and favorites location to
     * check if a favorites location has been visited. If the current location is within 1/10th of
     * a mile, then //TODO S.O. should be notified
     */
    Iterator itr;
    public aFavoritePlace constantlyCalculate (aFavoritePlace currentLocation) {
        aFavoritePlace visited = null;
        aFavoritePlace temp = null;
        itr = favoriteLocations.iterator();

        while (itr.hasNext()) {
            temp = (aFavoritePlace) itr.next();
            if (temp.lat() == currentLocation.lat()) {
                visited = temp;
                break;
            }
            //check locations
        }

        return visited;

    }


}
