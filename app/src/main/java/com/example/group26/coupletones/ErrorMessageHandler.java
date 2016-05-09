package com.example.group26.coupletones;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.provider.Settings;
import android.util.Log;

/**
 * Created by Jeremy on 5/8/2016.
 */
public class ErrorMessageHandler {

    AlertDialog.Builder alertDialogBuilder;

    // Constructor
    public ErrorMessageHandler (Context thisContext) {

        alertDialogBuilder = new AlertDialog.Builder(thisContext)
                                .setTitle("Error")
                                .setNegativeButton("Okay", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.cancel();
                                    }
                                });

    }

    /**
     * Name: onLoginerror
     * shows an error if email is incorrect email or password
     */
    void onLoginError () {

        alertDialogBuilder.setMessage("Incorrect email or password");
        AlertDialog alertDialog = alertDialogBuilder.show();
    }

    /**
     * onLoginMissingField
     * sets error if email or password is missing
     */
    void onLoginMissingField() {

        alertDialogBuilder.setMessage("Email or password is missing");
        AlertDialog alertDialog = alertDialogBuilder.show();
    }


    /**
     * Name: invalidEmail
     * Creates alert for invalid email
     */
    void invalidEmail () {

        alertDialogBuilder.setMessage("The email you entered is not valid");
        AlertDialog alertDialog = alertDialogBuilder.show();
    }

    /**
     * Name: existingEmail
     * Creates alert for email already in use
     */
    void existingEmail () {

        alertDialogBuilder.setMessage("This email is already in use");
        AlertDialog alertDialog = alertDialogBuilder.show();

    }

    /**
     * Name: passwrodsnotMatching
     * alerts for if the two passwords don't match
     */
    void passwordsNotMatching () {

        alertDialogBuilder.setMessage("The password and confirm password do not match");
        AlertDialog alertDialog = alertDialogBuilder.show();
    }
}
