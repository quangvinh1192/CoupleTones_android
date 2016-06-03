package com.example.group26.coupletones.changeSpousePageTesting;

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
import static android.support.test.espresso.action.ViewActions.clearText;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.withHint;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

/**
 * Created by Jeremy on 6/3/2016.
 */

@RunWith(AndroidJUnit4.class)
@LargeTest
public class changeSpousetest {

    @Rule
    public ActivityTestRule<loginPage> testloginPage= new ActivityTestRule<>(loginPage.class);

    /*
     * First test: See if visiting spouse change options page works
     * Input: Valid account
     */
    @Test
    public void testVisitingSpouseOptions () {

        // Log in for testing
        onView(withId(R.id.emailTV_Login)).perform(typeText("quangvinh1192@gmail.com"), closeSoftKeyboard());
        onView(withId(R.id.password_Login)).perform(typeText("123456"), closeSoftKeyboard());
        onView(withId(R.id.submitButton_Login)).perform(click());

        //delay to load the information from firebase
        SystemClock.sleep(4000);
        onView(withId(R.id.title_main_menu)).check(matches(withText("Main Menu")));

        // Go to spouse options page by pressing the appropriate button
        onView(withId(R.id.spouseOptionsBtn)).perform(click());

        //delay to see what happen when we click the button
        SystemClock.sleep(1000);

        // Check to see if we're visiting the right page
        onView(withId(R.id.addSpouseTitle)).check(matches(withText("Add Your Spouse")));
    }

    /*
     * Second test: See if editing spouse works (two part test)
     * Input: Valid account and valid spouse to switch to
     */
    @Test
    public void testChangingSpousePartOne () {

        // Log in for testing
        onView(withId(R.id.emailTV_Login)).perform(typeText("quangvinh1192@gmail.com"), closeSoftKeyboard());
        onView(withId(R.id.password_Login)).perform(typeText("123456"), closeSoftKeyboard());
        onView(withId(R.id.submitButton_Login)).perform(click());

        //delay to load the information from firebase
        SystemClock.sleep(4000);
        onView(withId(R.id.title_main_menu)).check(matches(withText("Main Menu")));

        // Go to spouse options page by pressing the appropriate button
        onView(withId(R.id.spouseOptionsBtn)).perform(click());

        //delay to see what happen when we click the button
        SystemClock.sleep(1000);

        // Check to see if we're visiting the right page
        onView(withId(R.id.spouseEditTextID)).check(matches(withHint("Enter Your Spouse Email")));

        // Type in new spouse email
        onView(withId(R.id.spouseEditTextID)).perform(clearText());
        onView(withId(R.id.spouseEditTextID)).perform(typeText("jeremysiocon@gmail.com"), closeSoftKeyboard());

        // Exit the app and reenter it so spouse refreshes

    }

    /*
     * Second test: See if editing spouse works (two part test)
     * Input: Valid account and valid spouse to switch to
     */
    @Test
    public void testChangingSpousePartTwo () {

        // Log in for testing
        onView(withId(R.id.emailTV_Login)).perform(typeText("quangvinh1192@gmail.com"), closeSoftKeyboard());
        onView(withId(R.id.password_Login)).perform(typeText("123456"), closeSoftKeyboard());
        onView(withId(R.id.submitButton_Login)).perform(click());

        //delay to load the information from firebase
        SystemClock.sleep(4000);
        onView(withId(R.id.title_main_menu)).check(matches(withText("Main Menu")));

        // Go to spouse options page by pressing the appropriate button
        onView(withId(R.id.spouseOptionsBtn)).perform(click());

        //delay to see what happen when we click the button
        SystemClock.sleep(1000);

        // Check to see if new spouse email was saved
        onView(withId(R.id.spouseEditTextID)).check(matches(withText("jeremysiocon@gmail.com")));

    }
}
