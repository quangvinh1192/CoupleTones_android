package com.example.group26.coupletones;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.firebase.client.AuthData;
import com.firebase.client.ChildEventListener;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class AddSpousePage extends AppCompatActivity {
    private Button updateYourSpouseBtn;
    private EditText spouseNameEditText;
    private Firebase myFirebaseRef;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_spouse_page);

        myFirebaseRef = new Firebase("https://coupletonescse100.firebaseio.com");
        updateYourSpouseBtn = (Button) findViewById(R.id.updateSpouseButtonID);
        updateYourSpouseBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Perform action on click
                String spouseEmail =  spouseNameEditText.getText().toString();

                findSpouseUID(spouseEmail);
                Log.d("MyApp", "Update Successful");


            }
        });

        spouseNameEditText = (EditText) findViewById(R.id.spouseEditTextID);

        displaySpouseID();

    }
    private void displaySpouseID(){
        AuthData authData = myFirebaseRef.getAuth();
        String userId = authData.getUid();
        final Firebase tempRef = myFirebaseRef.child("users").child(userId);
        tempRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {

                Log.d("MyApp",snapshot.toString());
                if (snapshot.child("spouseEmail").exists()){
                    spouseNameEditText.setText(snapshot.child("spouseEmail").getValue().toString(), TextView.BufferType.EDITABLE);
                    Log.d("MyApp", snapshot.getValue().toString());
                }
                else{
                    Log.d("MyApp","You dont have spouse");
                }

            }
            @Override
            public void onCancelled(FirebaseError firebaseError) {
                System.out.println("The read failed: " + firebaseError.getMessage());
            }
        });
//
    }
    private void findSpouseUID(final String spouseEmail){
        Map<String, Object> yourSpouseEMail = new HashMap<String, Object>();
        yourSpouseEMail.put("spouseEmail", spouseEmail);


        String spouseUID = "";

        final Firebase allUsersRef = myFirebaseRef.child("users");




        allUsersRef.addChildEventListener(new ChildEventListener() {
            // Retrieve new posts as they are added to the database
            @Override
            public void onChildAdded(DataSnapshot snapshot, String previousChildKey) {
                String temp = snapshot.getKey();
                Firebase aUserRef = allUsersRef.child(temp);
                if (snapshot.child("yourEmail").exists()) {
                    String email = snapshot.child("yourEmail").getValue().toString();
                    Log.d("MyApp", email);
                    Log.d("MyApp", spouseEmail);

                    if (email.equals(spouseEmail)) {
                        Map<String, Object> yourSpouseUID = new HashMap<String, Object>();
                        String UID = snapshot.getKey().toString();
                        Log.d("MyApp", UID);
                        Log.d("MyApp", "HAHA");

                        yourSpouseUID.put("spouseUID",UID);


                        Firebase userRef = getUserRef();
                        userRef.updateChildren(yourSpouseUID);

                    }
                    else{
                        Log.d("MyApp", "OMG");
                    }
                }

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

        Firebase userRef = getUserRef();

        userRef.updateChildren(yourSpouseEMail);

    }
    private Firebase getUserRef(){
        AuthData authData = myFirebaseRef.getAuth();
        String userId = authData.getUid();
        Firebase userRef = myFirebaseRef.child("users").child(userId);
        return userRef;
    }

}
