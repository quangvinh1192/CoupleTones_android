package com.example.group26.coupletones;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import com.firebase.client.Firebase;

import java.util.List;

public class SpouseFavoritesPage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_spouse_favorites_page);

        //TODO need SOListOfPlaces to work properly in order to complete this page
        //TODO For now, the Vibration and Sound settings will be created
        String array[] = {"Hello", "World", "Hola", "Mundo", "Goodbye", "World"};

        Spouse spouse = ((Initialize)this.getApplication()).getSpouse();
        Firebase firebaseRef = ((Initialize)this.getApplication()).getFirebase();
        SOListOfPlaces soListOfPlaces = new SOListOfPlaces(spouse, firebaseRef);
        Log.d("SPPUSEFAVORITESPAGE", "ONCREATE");
        List<aFavoritePlace> listOfPlaces = soListOfPlaces.getFavoritesList();
        LinearLayout linear = (LinearLayout) findViewById(R.id.linear);
        LinearLayout.LayoutParams param = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT, 1.0f);

        Button[] btnEdit = new Button[listOfPlaces.size()];
        Log.d("Size of favPlacesList", "Size: " + listOfPlaces.size());
        for (int i = 0; i < listOfPlaces.size(); i++) {
            Log.d("creatingButton", "Button is being created");
            btnEdit[i] = new Button(getApplicationContext());
            btnEdit[i].setText(listOfPlaces.get(i).getName().toString());
            btnEdit[i].setTextColor(Color.parseColor("#000000"));
            btnEdit[i].setTextSize(20);
            btnEdit[i].setHeight(100);
            btnEdit[i].setLayoutParams(param);
            btnEdit[i].setPadding(15, 5, 15, 5);
            linear.addView(btnEdit[i]);

            btnEdit[i].setOnClickListener(handleOnClick(btnEdit[i]));
        }

        //DEBUG
       /* if(listOfPlaces.size() == 0){

            Button b = new Button(getApplicationContext());
            b.setText("SOListOfPlaces not working properly");

            Button b2 = new Button(getApplicationContext());
            b.setText("or Your SO has not added any places");

            linear.addView(b);
            linear.addView(b2);
        }*/

    }

    View.OnClickListener handleOnClick(final Button button) {
        return new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent( SpouseFavoritesPage.this, LocationSettingsPage.class );
                intent.putExtra( "Location Name", button.getText() );
                startActivity(intent);
            }
        };
    };
}
