package com.example.group26.coupletones.loginpageTesting;

import android.os.SystemClock;
import android.support.test.filters.LargeTest;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.example.group26.coupletones.R;
import com.example.group26.coupletones.loginPage;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

/**
 * Created by vinh_tran on 5/31/16.
 */
@RunWith(AndroidJUnit4.class)
@LargeTest
public class signUpfromLogIntest {
    @Rule
    public ActivityTestRule<loginPage> testloginPage= new ActivityTestRule<>(loginPage.class);
    /*
     This test tests Sign Up button from log in page
     Input: When users choose sign up option, app will open a new layout and
     users can register a new account
     */
    @Test
    public void gotoSignUppage() {
        onView(withId(R.id.signUpButton_Login)).perform(click());
        //delay-waiting to see what happens
        SystemClock.sleep(1000);
        onView(withId(R.id.textView_SignUp)).check(matches(withText("Register")));
    }
}
