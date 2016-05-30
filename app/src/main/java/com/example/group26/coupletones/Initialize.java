package com.example.group26.coupletones;

import android.Manifest;
import android.app.Application;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.util.Log;

import com.firebase.client.AuthData;
import com.firebase.client.ChildEventListener;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase; // for myFirebaseRef
import com.firebase.client.FirebaseError;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.HashMap;


/**
 * To use this global class/object, do:
 * ((Initialize) this.getApplication()).whatevermethodyouneed();
 */
public class Initialize extends android.app.Application {
    private static Initialize application;
    private Spouse spouse;
    private Firebase myFirebaseRef;
    private Context context;
    private LocationManager locationManager;
    private HashMap<String, aFavoritePlace> favoriteLocations;
    private PushPullMediator mediator;
    private long minTime = 0; //5 minutes  = 5 * 60 * 1000;
    private long minDistance = 20;



    public Initialize() {
        application = this;
        mediator = new PushPullMediator(); //checks to see if anything needs to be pushed
        favoriteLocations = new HashMap<String, aFavoritePlace>();
    }

    /** Name: setFirebase
     * sets up Firebase variable so that it is no longer null
     * @param appcontext
     * @return boolean that is true if firebase was set
     */
    public boolean setFirebase(Context appcontext) {
        context = appcontext;
        Firebase.setAndroidContext(appcontext);
        myFirebaseRef = new Firebase("https://coupletonescse100.firebaseio.com");
        mediator.setMyFirebaseRef(myFirebaseRef);
        //null check
        if (myFirebaseRef == null) {
            return false;
        }
        return true;
    }

    /**Name:
     * This method initializes spouse and sets its listeners if firebase is running
     * @return
     */
    public boolean setSpouse() {
        spouse = new Spouse(); // automatically starts to listen
        if (myFirebaseRef != null) {
            Log.d("Initialize", "setSpouse");
            spouse.setMyFirebaseRef(myFirebaseRef);
            spouse.listenToSpouseFavPlaces();
            return true;
        } else {
            //throw error
            Log.d("Initialize", "No Firebase for spouse to come from. use setFirebase");
            return false;
        }
    }


    public void startListeningToMyself() {
        //null check
        Log.d("Initialize", "startListeningToMyself");
        if (myFirebaseRef == null) {
            Log.d ("Initialize", "startListening: cannot update firebase");
        }
        // null check
        if(mediator.getMyFirebaseRef() == null) {
            Log.d("Initialize", "startlistening: mediator Firebase was null");
            setFirebase(context);
            mediator.setMyFirebaseRef(myFirebaseRef);
        }
        //calls on the hashmap that holds the favorites
        updateHashMap();

        // Acquire a reference to the system Location Manager
        locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);

        // Define a listener that responds to location updates
        LocationListener locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                Log.d("Initialize", "Location changed");
                //listens to the current location to see if there is a change. Calls mediator to
                //check if Firebase needs to be updated, saying that we visited a page
                aFavoritePlace currentLocation = new aFavoritePlace("Current Location",
                        location.getLatitude(), location.getLongitude(), true);
                if (mediator.checkToSend(currentLocation, favoriteLocations)) {
                    aFavoritePlace temp = mediator.getVisited();
                    // if we visited a place, update that place in firebase
                    if (temp != null){
                        mediator.updateVisitedPlaceFirebase(temp.getName());
                    }

                } else {
                    mediator.updateVisitedPlaceFirebase("YOU-ARE-NOT-VISITING-ANY-PLACE");
                }
            }
            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {
            }
            @Override
            public void onProviderEnabled(String provider) {
            }
            @Override
            public void onProviderDisabled(String provider) {
            }
        };

        // Register the listener with the Location Manager to receive location updates
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            Log.d("INITIALIZE", "NEED PERMISSION FOR GPS");
            return;
        }
        Log.d("INITIALIZE", "GOT PERMISSION FOR GPS");
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, minTime, minDistance, locationListener);
    }

    /** Name: getSpouse()
     *  returns the spouse that was initialized
     * @return Spouse
     */
    public Spouse getSpouse() {
        return spouse;
    }

    /** Name: getFirebase
     * returns the firebase that was initialized
     * @return Firebase
     */
    public Firebase getFirebase() {
        return myFirebaseRef;
    }

    public void addFavoriteLocation(String name, aFavoritePlace newFavoritePlace) {
        Log.d("Initialize", "addFavoriteLocation");
        favoriteLocations.put(name, newFavoritePlace);
    }

   public void updateHashMap() {
       Log.d("INITIALIZE", "UpdateHashMap");
       AuthData authData = myFirebaseRef.getAuth();
       String userId = authData.getUid();
       final Firebase tempRef = myFirebaseRef.child("users").child(userId).child("favPlaces");

       tempRef.addChildEventListener(new ChildEventListener() {
           // Retrieve new posts as they are added to the database
           @Override
           public void onChildAdded(DataSnapshot snapshot, String previousChildKey) {
               String temp = snapshot.getKey();
               aFavoritePlace tempClass = snapshot.getValue(aFavoritePlace.class);

               //TODO TEST if this is correctly adding

               //adds the new favorite class to the hashmap
               addFavoriteLocation(tempClass.getName(), tempClass);

           }

           @Override
           public void onChildChanged(DataSnapshot snapshot, String previousChildKey) {
           }

           @Override
           public void onChildRemoved(DataSnapshot snapshot) {
           }

           @Override
           public void onChildMoved(DataSnapshot snapshot, String previousChildKey) {
           }

           @Override
           public void onCancelled(FirebaseError e) {
           }
           //... ChildEventListener also defines onChildChanged, onChildRemoved,
           //    onChildMoved and onCanceled, covered in later sections.
       });

   }


}
