package com.example.group26.coupletones.tests;

import android.support.test.filters.LargeTest;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.example.group26.coupletones.R;
import com.example.group26.coupletones.loginPage;
import com.example.group26.coupletones.signUpPage;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.doesNotExist;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

/**
 * Created by admin_me on 5/31/16.
 */
@RunWith(AndroidJUnit4.class)
@LargeTest
public class existenceEmail {
    @Rule
    public ActivityTestRule<signUpPage> testloginPage= new ActivityTestRule<>(signUpPage.class);

    @Test
    public void existenceEmail(){
        onView(withId(R.id.emailTVSignUp)).perform(typeText("quangvinh1192@gmail.com"),closeSoftKeyboard());
        onView(withId(R.id.passwordSignUp)).perform(typeText("123"),closeSoftKeyboard());
        onView(withId(R.id.confirmPasswordTV)).perform(typeText("123"),closeSoftKeyboard());
        onView(withId(R.id.signUpButton_SignUp)).perform(click());
        onView(withText("This email is already in use")).check(matches(isDisplayed()));
    }

}
