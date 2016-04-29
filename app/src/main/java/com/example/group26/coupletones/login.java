package com.example.group26.coupletones;

import com.firebase.client.AuthData;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;

/**
 * Created by gagan on 4/28/16.
 */
public class login {
    Firebase ref;
    Firebase.AuthResultHandler authResultHandler;
    Firebase.ResultHandler result;
    FirebaseError error;
    String value;

    /**
     * Creates a new Firebase reference with the server
     * @param url
     */
    public login(String url) {
        ref = new Firebase(url);
    }

    /**
     * Logs user in with the server
     * @param email
     * @param password
     * @return String that has information about what happend in the authentication
     * process
     */
    public String authenticateUser(String email, String password) {
        ref.authWithPassword(email, password, authResultHandler);
        authResultHandler.onAuthenticationError(error);

        //if there were no errors then return no error
        if (error == null)
            value = "No Error";

        else {
            //figure out what went wrong and return it to the devleoper
            switch (error.getCode()) {
                case -5:
                    value = "Invalid Email";
                    break;
                case -6:
                    value = "Invalid Password";
                    break;
                default:
                    value = "Something else";
                    break;
            }
        }
        return value;
    }

    /**
     * Creates a new user then creates data on the server
     * @param email
     * @param password
     * @return String that states if the user was created or what went wrong
     */
    public String createUser(String email, String password){
        ref.createUser(email, password, result);
        result.onError(error);

        if(error == null){
            value = "No Error";
            ref.authWithPassword(email, password, authResultHandler);
            ref.child("users").setValue(email);
        }

        else{
            switch(error.getCode()){
                case -9: value = "Email Taken";
                    break;
                default: value = "Something else";
            }
        }
        return value;
    }
}
