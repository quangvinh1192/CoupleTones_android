package com.example.group26.coupletones;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentActivity;

import com.firebase.client.Firebase;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.places.Places;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.List;

public class SOFavMap extends FragmentActivity implements OnMapReadyCallback, GoogleApiClient.OnConnectionFailedListener {

    private GoogleMap mMap;
    private Initialize app;
    private Firebase myFirebaseRef;
    private GoogleApiClient myGoogleApiClient;
    private SOListOfPlaces soListFavPlaces;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        app = (Initialize) this.getApplication();
        myFirebaseRef = app.getFirebase();

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sofav_map);

        myGoogleApiClient = new GoogleApiClient
                .Builder(this)
                .addApi(Places.GEO_DATA_API)
                .addApi(Places.PLACE_DETECTION_API)
                .enableAutoManage(this, this)
                .addApi(AppIndex.API).build();
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.SOFavmap);
        mapFragment.getMapAsync(this);




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

        mMap.getUiSettings().setZoomControlsEnabled(true);
        showSOFavMap();
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
    //TODO make an error message popup for no connection
    }

    /** shows a map that has markers that match up with the soListOfFavPlaces */
    void showSOFavMap() {
        soListFavPlaces = app.getSOListOfFavoritePlaces();
        List<aFavoritePlace> list = soListFavPlaces.favoritePlaceList;
        /** create all the markers and add it */
        for(aFavoritePlace place: list) {
            LatLng favPoint = new LatLng(place.getLatitude(), place.getLongitude());
            mMap.addMarker(new MarkerOptions().position(favPoint).title(place.getName()).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_ROSE)));
        }

    }
}
