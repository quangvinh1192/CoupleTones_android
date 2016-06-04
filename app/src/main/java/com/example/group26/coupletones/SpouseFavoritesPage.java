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

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class SpouseFavoritesPage extends AppCompatActivity {
    private static final ScheduledExecutorService worker =
            Executors.newSingleThreadScheduledExecutor();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_spouse_favorites_page);

        Bundle extra = getIntent().getBundleExtra("extra");
        ArrayList<aFavoritePlace> listOfPlaces = (ArrayList<aFavoritePlace>) extra.getSerializable("SOList");

        LinearLayout linear = (LinearLayout) findViewById(R.id.linear);
        LinearLayout.LayoutParams param = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT, 1.0f);

        // load buttons
        loadButtons(linear, listOfPlaces);
        Button showPlacesOnMap = (Button) findViewById(R.id.viewSOFavsBtn);
        showPlacesOnMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SpouseFavoritesPage.this, SOFavMap.class));
            }
        });
    }

    /** show all the places that are the spouse favorite's places. Each of these places are buttons
     * that can be clicked and will take the user to a new page that contains settings options
     * @param linear
     * @param listOfPlaces
     */
    void loadButtons(final LinearLayout linear, final List<aFavoritePlace> listOfPlaces) {
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
        };
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
