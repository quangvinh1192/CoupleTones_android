package com.example.group26.coupletones;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;

import java.util.HashMap;
import java.util.Map;


public class signUpPage extends AppCompatActivity {

    private TextView email;
    private TextView password;
    private TextView confirmPassword;
    private Firebase ref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Firebase.setAndroidContext(this);
        ref = new Firebase("https://coupletonescse100.firebaseio.com");
        setContentView(R.layout.activity_sign_up_page);
        //set up text fields
        email = (TextView) findViewById(R.id.emailTV);
        password = (TextView) findViewById(R.id.passwordTV);
        confirmPassword =(TextView) findViewById(R.id.confirmPasswordTV);

        /******[START]******/
        //create sign up button
        Button signUpButton = (Button) findViewById(R.id.signUpButton);
        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //if the passwords match then create an account
                if(password.getText().toString().equals((confirmPassword.getText()).toString())) {
                    ref.createUser(email.getText().toString(),password.getText().toString(), new Firebase.ValueResultHandler<Map<String, Object>>() {
                        //user was created successfully
                        @Override
                        public void onSuccess(Map<String, Object> result) {
                            Map<String, Object> userEmail = new HashMap<String, Object>();
                            ref = ref.child("users").child(ref.getAuth().getUid());
                            userEmail.put("email", email.getText().toString());
                            ref.updateChildren(userEmail);
                            startActivity(new Intent(signUpPage.this, favMap.class));
                        }
                        //user was not created successfully
                        //show appropriate error
                        @Override
                        public void onError(FirebaseError firebaseError) {
                            //TODO
                            //take care of telling the user they messed up
                            System.out.println("something went wrong");
                        }
                    });
                }
            }
        });
        /*****[END]*****/
    }
}