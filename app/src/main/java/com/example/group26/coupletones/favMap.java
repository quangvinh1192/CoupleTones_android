package com.example.group26.coupletones;

import android.bluetooth.le.AdvertiseData;
import android.gesture.GestureOverlayView;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;

import com.firebase.client.AuthData;
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

public class favMap extends FragmentActivity implements OnMapReadyCallback    {

    private GoogleMap mMap;
    private Button addPlaceBtn;
    private boolean addingMode;
    private RelativeLayout addingView;
    private Button confirmAddBtn;
    private Button cancelAddBtn;
    private Marker temporaryMarker;
    private Firebase myFirebaseRef;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fav_map);

        String id = "https://coupletonescse100.firebaseio.com";
        myFirebaseRef = new Firebase(id);



        Log.d("MyApp",id);




        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        addPlaceBtn = (Button)findViewById(R.id.addPlaceBtnID);
        confirmAddBtn = (Button) findViewById(R.id.confirmAddID);
        cancelAddBtn = (Button) findViewById(R.id.cancelAddID);

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
        confirmAddBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Perform action on click
                addingView.setVisibility(View.INVISIBLE);
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

        // Add a marker in Sydney and move the camera
        LatLng sydney = new LatLng(-34, 151);
        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
        mMap.setOnMapLongClickListener(new GoogleMap.OnMapLongClickListener() {
            @Override
            public void onMapLongClick(LatLng point) {
                if (addingMode){
                    addingView.setVisibility(View.VISIBLE);
                    //temporaryMarker.position(point);
                    temporaryMarker = mMap.addMarker(new MarkerOptions().position(point).title("Marker in Sydney"));
                }
            }

        });

    }
    public void addPlaceToServer(){
        AuthData authData = myFirebaseRef.getAuth();
        String a = authData.getUid();
        Firebase temp = myFirebaseRef.child("users").child(a).child("favPlaces");
//        tempUser.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(DataSnapshot snapshot) {
//                System.out.println(snapshot.getValue());
//            }
//            @Override
//            public void onCancelled(FirebaseError firebaseError) {
//                System.out.println("The read failed: " + firebaseError.getMessage());
//            }
//        });
        aFavoritePlace newPlaceToAdd = new aFavoritePlace(temporaryMarker.getPosition(),"haha",false);
        temp.push().setValue(newPlaceToAdd);
    }
}
