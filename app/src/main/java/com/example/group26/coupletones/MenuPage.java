package com.example.group26.coupletones;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;

public class MenuPage extends AppCompatActivity {

    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_page);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

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
                startActivity(new Intent(MenuPage.this, SpouseVisitsPage.class));
            }
        });

        viewSpousesFavsBtn.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                //Perform action on click
                startActivity(new Intent(MenuPage.this, SpouseFavoritesPage.class));
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
