package com.example.group26.coupletones;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Spinner;
import android.widget.TextView;

public class LocationSettingsPage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location_settings);

        String location_name = getIntent().getStringExtra("Location Name");

        TextView title = (TextView) findViewById(R.id.title);
        title.setText(location_name + " Settings");
    }

    public void onClickTestVibration(View view){

        Intent intent = new Intent(LocationSettingsPage.this, VibrationService.class);
        Spinner vibration_spinner = (Spinner) findViewById( R.id.vibrationSpinner );
        String vibration_selected = String.valueOf( vibration_spinner.getSelectedItem() );
        intent.putExtra( "vibration selected", vibration_selected );

        this.startService(intent);
    }

    //TODO
    public void onClickSetVibration(View view){

    }

    //TODO
    public void onClickTestSound(View view){
        Intent intent = new Intent(LocationSettingsPage.this, SoundService.class);
        Spinner sound_spinner = (Spinner) findViewById( R.id.soundSpinner );
        String sound_selected = String.valueOf( sound_spinner.getSelectedItem() );
        intent.putExtra( "sound selected", sound_selected );

        this.startService(intent);
    }

    //TODO
    public void onClickSetSound(View view){

    }
}
