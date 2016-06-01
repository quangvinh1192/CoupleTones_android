package com.example.group26.coupletones;

import android.content.Context;
import android.os.Vibrator;
import android.util.Log;

/**
 * Created by Jeremy on 5/30/2016.
 */
public class VibrationHandler {

    String typeOfVibration; // Holds the type of vibration (arrival, departure, place-specific)

    Vibrator vibratorObject; // Vibrator object to do actual vibrating

    long[] arrivalPattern = {0, 1000};
    long[] departurePattern = {0, 500, 100, 500};
    long[] uniquePattern;

    // Constructor for handling vibrations NON-UNIQUE
    VibrationHandler (String typeOfVibration, Context thisContext) {
        this.typeOfVibration = typeOfVibration;
        vibratorObject = (Vibrator) thisContext.getSystemService(Context.VIBRATOR_SERVICE);
    }

    // Constructor for handling vibrations UNIQUE
    VibrationHandler (String typeOfVibration, Context thisContext, long[] uniquePattern) {
        this.typeOfVibration = typeOfVibration;
        vibratorObject = (Vibrator) thisContext.getSystemService(Context.VIBRATOR_SERVICE);
        this.uniquePattern = uniquePattern;
    }

    // Method to call appropriate vibration pattern
    void vibrateByType () {

        // Play arrival tone
        if (typeOfVibration.equals("arrival")) {

            vibratorObject.vibrate(arrivalPattern, -1);
        }

        // Play departure tone
        else if (typeOfVibration.equals("departure")) {

            vibratorObject.vibrate(departurePattern, -1);
        }

        // Play unique tone for the location
        else if (typeOfVibration.equals("unique")) {

            if (uniquePattern != null && uniquePattern.length != 0) {

                vibratorObject.vibrate(uniquePattern, -1);
            }
        }

        // Debug - error, incorrect call
        else {

            Log.d("ErrorMsg", "Incorrect vibration type passed in");
        }
    }
}
