package com.example.group26.coupletones;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

public class SpouseFavoritesPage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_spouse_favorites_page);
        //TODO need to access Firebase to get the Spouses favorite locations then pass into list
       /* String num_array_name[] = {"Hello", "World", "Hola", "Mundo","World", "Hola", "Mundo","World", "Hola", "Mundo"
                ,"Hello", "World", "Hola", "Mundo","World", "Hola", "Mundo","World", "Hola", "Mundo"};

        LinearLayout linear = (LinearLayout) findViewById(R.id.linear);

        LinearLayout.LayoutParams param = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT, 1.0f);

        Button[] btn = new Button[num_array_name.length];
        for (int i = 0; i < num_array_name.length; i++) {
            btn[i] = new Button(getApplicationContext());
            btn[i].setText(num_array_name[i].toString());
            btn[i].setTextColor(Color.parseColor("#000000"));
            btn[i].setTextSize(20);
            btn[i].setHeight(100);
            btn[i].setLayoutParams(param);
            btn[i].setPadding(15, 5, 15, 5);
            linear.addView(btn[i]);

            btn[i].setOnClickListener(handleOnClick(btn[i]));
        }*/


    }

    View.OnClickListener handleOnClick(final Button button) {
        return new View.OnClickListener() {
            public void onClick(View v) {
            }
        };
    };
}
