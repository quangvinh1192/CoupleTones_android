package com.example.group26.coupletones;

import android.content.Context;
import android.os.Vibrator;
import android.util.Log;

/**
 * Created by Jeremy on 5/30/2016.
 */
public class VibrationHandler {

    String typeOfVibration; // Holds the type of vibration (arrival, departure, place-specific)

    Vibrator vibrator; // Vibrator object to do actual vibrating

    private final long[] arrivalPattern = {0, 100};
    private final long[] departurePattern = {0, 500, 100, 500};
    private final long[] two_short_vibrations = {0, 300, 200, 300};
    private final long[] one_short_vibration = {0, 1000};
    private final long[] one_long_vibration = {0, 2000};

    // Constructor for handling vibrations NON-UNIQUE
    VibrationHandler (Context thisContext) {
        vibrator = (Vibrator) thisContext.getSystemService(Context.VIBRATOR_SERVICE);
    }

    public void vibrate( String vibration_type ){

        if(vibrator.hasVibrator()) {
            Log.v("Can Vibrate", "YES");
            if( vibration_type.equals( "2 short vibrations" ) ) {

                vibrator.vibrate( two_short_vibrations, -1);
            }else if(vibration_type.equals("1 short vibration")) {

                vibrator.vibrate( one_short_vibration, -1);
            }else if( vibration_type.equals("1 long vibration")) {

                vibrator.vibrate( one_long_vibration, -1);
            }else if( vibration_type.equals("arrival vibration")){

                vibrator.vibrate( arrivalPattern, -1);
            }else if( vibration_type.equals("departure vibration")){

                vibrator.vibrate( departurePattern, -1 );
            }else{
                Log.d( "vibration set to: ", vibration_type );
            }
        }else{
            Log.v("Can Vibrate", "NO");
        }
    }
}
