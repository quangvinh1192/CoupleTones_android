package com.example.group26.coupletones;

import android.Manifest;
import android.app.Application;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.client.AuthData;
import com.firebase.client.ChildEventListener;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.GoogleApiClient.OnConnectionFailedListener;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.Places;
import com.google.android.gms.location.places.ui.PlaceAutocomplete;
import com.google.android.gms.location.places.ui.PlaceAutocompleteFragment;
import com.google.android.gms.location.places.ui.PlaceSelectionListener;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.HashMap;


public class favMapPage extends FragmentActivity implements OnMapReadyCallback, OnConnectionFailedListener    {
    private GoogleMap mMap;
    private Button addPlaceBtn;
    private boolean addingMode;
    private boolean removeMode;
    private boolean editMode;
    private RelativeLayout addingView;
    private Button confirmAddBtn;
    private Button cancelAddBtn;
    private Button editBtn;
    private Button removeBtn;
    private Marker temporaryMarker;
    private Firebase myFirebaseRef;
    private GoogleApiClient mGoogleApiClient;
    protected LocationManager locationManager;
    private Application app;

    private static final long LOCATION_REFRESH_TIME = 30;
    private static final float LOCATION_REFRESH_DISTANCE = 20;
    private static final int PLACE_AUTOCOMPLETE_REQUEST_CODE = 1;




    /** creates the map and initializes favorite places and buttons
     *
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        app = this.getApplication();
        if (app == null) {
            Log.d("favMapPage", "favMap cannot get app instance");
        }

        // Create map using existing map instance state from Firebase
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fav_map);

        // Firebase server location
        myFirebaseRef = ((Initialize)this.getApplication()).getFirebase();

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
        removeBtn = (Button) findViewById(R.id.removeBtn);
        removeBtn.setVisibility(View.INVISIBLE);
        editBtn = (Button) findViewById(R.id.editBtn);
        editBtn.setVisibility(View.INVISIBLE);

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
                    addingSearchingPlace();

                } else {
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
                removeBtn.setVisibility(View.INVISIBLE);
                editBtn.setVisibility(View.INVISIBLE);
                addPlaceBtn.setVisibility(View.VISIBLE);
                if (removeMode && !addingMode) {
                    aFavoritePlace oldPlace = new aFavoritePlace(temporaryMarker.getTitle(),temporaryMarker.getPosition().latitude,temporaryMarker.getPosition().longitude);

                    oldPlace.removeFromServer(myFirebaseRef,temporaryMarker.getSnippet());
                    temporaryMarker.remove();
                    removeMode = false;
                }
                if (editMode){
                    EditText nameText = (EditText) findViewById(R.id.placeName);
                    String newName = nameText.getText().toString();
                    HashMap<String, aFavoritePlace> tempHashMap = ((Initialize) app).getFavoriteLocations();
                    aFavoritePlace editPlace  = tempHashMap.get(temporaryMarker.getTitle());
                    editPlace.editFromServer(myFirebaseRef,temporaryMarker.getSnippet(),newName);
                    editMode = false;
                    temporaryMarker.setTitle(newName);


                }
                //adding markers
                if (addingMode) {
                    String nameOfPlace = ((EditText) findViewById(R.id.placeName)).getText().toString();
                    if (nameOfPlace.isEmpty()) {
                        Toast.makeText(favMapPage.this, "Please enter a name for this place.",
                                Toast.LENGTH_SHORT).show();
                    } else {
                        temporaryMarker.setTitle(nameOfPlace);
                        aFavoritePlace newPlace = new aFavoritePlace();
                        newPlace.addPlaceToServer(nameOfPlace, myFirebaseRef, temporaryMarker);
                    }
                }
            }
        });
        cancelAddBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Perform action on click
                addingView.setVisibility(View.INVISIBLE);
                removeBtn.setVisibility(View.INVISIBLE);
                addPlaceBtn.setVisibility(View.VISIBLE);
                editBtn.setVisibility(View.INVISIBLE);
                removeMode = false;
                editMode = false;
            }
        });
        editBtn.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                addingView.setVisibility(View.VISIBLE);
                editMode = true;
                addPlaceBtn.setVisibility(View.INVISIBLE);

            }
        });
        removeBtn.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                addingView.setVisibility(View.VISIBLE);
                addPlaceBtn.setVisibility(View.INVISIBLE);

                EditText edit = (EditText) findViewById(R.id.placeName);
                edit.setVisibility(addingView.INVISIBLE);
                TextView a = (TextView) findViewById(R.id.addingTitle);
                a.setVisibility(addingView.INVISIBLE);
                removeMode = true;
            }
        });

    }


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

        LocationListener mLocationListener = new LocationListener() {
            @Override
            public void onLocationChanged(final Location location) {}
            @Override
            public void onStatusChanged(String provider, int status, Bundle extras){}
            @Override
            public void onProviderEnabled(String provider){}
            @Override
            public void onProviderDisabled(String provider){}

        };

        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        mMap.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {

            @Override
            public void onInfoWindowClick(Marker arg0) {
                temporaryMarker = arg0;
                removeBtn.setVisibility(View.VISIBLE);
                editBtn.setVisibility(View.VISIBLE);
                addPlaceBtn.setVisibility(View.INVISIBLE);
                Log.i("Maps", "TAP TO A MARKER");
            }
        });
        mMap.setOnMapClickListener(new GoogleMap.OnMapClickListener(){
            @Override
            public void onMapClick(LatLng point){
                removeBtn.setVisibility(View.INVISIBLE);
                editBtn.setVisibility(View.INVISIBLE);
                addPlaceBtn.setVisibility(View.VISIBLE);
            }
            });
        mMap.setOnMapLongClickListener(new GoogleMap.OnMapLongClickListener() {
            @Override
            public void onMapLongClick(LatLng point) {
                // allow for adding a marker
                if (addingMode) {
                    addingView.setVisibility(View.VISIBLE);
                    EditText edit = (EditText) findViewById(R.id.placeName);
                    edit.setVisibility(addingView.VISIBLE);
                    TextView a = (TextView) findViewById(R.id.addingTitle);
                    a.setVisibility(addingView.VISIBLE);
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


    /** name:showYourFavMap()
     * shows all your favorite places as pins on the map
     */
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
                LatLng favPoint = new LatLng(tempClass.getLatitude(), tempClass.getLongitude());
                mMap.addMarker(new MarkerOptions().position(favPoint).title(tempClass.getName()).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE)).snippet(temp));

            }

            @Override
            public void onChildChanged(DataSnapshot snapshot, String previousChildKey) {}
            @Override
            public void onChildRemoved(DataSnapshot snapshot) {}
            @Override
            public void onChildMoved(DataSnapshot snapshot, String previousChildKey) {}
            @Override
            public void onCancelled(FirebaseError e) {}

        });
        mMap.setPadding(0, 96, 0, 0);
    }


    /** Name: addingSearchingPlace()
     * allow for the search function on google maps
     */
    void addingSearchingPlace(){
        PlaceAutocompleteFragment autocompleteFragment = (PlaceAutocompleteFragment)
                getFragmentManager().findFragmentById(R.id.place_autocomplete_fragment);

        autocompleteFragment.setOnPlaceSelectedListener(new PlaceSelectionListener() {
            @Override
            public void onPlaceSelected(Place place) {
                // TODO: Get info about the selected place.
                Log.i("favMapPage", "addingSearchingPlace: Place: " + place.getName());
            }

            @Override
            public void onError(Status status) {
                Log.i("favMapPage", "addingSearchingPlaec: An error occurred: " + status);
            }
        });

        try {
            Intent intent =
                    new PlaceAutocomplete.IntentBuilder(PlaceAutocomplete.MODE_OVERLAY)
                            .build(this);
            startActivityForResult(intent, PLACE_AUTOCOMPLETE_REQUEST_CODE);
        } catch (GooglePlayServicesRepairableException e) {
        } catch (GooglePlayServicesNotAvailableException e) {
        }

    }


    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {}


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == PLACE_AUTOCOMPLETE_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                Place place = PlaceAutocomplete.getPlace(this, data);
                LatLng position = place.getLatLng();

                mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(position,10) );


            } else if (resultCode == PlaceAutocomplete.RESULT_ERROR) {
                Status status = PlaceAutocomplete.getStatus(this, data);
                // TODO: Handle the error.
                Log.i("favMapPage", "onActivityResult: " + status.getStatusMessage());

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

}
