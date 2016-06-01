package com.example.group26.coupletones;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Spinner;
import android.widget.TextView;

public class LocationSettingsPage extends AppCompatActivity {

    String location_name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location_settings);

        this.location_name = getIntent().getStringExtra("Location Name");

        TextView title = (TextView) findViewById(R.id.title);
        title.setText(location_name + " Settings");
    }

    //Plays the vibration
    public void onClickTestVibration(View view){

        Intent intent = new Intent(LocationSettingsPage.this, VibrationService.class);
        Spinner vibration_spinner = (Spinner) findViewById( R.id.vibrationSpinner );
        String vibration_selected = String.valueOf( vibration_spinner.getSelectedItem() );
        intent.putExtra( "vibration selected", vibration_selected );

        this.startService(intent);
    }

    //
    public void onClickSetVibration(View view){
        Spinner vibration_spinner = (Spinner)findViewById(R.id.vibrationSpinner);
        String vibration_selected = String.valueOf(vibration_spinner.getSelectedItem());

        //gets aFavoritePlace object
        aFavoritePlace favoritePlace = ((Initialize) this.getApplication()).getFavoriteLocations().get( location_name );

        //sets the unique vibration of the favorite place to what is chosen
        favoritePlace.setUniqueVibration( vibration_selected );
    }

    //
    public void onClickTestSound(View view){
        Intent intent = new Intent(LocationSettingsPage.this, SoundService.class);
        Spinner sound_spinner = (Spinner) findViewById( R.id.soundSpinner );
        String sound_selected = String.valueOf( sound_spinner.getSelectedItem() );
        intent.putExtra( "sound selected", sound_selected );

        this.startService(intent);
    }

    //sets the sound on initilize's FavoriteLocations HashMap
    public void onClickSetSound(View view){
        Spinner sound_spinner = (Spinner)findViewById(R.id.soundSpinner);
        String sound_selected = String.valueOf( sound_spinner.getSelectedItem() );

        //gets aFavoritePlace object
        aFavoritePlace favoritePlace = ((Initialize) this.getApplication()).getFavoriteLocations().get( location_name );

        //sets the unique sound of the favorite place to what is chosen
        favoritePlace.setUniqueSound( sound_selected );
    }
}
