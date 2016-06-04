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
    private Button updateYourSpouseBtn; // button to update spouse
    private EditText spouseNameEditText; // edit text field
    private Firebase myFirebaseRef; // firebase
    private String yourSpouseSpouseEmail;
    private String myEmail;
    private String yourSpouseEmail; //spouse email
    private String yourSpouseUID; //spouse uid

    /**
     * Name: onCreate
     * @param savedInstanceState
     * create spouse page, add buttons to the addSpousePage,
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_spouse_page);
        myEmail ="";
        yourSpouseSpouseEmail = "";
        yourSpouseEmail = "";
        yourSpouseUID = "";
        myFirebaseRef = ((Initialize) this.getApplication()).getFirebase();
        updateYourSpouseBtn = (Button) findViewById(R.id.updateSpouseButtonID);
        updateYourSpouseBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Perform action on click
                String spouseEmail =  spouseNameEditText.getText().toString();
                findSpouseUID(spouseEmail);
                Log.d("AddSpousePage", "onCreate: Update Successful");


            }
        });

        //fill the textView so that you can see the current spouse
        spouseNameEditText = (EditText) findViewById(R.id.spouseEditTextID);
        displaySpouseID();

    }


    /**
     * Name: displaySpouseID
     * displays the spouse email
     */
    private void displaySpouseID(){

        final Firebase tempRef = getUserRef();
        tempRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                //gets your email
                if (snapshot.child("yourEmail").exists()) {
                    myEmail = snapshot.child("yourEmail").getValue().toString();
                } else {
                    Log.d("AddSpousePage", "displaySpouseID: You dont have Email");
                }
                //get spouse email
                if (snapshot.child("spouseEmail").exists()) {
                    spouseNameEditText.setText(snapshot.child("spouseEmail").getValue().toString(), TextView.BufferType.EDITABLE);
                } else {
                    Log.d("AddSpousePage", "displaySpouseID: You dont have spouse");
                }

            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {
                System.out.println("The read failed: " + firebaseError.getMessage());
            }
        });
    }

    /**
     * Name: findSpouseUID
     * @param spouseEmail
     * finds the spouse UID
     */
    private void findSpouseUID(final String spouseEmail){
        Map<String, Object> yourSpouseEMail = new HashMap<String, Object>();
        yourSpouseEMail.put("spouseEmail", spouseEmail);
        updateSpouseUID(spouseEmail);
        Firebase userRef = getUserRef();
        userRef.updateChildren(yourSpouseEMail);


    }

    /** updates the spouseUID so that when you update your spouse, you're connected on firebase
     *
     * @param spouseEmail : the new spouse's email
     */
    private void updateSpouseUID(final String spouseEmail) {
        Firebase allUsersRef = myFirebaseRef.child("users");
        Log.d("My Email:", myEmail);
        Log.d("Your spouse spouse:", yourSpouseSpouseEmail);

        String spouseUID = "";
        allUsersRef.addChildEventListener(new ChildEventListener() {
            // Retrieve new posts as they are added to the database
            @Override
            public void onChildAdded(DataSnapshot snapshot, String previousChildKey) {
                String temp = snapshot.getKey();
                if (snapshot.child("yourEmail").exists()) {
                    String email = snapshot.child("yourEmail").getValue().toString();
                    Log.d("MyApp", email);
                    Log.d("MyApp", spouseEmail);

                    //checks to see if soouse added you. If so, then you can update your spouse
                    //locally
                    if (email.equals(spouseEmail)) {
                        Map<String, Object> yourSpouseUID = new HashMap<String, Object>();
                        String UID = snapshot.getKey().toString();
                        Log.d("AddSpousePage", "updateSpouseUIDL The spouse you entered is using the app");
                        yourSpouseUID.put("spouseUID", UID);
                        findYourSpouseSpouse(UID);

                    } else {
                        Log.d("AddSpousePage", "UpdateSpouseUID: No Spouse");
                    }
                }

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
    }

    /** Name: getUserRef()
     * get the User's Firebase authorization
     * @return
     */
    private Firebase getUserRef(){
        AuthData authData = myFirebaseRef.getAuth();
        String userId = authData.getUid();
        Firebase userRef = myFirebaseRef.child("users").child(userId);
        return userRef;
    }

    /** findYourSpouseSpouse
     * This method checks to see if you are your spouse's spouse and updates accordingly
     * @param spouseUID: your spouse's uid
     */
    private void findYourSpouseSpouse(final String spouseUID){
        final Firebase spouseRef = myFirebaseRef.child("users").child(spouseUID);
        spouseRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                // if your spouse has a spouse
                if (snapshot.child("spouseEmail").exists()){
                    yourSpouseSpouseEmail = snapshot.child("spouseEmail").getValue().toString();

                    //checks to see if your spouse's spouse is you
                    if (yourSpouseSpouseEmail.equals(myEmail)){
                        Firebase userRef = getUserRef();
                        Map<String, Object> yourSpouseUID = new HashMap<String, Object>();
                        yourSpouseUID.put("spouseUID", spouseUID);
                        userRef.updateChildren(yourSpouseUID);
                    }

                } else{ // you are not set as spouse
                    Log.d("AddSpousePage","findYourSpouseSpouse: Your spouse is not using the app yet!");
                }

            }
            @Override
            public void onCancelled(FirebaseError firebaseError) {
                System.out.println("The read failed: " + firebaseError.getMessage());
            }

        });

    }


}
