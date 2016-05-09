package com.example.group26.coupletones;

import android.Manifest;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.bluetooth.le.AdvertiseData;
import android.content.Context;
import android.content.pm.PackageManager;
import android.content.Intent;
import android.gesture.GestureOverlayView;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v4.app.TaskStackBuilder;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.NotificationCompat;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;

import com.firebase.client.AuthData;
import com.firebase.client.ChildEventListener;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;
import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.Places;
import com.google.android.gms.location.places.ui.PlaceAutocomplete;
import com.google.android.gms.location.places.ui.PlaceAutocompleteFragment;
import com.google.android.gms.location.places.ui.PlaceSelectionListener;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.GoogleApiClient.OnConnectionFailedListener;
import java.util.HashMap;
import java.util.HashSet;

public class favMapPage extends FragmentActivity implements OnMapReadyCallback, OnConnectionFailedListener    {

    private GoogleMap mMap;
    private Button addPlaceBtn;
    private boolean addingMode;
    private RelativeLayout addingView;
    private Button confirmAddBtn;
    private Button cancelAddBtn;
    private Button spouseOptionsBtn;
    private Marker temporaryMarker;
    private Firebase myFirebaseRef;
    private String nameOfPlace;
    private HashMap<String, aFavoritePlace> favoriteLocations;
    private GoogleApiClient mGoogleApiClient;
    private NotificationCompat.Builder mBuilder;

    protected LocationManager locationManager;

    private static final long LOCATION_REFRESH_TIME = 30;
    private static final float LOCATION_REFRESH_DISTANCE = 20;
    private static final int PLACE_AUTOCOMPLETE_REQUEST_CODE = 1;



    private PushPullMediator mediator;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        favoriteLocations = new HashMap<String, aFavoritePlace>();
        mediator = new PushPullMediator();

        // Create map using existing map instance state from Firebase
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fav_map);

        // Firebase server location
        myFirebaseRef = new Firebase("https://coupletonescse100.firebaseio.com");

        // ATTENTION: This "addApi(AppIndex.API)"was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        mGoogleApiClient = new GoogleApiClient
                .Builder(this)
                .addApi(Places.GEO_DATA_API)
                .addApi(Places.PLACE_DETECTION_API)
                .enableAutoManage(this, this)
                .addApi(AppIndex.API).build();


        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        // Buttons for adding location or cancelling an add call
        addPlaceBtn = (Button) findViewById(R.id.addPlaceBtnID);
        confirmAddBtn = (Button) findViewById(R.id.confirmAddID);
        cancelAddBtn = (Button) findViewById(R.id.cancelAddID);
        spouseOptionsBtn = (Button) findViewById(R.id.spouseOptions);

        // Setup display for adding mode
        addingView = (RelativeLayout) findViewById(R.id.addingPlaceViewID);
        addingMode = false;
        addingView.setVisibility(View.INVISIBLE);
        addPlaceBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Perform action on click
                String buttonText = addPlaceBtn.getText().toString();

                if (buttonText != "Cancel") {
                    Log.d("MyApp", "I am here");
                    addingMode = true;
                    addPlaceBtn.setText("Cancel");
                } else {
                    addingMode = false;
                    addPlaceBtn.setText("Add Favorite Place");

                }
                addingSearchingPlace();

            }
        });

        // Sends favorite location to server
        confirmAddBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Perform action on click
                addingView.setVisibility(View.INVISIBLE);
                temporaryMarker.setTitle(nameOfPlace);
                addPlaceToServer();
            }
        });
        cancelAddBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Perform action on click
                addingView.setVisibility(View.INVISIBLE);
                temporaryMarker.remove();
            }
        });

        spouseOptionsBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //Peform action on click
                startActivity(new Intent(favMapPage.this, AddSpousePage.class));
            }

        });
        mBuilder = new NotificationCompat.Builder(this);
    };


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;


        // Add zoom feature
        mMap.getUiSettings().setZoomControlsEnabled(true);
        showYourFavMap();
        listenToSpouseFavPlaces();

//        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
        LocationListener mLocationListener = new LocationListener() {
            @Override
            public void onLocationChanged(final Location location) {
                aFavoritePlace currentLocation = new aFavoritePlace("Current Location",
                        location.getLatitude(), location.getLongitude(), true);
                if (mediator.checkToSend(currentLocation, favoriteLocations)) {
                    aFavoritePlace temp = mediator.getVisited();
                    if (temp != null){
                        mediator.updateVisitedPlaceFirebase(temp.getName());
                    }
                    else{
                        Log.i("My App","check visited place null");
                    }
                };
            }
            @Override
            public void onStatusChanged(String provider, int status, Bundle extras){

            }
            @Override
            public void onProviderEnabled(String provider){

            }

            @Override
            public void onProviderDisabled(String provider){

            }

        };

        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);

        mMap.setOnMapLongClickListener(new GoogleMap.OnMapLongClickListener() {
            @Override
            public void onMapLongClick(LatLng point) {
                if (addingMode) {
                    addingView.setVisibility(View.VISIBLE);
                    //temporaryMarker.position(point);
                    temporaryMarker = mMap.addMarker(new MarkerOptions().position(point).title(""));
                }
            }

        });
        try {
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, LOCATION_REFRESH_TIME,
                    LOCATION_REFRESH_DISTANCE, mLocationListener);
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                    == PackageManager.PERMISSION_GRANTED) {
                mMap.setMyLocationEnabled(true);
            } else {
                // Show rationale and request permission.
            }
        }
        catch (SecurityException e){
            Log.d("MyApp", "User not allow us to see our location   ");

        }


    }

    public void showYourFavMap(){
        AuthData authData = myFirebaseRef.getAuth();
        String userId = authData.getUid();
        final Firebase tempRef = myFirebaseRef.child("users").child(userId).child("favPlaces");

        tempRef.addChildEventListener(new ChildEventListener() {
            // Retrieve new posts as they are added to the database
            @Override
            public void onChildAdded(DataSnapshot snapshot, String previousChildKey) {
                String temp = snapshot.getKey();
                aFavoritePlace tempClass = snapshot.getValue(aFavoritePlace.class);
                Log.e("Count ", tempClass.getLatitude() + "");
                Log.e("Count ", tempClass.getLongitude() + "");
                Log.e("Count ", "HAHAHA");

                LatLng favPoint = new LatLng(tempClass.getLatitude(), tempClass.getLongitude());
                mMap.addMarker(new MarkerOptions().position(favPoint).title(tempClass.getName()).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE)));
                ;
                //TODO TEST if this is correctly adding

                favoriteLocations.put(tempClass.getName(), tempClass);

//                for (DataSnapshot postSnapshot: snapshot.getChildren()) {
//                    <YourClass> post = postSnapshot.getValue(<YourClass>.class);
//                    Log.e("Get Data", post.<YourMethod>());
//                }
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
        mMap.setPadding(0, 96, 0, 0);
    }

    public void addPlaceToServer(){

        nameOfPlace = ((EditText) findViewById(R.id.placeName)).getText().toString();
        AuthData authData = myFirebaseRef.getAuth();
        String userId = authData.getUid();
        Firebase temp = myFirebaseRef.child("users").child(userId).child("favPlaces");

        if (nameOfPlace.isEmpty()) {

            //TODO Create popup so user doesn't forget to put in name
            nameOfPlace = "Haha no string given";
        }
        double lat = temporaryMarker.getPosition().latitude;
        double longitude = temporaryMarker.getPosition().longitude;
        aFavoritePlace newPlaceToAdd = new aFavoritePlace(nameOfPlace, lat, longitude, false);
        temp.push().setValue(newPlaceToAdd);

        //TODO IF TRACK WHILE OFFLINE, ADD PLACE TO HASHSET
    }


    void addingSearchingPlace(){
        PlaceAutocompleteFragment autocompleteFragment = (PlaceAutocompleteFragment)
                getFragmentManager().findFragmentById(R.id.place_autocomplete_fragment);

        autocompleteFragment.setOnPlaceSelectedListener(new PlaceSelectionListener() {
            @Override
            public void onPlaceSelected(Place place) {
                // TODO: Get info about the selected place.
                Log.i("haha", "Place: " + place.getName());
            }

            @Override
            public void onError(Status status) {
                // TODO: Handle the error.
                Log.i("haha", "An error occurred: " + status);
            }
        });

        try {
            Intent intent =
                    new PlaceAutocomplete.IntentBuilder(PlaceAutocomplete.MODE_OVERLAY)
                            .build(this);
            startActivityForResult(intent, PLACE_AUTOCOMPLETE_REQUEST_CODE);
        } catch (GooglePlayServicesRepairableException e) {
            // TODO: Handle the error.
        } catch (GooglePlayServicesNotAvailableException e) {
            // TODO: Handle the error.
        }

    }
    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {

    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == PLACE_AUTOCOMPLETE_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                Place place = PlaceAutocomplete.getPlace(this, data);
                Log.i("HAHA", "Place: " + place.getName());
                LatLng position = place.getLatLng();

                mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(position,10) );


            } else if (resultCode == PlaceAutocomplete.RESULT_ERROR) {
                Status status = PlaceAutocomplete.getStatus(this, data);
                // TODO: Handle the error.
                Log.i("HAHA", status.getStatusMessage());

            } else if (resultCode == RESULT_CANCELED) {
                // The user canceled the operation.
            }
        }
    }

    @Override
    public void onStart() {
        super.onStart();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        mGoogleApiClient.connect();
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "favMapPage Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app deep link URI is correct.
                Uri.parse("android-app://com.example.group26.coupletones/http/host/path")
        );
        AppIndex.AppIndexApi.start(mGoogleApiClient, viewAction);
    }

    @Override
    public void onStop() {
        super.onStop();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "favMapPage Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app deep link URI is correct.
                Uri.parse("android-app://com.example.group26.coupletones/http/host/path")
        );
        AppIndex.AppIndexApi.end(mGoogleApiClient, viewAction);
        mGoogleApiClient.disconnect();
    }

    /** Name: getCurrentLocation()
     * TODO revamp this
     * @param location
     * @return
     */
    public aFavoritePlace getCurrentLocation(Location location) {
        double currentLatitude = location.getLatitude();
        double currentLongitude = location.getLongitude();
        aFavoritePlace currentLocation = new aFavoritePlace("Current Location",
                                        currentLatitude, currentLongitude, true);
        return currentLocation;
    }
    void createANotification(String title){
        mBuilder.setSmallIcon(R.drawable.notifications_icon);
        mBuilder.setContentTitle("Your spouse just visited a new place");
        String welcomeText = "Your S/O just visited " + title;
        mBuilder.setContentText(welcomeText);
        mBuilder.build();

        NotificationManager mNotificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
// notificationID allows you to update the notification later on.
        mNotificationManager.notify(1, mBuilder.build());
    }
    void listenToSpouseFavPlaces() {
        AuthData authData = myFirebaseRef.getAuth();
        String userId = authData.getUid();
        final Firebase tempRef = myFirebaseRef.child("users").child(userId);
        tempRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                if (snapshot.child("spouseUID").exists()){
                    Log.d("MyApp", snapshot.child("spouseUID").getValue().toString());
                    String temp = snapshot.child("spouseUID").getValue().toString();
                    createAListenerToSpouseFavPlaces(temp);
                }
                else{
                    Log.d("MyApp", "You have no spouse");
                }

            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {
                System.out.println("The read failed: " + firebaseError.getMessage());
            }
        });
    }

    void createAListenerToSpouseFavPlaces(String spouseID){

        Log.d("I WANT TO SEE", spouseID);

        if (!spouseID.equals("")) {
            Log.i("HAHA", "CHECK Your SPOUSE FAV Places");
            final Firebase spouseRef = myFirebaseRef.child("users").child(spouseID).child("favPlaces");
            spouseRef.addChildEventListener(new ChildEventListener() {
                // Retrieve new posts as they are added to the database
                @Override
                public void onChildAdded(DataSnapshot snapshot, String previousChildKey) {

                }

                @Override
                public void onChildChanged(DataSnapshot snapshot, String previousChildKey) {
                    String title = (String) snapshot.child("name").getValue();
                    Log.e("Count ", title);

                    createANotification(title);
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


}
