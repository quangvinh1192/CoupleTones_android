package com.example.group26.coupletones;

import java.util.HashSet;

/**
 * Created by jkapi on 5/4/2016.
 * This class checks to see whether to push changes to Firebase and whether any information that is
 * passed in should cause a notification.
 */
public class PushPullMediator {
    boolean imOnline;
    boolean spouseOnline;
    aFavoritePlace visited;

    //FirebaseApp firebase = Firebase.getApp();

    public boolean checkToSend(aFavoritePlace currentLocation, HashSet<aFavoritePlace> favoriteLocations) {
        VisitFavoritesCalculator calculator = new VisitFavoritesCalculator();
        aFavoritePlace newlyVisited = calculator.calculateVisited(currentLocation, favoriteLocations);

        // if we are visiting a favorited location and we haven't already sent a push notification, send one
        if (visited != newlyVisited && newlyVisited != null) {
            visited = newlyVisited;

            //TODO check if spouse is online
            //send push notification
            return true;
        }
        return false;
    }

}
