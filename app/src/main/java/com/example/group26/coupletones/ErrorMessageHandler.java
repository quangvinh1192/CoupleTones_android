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

    void onLoginError () {

        alertDialogBuilder.setMessage("Incorrect email or password");
        AlertDialog alertDialog = alertDialogBuilder.show();
    }

    void onLoginMissingField() {

        alertDialogBuilder.setMessage("Email or password is missing");
        AlertDialog alertDialog = alertDialogBuilder.show();
    }

    void invalidEmail () {

        alertDialogBuilder.setMessage("The email you entered is not valid");
        AlertDialog alertDialog = alertDialogBuilder.show();
    }

    void existingEmail () {

        alertDialogBuilder.setMessage("This email is already in use");
        AlertDialog alertDialog = alertDialogBuilder.show();

    }
}
