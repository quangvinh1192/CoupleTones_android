package com.example.group26.coupletones;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.List;

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

    //sets the vibration locally for the specific location
    //Check SharedPreferences Lab to see how to access it
    //TODO possible bug will be when another vibration is set is might just append to the old vibration_selected
    public void onClickSetVibration(View view){

        /** Stores the Unique Vibration on the SharedPreferences **/
        Spinner vibration_spinner = (Spinner)findViewById(R.id.vibrationSpinner);
        String vibration_selected = String.valueOf(vibration_spinner.getSelectedItem());

        SharedPreferences sharedPreference = getSharedPreferences("location_info", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreference.edit();

        editor.putString( location_name + "_vibration" , vibration_selected );
        editor.apply();


    }

    //
    public void onClickTestSound(View view){
        Intent intent = new Intent(LocationSettingsPage.this, SoundService.class);
        Spinner sound_spinner = (Spinner) findViewById( R.id.soundSpinner );
        String sound_selected = String.valueOf( sound_spinner.getSelectedItem() );
        intent.putExtra( "sound selected", sound_selected );

        this.startService(intent);
    }

    //sets the Sound locally for the location
    //To get the sound for the certain location check SharedPreferences lab
    //TODO possible bug will be when another sound is selected, it might append to the old sound selected
    public void onClickSetSound(View view){

        /** Saves the unique sound String on a SharedPreference **/
        Spinner sound_spinner = (Spinner)findViewById(R.id.soundSpinner);
        String sound_selected = String.valueOf( sound_spinner.getSelectedItem() );

        SharedPreferences sharedPreference = getSharedPreferences("location_info", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreference.edit();

        editor.putString( location_name + "_sound" , sound_selected );
        editor.apply();

    }
}
