package com.example.group26.coupletones;

import android.Manifest;
import android.bluetooth.le.AdvertiseData;
<<<<<<< HEAD
import android.content.pm.PackageManager;
=======
import android.content.Intent;
>>>>>>> c2ea581ac4b92286253487b90eabde2e56731c09
import android.gesture.GestureOverlayView;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
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
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.HashMap;
import java.util.HashSet;

public class favMapPage extends FragmentActivity implements OnMapReadyCallback    {

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
    private HashSet<aFavoritePlace> favoriteLocations;
    protected LocationManager locationManager;
    private static final long LOCATION_REFRESH_TIME = 30;
    private static final float LOCATION_REFRESH_DISTANCE = 20;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        favoriteLocations = new HashSet<aFavoritePlace>();

        // Create map using existing map instance state from Firebase
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fav_map);

        // Firebase server location
        String id = "https://coupletonescse100.firebaseio.com";
        myFirebaseRef = new Firebase(id);

        // Debug comment
        Log.d("MyApp",id);



        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        // Buttons for adding location or cancelling an add call
        addPlaceBtn = (Button)findViewById(R.id.addPlaceBtnID);
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
                }
                else{
                    addingMode = false;
                    addPlaceBtn.setText("Add Favorite Place");

                }
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
//        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
        LocationListener mLocationListener = new LocationListener() {
            @Override
            public void onLocationChanged(final Location location) {
                //your code here
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
                LatLng favPoint = new LatLng(tempClass.getLatitude(), tempClass.getLongitude());

                mMap.addMarker(new MarkerOptions().position(favPoint).title(tempClass.getName()).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE)));
                ;
                //TODO TEST if this is correctly adding

                favoriteLocations.add(tempClass);

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
        aFavoritePlace newPlaceToAdd = new aFavoritePlace(nameOfPlace, lat, longitude,false);
        temp.push().setValue(newPlaceToAdd);

        //TODO IF TRACK WHILE OFFLINE, ADD PLACE TO HASHSET
    }
}
