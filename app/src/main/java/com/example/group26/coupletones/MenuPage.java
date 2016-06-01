package com.example.group26.coupletones;

import android.app.Application;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.firebase.client.Firebase;
import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class MenuPage extends AppCompatActivity {

    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;
    private Application app;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        app = this.getApplication();
        if (app == null) {
            Log.d("menuPage", "menuPage cannot get app instance");
        }
        ((Initialize) app).setSolistofplaces();
        setContentView(R.layout.activity_menu_page);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        Spouse spouse = ((Initialize)this.getApplication()).getSpouse();
        Firebase firebaseRef = ((Initialize)this.getApplication()).getFirebase();
        final SOListOfPlaces soListOfPlaces = ((Initialize)this.getApplication()).getSOListOfFavoritePlaces();



        Log.d("SPPUSEFAVORITESPAGE", "ONCREATE");


        Button goToMapBtn = (Button) findViewById(R.id.goToMapBtn);
        Button spouseOptionsBtn = (Button) findViewById(R.id.spouseOptionsBtn);
        Button viewSpousesVisitsBtn = (Button) findViewById(R.id.viewSpousesVisitsBtn);
        Button viewSpousesFavsBtn = (Button) findViewById(R.id.viewSOFavsBtn);
        Button settingsBtn = (Button) findViewById(R.id.settingsBtn);

        goToMapBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //Perform action on click
                startActivity(new Intent(MenuPage.this, favMapPage.class));
            }
        });

        spouseOptionsBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //Peform action on click
                startActivity(new Intent(MenuPage.this, AddSpousePage.class));
            }
        });

        viewSpousesVisitsBtn.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                //Perform action on click

                if (((Initialize) app).getSpouse().spouseEmail == null) { // Don't attempt to open visited places without spouse

                    Toast.makeText(MenuPage.this, "No spouse detected", Toast.LENGTH_SHORT).show();
                }
                else {

                    startActivity(new Intent(MenuPage.this, SpouseVisitsPage.class));
                }
            }
        });

        viewSpousesFavsBtn.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                //Perform action on click

                if (((Initialize) app).getSpouse().spouseEmail == null) { // Don't attempt to open spouse favs place without spouse

                    Toast.makeText(MenuPage.this, "No spouse detected", Toast.LENGTH_SHORT).show();
                }
                else {
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            ArrayList<aFavoritePlace> listOfPlaces = new ArrayList<aFavoritePlace>();
                            soListOfPlaces.getFavoritesList(listOfPlaces);
                            Bundle extra = new Bundle();

                            Log.i("#number", Integer.toString(listOfPlaces.size()));
                            extra.putSerializable("SOList", (ArrayList<aFavoritePlace>) listOfPlaces);

                            Intent intent = new Intent(MenuPage.this, SpouseFavoritesPage.class);
                            intent.putExtra("extra", extra);

                            startActivity(intent);
                        }
                    }, 1000);

                   // startActivity(new Intent(MenuPage.this, SpouseFavoritesPage.class));
                }
            }
        });

        /*          TODO uncomment once the settings page is created
        settingsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Perform action on click
                startActivity(new Intent(MenuPage.this, settingsPage));
            }
        });*/

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }

    @Override
    public void onStart() {
        super.onStart();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client.connect();
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "MenuPage Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app deep link URI is correct.
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
                "MenuPage Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app deep link URI is correct.
                Uri.parse("android-app://com.example.group26.coupletones/http/host/path")
        );
        AppIndex.AppIndexApi.end(client, viewAction);
        client.disconnect();
    }
}
