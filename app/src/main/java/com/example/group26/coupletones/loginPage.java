package com.example.group26.coupletones;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;

import com.firebase.client.AuthData;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;

import java.util.HashMap;
import java.util.Map;


public class loginPage extends AppCompatActivity {

    public Initialize globalAppVariables; // THIS WILL BE USED BY ALL OTHER CLASSES
    private TextView email;
    private TextView password;
    private Firebase ref;
    private ErrorMessageHandler errorHandler;
    private GoogleApiClient client;
    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_page);
        email = (TextView) findViewById(R.id.emailTVSignUp);
        password = (TextView) findViewById(R.id.passwordSignUp);
        errorHandler = new ErrorMessageHandler (loginPage.this);
        context = this;

        Log.d("LoginPage", "onCreate initialized email, password, etc");

        //initialize global variables this way
        globalAppVariables = ((Initialize) getApplicationContext());
        globalAppVariables.setFirebase(context);
        ref = globalAppVariables.getFirebase();

        if(ref == null) {
            Log.d ("LoginPage", "OnCreate: could not initialize Firebase");
        }

        Log.d("LoginPage","OnCreate, initialized firebase");

        /******[START]******/
        //create submit and sign up buttons and set there respective actions
        Button submitButton = (Button) findViewById(R.id.submitButton);
        Button signUpButton = (Button) findViewById(R.id.signUpButton);
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fieldsFilled (email, password, errorHandler);
            }
        });

        //listen to see if user wants to sign up
        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //simply go to sign up page
                startActivity(new Intent(loginPage.this, signUpPage.class));
            }
        });

        /*****[END]*****/

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_login_page, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onStart() {
        super.onStart();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client.connect();
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "loginPage Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app URL is correct.
                Uri.parse("android-app://com.example.group26.coupletones/http/host/path")
        );
        AppIndex.AppIndexApi.start(client, viewAction);
    }

    @Override
    public void onStop() {
        super.onStop();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "loginPage Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app URL is correct.
                Uri.parse("android-app://com.example.group26.coupletones/http/host/path")
        );
        AppIndex.AppIndexApi.end(client, viewAction);
        client.disconnect();
    }

    /** fieldsFilled
     * checks to see if email and password are filled and are correct returns true if they're correct
     * @param email
     * @param password
     * @param errorHandler
     * @return bool indiicating if they are correct
     */
    public boolean fieldsFilled (TextView email, TextView password, final ErrorMessageHandler errorHandler) {
        Log.d("LOGIN PAGE", "fields filled method");
        //check if at least both fields are filled
        if (!email.getText().toString().isEmpty() && !password.getText().toString().isEmpty()) {
            // Create a handler to handle the result of the authentication
            Firebase.AuthResultHandler authResultHandler = new Firebase.AuthResultHandler() {
                @Override
                public void onAuthenticated(AuthData authData) {
                    Log.d("LOGIN PAGE", "fieldsFilled: onAuthenticated");
                    // Authenticated successfully with payload authData
                    //move to maps page
                    Map<String, Object> yourEmail = new HashMap<String, Object>();
                    String email =  authData.getProviderData().get("email").toString();
                    yourEmail.put("yourEmail", email);

                    //initialize spouse
                    globalAppVariables.setSpouse();
                    globalAppVariables.startListeningToMyself();

                    Firebase userRef = ref.child("users").child(authData.getUid());
                    userRef.updateChildren(yourEmail);

                    Log.d("MyApp", "Update Successful");

                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
//                            Spouse spouse = globalAppVariables.getSpouse();
//                            Firebase firebaseRef = globalAppVariables.getFirebase();
//                            SOListOfPlaces soListOfPlaces = new SOListOfPlaces(spouse, firebaseRef);

                            Intent i=new Intent(loginPage.this, MenuPage.class);
                            startActivity(i);
                        }
                    }, 3000);
                    //go to menupage
                    //startActivity(new Intent(loginPage.this, MenuPage.class));
                }

                @Override
                public void onAuthenticationError(FirebaseError firebaseError) {
                    // Authenticated failed with error firebaseError
                    //figure out what went wrong and return it to the developer
                    //TODO

                    errorHandler.onLoginError();
                }
            };
            ref.authWithPassword(email.getText().toString(),password.getText().toString(),authResultHandler);
            return true;
        }
        //either the password or the email field is missing
        //give the user an appropriate error message
        else {

            errorHandler.onLoginMissingField();
            return false;
        }
    }
}
