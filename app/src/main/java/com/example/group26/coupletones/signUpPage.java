package com.example.group26.coupletones;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.firebase.client.AuthData;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;

import java.util.HashMap;
import java.util.Map;


public class signUpPage extends AppCompatActivity {

    private TextView email;
    private TextView password;
    private TextView confirmPassword;
    private Firebase ref;
    private boolean added;

    /** Name: onCreate
     * creates the page, adds buttons and fields to allow for signup
     * @param savedInstanceState
     */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        added = false;
        super.onCreate(savedInstanceState);
        Firebase.setAndroidContext(this);
        ref = new Firebase("https://coupletonescse100.firebaseio.com");
        setContentView(R.layout.activity_sign_up_page);
        //set up text fields
        email = (TextView) findViewById(R.id.emailTVSignUp);
        password = (TextView) findViewById(R.id.passwordSignUp);
        confirmPassword =(TextView) findViewById(R.id.confirmPasswordTV);
        final ErrorMessageHandler errorHandler = new ErrorMessageHandler(signUpPage.this);

        /******[START]******/
        //create sign up button
        Button signUpButton = (Button) findViewById(R.id.signUpButton_SignUp);
        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createAccount(email, password, errorHandler);
            }
        });
        /*****[END]*****/
    }

    /** Name: createAccount
     * creates an account, checks to see if allowed, adds to firebase
     * @param email
     * @param password
     * @param errorHandler
     * @return bool. true if account is created, false otherwise
     */
    public boolean createAccount (final TextView email,final TextView password, final ErrorMessageHandler errorHandler) {
        //if the passwords match then create an account
        if(password.getText().toString().equals((confirmPassword.getText()).toString())) {
            ref.createUser(email.getText().toString(),password.getText().toString(), new Firebase.ValueResultHandler<Map<String, Object>>() {
                //user was created successfully
                @Override
                public void onSuccess(Map<String, Object> result) {
                    Map<String, Object> userEmail = new HashMap<String, Object>();
                    Firebase.AuthResultHandler authResultHandler = new Firebase.AuthResultHandler() {
                        public void onAuthenticated(AuthData authData) {};
                        public void onAuthenticationError(FirebaseError firebaseError) {};
                    };

                    startActivity(new Intent(signUpPage.this, favMapPage.class));
                    setAddedNewAccount(true);
                }
                //user was not created successfully
                //show appropriate error
                @Override
                public void onError(FirebaseError firebaseError) {

                    if (firebaseError.getCode() == FirebaseError.INVALID_EMAIL) {

                        errorHandler.invalidEmail();
                    }
                    else if (firebaseError.getCode() == FirebaseError.EMAIL_TAKEN) {

                        errorHandler.existingEmail();
                    }
                    setAddedNewAccount(false);
                }
            });
        }

        // Password and confirm password do not match
        else {
            errorHandler.passwordsNotMatching();
            setAddedNewAccount(false);
        }

        return this.added;
    }

    /** Name: setAddedNewAccount
     * changes boolean that determines if a new account has been added
     * @param added
     * @return boolean
     */
    boolean setAddedNewAccount(boolean added){
        this.added = added;
        return added;
    }
}
