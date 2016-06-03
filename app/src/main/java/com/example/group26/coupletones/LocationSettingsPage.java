package com.example.group26.coupletones;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

public class LocationSettingsPage extends AppCompatActivity {

    private String location_name;
    private NotificationControl notificationControl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location_settings);

        this.location_name = getIntent().getStringExtra("Location Name");
        this.notificationControl = ((Initialize)getApplication()).getNotificationControl();

        TextView title = (TextView) findViewById(R.id.title);
        title.setText(location_name + " Settings");
    }

    //Plays the vibration
    public void onClickTestVibration(View view){

        Spinner vibration_spinner = (Spinner) findViewById( R.id.vibrationSpinner );
        String vibration_selected = String.valueOf( vibration_spinner.getSelectedItem() );

        VibrationHandler vibrationHandler = notificationControl.getVibrationHandler();

        vibrationHandler.vibrate( vibration_selected );
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

        Toast toast = Toast.makeText( getApplicationContext(), "Vibration Set" , Toast.LENGTH_SHORT);
        toast.show();
    }

    //
    public void onClickTestSound(View view){


        Spinner sound_spinner = (Spinner) findViewById( R.id.soundSpinner );
        String sound_selected = String.valueOf( sound_spinner.getSelectedItem() );

        SoundHandler soundHandler = notificationControl.getSoundHandler();

        soundHandler.playSound(sound_selected);
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

        Toast toast = Toast.makeText( getApplicationContext(), "Sound Set" , Toast.LENGTH_SHORT);
        toast.show();
    }
}
