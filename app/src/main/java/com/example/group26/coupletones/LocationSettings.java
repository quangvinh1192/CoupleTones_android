package com.example.group26.coupletones;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class LocationSettings extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location_settings);

        String location_name = getIntent().getStringExtra("Location Name");

        TextView title = (TextView) findViewById(R.id.title);
        title.setText(location_name + " Settings");
    }
}
