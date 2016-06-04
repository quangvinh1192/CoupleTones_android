package com.example.group26.coupletones;

import android.graphics.Color;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Pair;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.firebase.client.ChildEventListener;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;
import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;

public class SpouseVisitsPage extends AppCompatActivity {

    Firebase ref;
    String spouse;
    LinearLayout linear;
    LinearLayout.LayoutParams param;

    // Hashmap to hold history; key is a long representing time; value is a pair with string Name
    // and boolean isArrival (1 for arrive; 0 for depart)
    private HashMap<Long, Pair<String, Boolean>> historyMap = new HashMap<>();
    private ArrayList<Long> timeKeys = new ArrayList<>();
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_spouse_visits_page);
        ref = ((Initialize) this.getApplication()).getFirebase();
        spouse = ((Initialize) this.getApplication()).getSpouse().spouseUID;
        Firebase tempRef = ref.child("users").child(spouse).child("history");
        linear = (LinearLayout) findViewById(R.id.linear);
        param = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT, 1.0f);

        /** use this to fill the spouse visited page */
        tempRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                populateHistory(dataSnapshot);
            }
            @Override
            public void onCancelled(FirebaseError firebaseError) {}
        });
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }

    // This method gets the arrival/departure information from history in a Firebase snapshot
    // and uses it to populate a Hashmap whose keys are used to populate the arrayList
    private void populateHistory(DataSnapshot dataSnapshot) {
        // For all favorite locations
        for (DataSnapshot favLoc : dataSnapshot.getChildren()) {
            // For both arrival and departure
            for (DataSnapshot arDep : favLoc.getChildren()) {
                // For all arrival and departure times
                for (DataSnapshot time : arDep.getChildren()) {
                    // Set up pair to handle the name and whether one is arriving or departing
                    Pair<String, Boolean> temp;
                    if (arDep.getKey().toString().equals("arrive")) {
                        // Arriving = true bool
                        temp = new Pair<>(favLoc.getKey().toString(), Boolean.TRUE);
                    } else {
                        // Departing = false bool
                        temp = new Pair<>(favLoc.getKey().toString(), Boolean.FALSE);
                    }
                    historyMap.put(Long.parseLong(time.getValue().toString()), temp);
                }
            }
        }

        // Sort the departure and arrival times for printing
        timeKeys.addAll(historyMap.keySet());
        Collections.sort(timeKeys, Collections.<Long>reverseOrder());

        // Create buttons based on hashmap
        createButtons();
    }

    // Creates buttons for the page
    private void createButtons() {
        TextView[] visitedList = new TextView[timeKeys.size()];
        for (int i = 0; i < timeKeys.size(); i++) {
            visitedList[i] = new TextView(getApplicationContext());
            // Set up the right text to print: LOCATION NAME, arrived at: DATE + TIME
            String textToPrint = historyMap.get(timeKeys.get(i)).first;
            if (historyMap.get(timeKeys.get(i)).second) {
                textToPrint += ", arrived at: ";
            } else {
                textToPrint += ", departed from: ";
            }
            Date time = new Date(timeKeys.get(i));
            textToPrint += time.toString();

            visitedList[i].setText(textToPrint);
            visitedList[i].setTextColor(Color.parseColor("#000000"));
            visitedList[i].setTextSize(10);
            visitedList[i].setHeight(75);
            visitedList[i].setLayoutParams(param);
            visitedList[i].setPadding(10, 5, 10, 5);
            linear.addView(visitedList[i]);
        }
    }

    @Override
    public void onStart() {
        super.onStart();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client.connect();
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "SpouseVisitsPage Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app URL is correct.
                Uri.parse("android-app://com.example.group26.coupletones/http/host/path")
        );
        AppIndex.AppIndexApi.start(client, viewAction);
    }

    @Override
    public void onStop() {
        super.onStop();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "SpouseVisitsPage Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app URL is correct.
                Uri.parse("android-app://com.example.group26.coupletones/http/host/path")
        );
        AppIndex.AppIndexApi.end(client, viewAction);
        client.disconnect();
    }
}
