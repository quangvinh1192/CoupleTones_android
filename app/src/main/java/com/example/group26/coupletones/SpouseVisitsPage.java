package com.example.group26.coupletones;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.firebase.client.Firebase;

public class SpouseVisitsPage extends AppCompatActivity {

    Firebase ref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_spouse_visits_page);

        ref = ((Initialize)this.getApplication()).getFirebase();

        //TODO still in progress
        //TODO Idea is to get a list of all the places your spouse visited and pass them on the
        //for loop
        String num_array_name[] = {"Hello", "World", "Hola", "Mundo","World", "Hola", "Mundo","World", "Hola", "Mundo"
                ,"Hello", "World", "Hola", "Mundo","World", "Hola", "Mundo","World", "Hola", "Mundo"};

        LinearLayout linear = (LinearLayout) findViewById(R.id.linear);

        LinearLayout.LayoutParams param = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT, 1.0f);

        TextView[] visitedList = new TextView[num_array_name.length];

        // TODO: fill 2-d array with corresponding arrival, name, and departure TIMESTAMPS/data
        //       this will be populated with all the locations, sometimes multiple times if they go to a place multiple times
        //       sort by arrival time and MAKE SURE TO KEEP IT WITH THE CORRECT NAME AND DEPARTURE TIME

        for (int i = 0; i < num_array_name.length; i++) { // NAME OF PLACE -- ARRIVED: [TIME] -- DEPARTED: [TIME]
                                                          // If not departed, then fill [TIME] with "has not left"

            // TODO: loop through the 2-d array and create TextViews as needed

            visitedList[i] = new TextView(getApplicationContext());
            visitedList[i].setText(num_array_name[i].toString());
            visitedList[i].setTextColor(Color.parseColor("#000000"));
            visitedList[i].setTextSize(20);
            visitedList[i].setHeight(75);
            visitedList[i].setLayoutParams(param);
            visitedList[i].setPadding(10, 5, 10, 5);
            linear.addView(visitedList[i]);
        }
    }
}
