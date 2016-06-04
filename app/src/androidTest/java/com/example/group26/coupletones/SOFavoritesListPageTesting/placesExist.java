package com.example.group26.coupletones.SOFavoritesListPageTesting;

import android.os.SystemClock;
import android.support.test.filters.LargeTest;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.example.group26.coupletones.R;
import com.example.group26.coupletones.SOListOfPlaces;
import com.example.group26.coupletones.loginPage;

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
 * Created by jkapi on 6/3/2016.
 */
@RunWith(AndroidJUnit4.class)
@LargeTest
public class placesExist {
    @Rule
    public ActivityTestRule<loginPage> testloginPage= new ActivityTestRule<>(loginPage.class);

    /*
     * First test: Check if the SO Favorite Places Page is working
     * Input: Valid account w/SO
     */
    @Test
    public void TestOpeningPlacesPage () {

        // Login on Vinhs account
        onView(withId(R.id.emailTV_Login)).perform(typeText("quangvinh1192@gmail.com"), closeSoftKeyboard());
        onView(withId(R.id.password_Login)).perform(typeText("123456"), closeSoftKeyboard());
        onView(withId(R.id.submitButton_Login)).perform(click());

        // Wait for menu screen to load & confirm you are at menu
        SystemClock.sleep(4000);
        onView(withId(R.id.title_main_menu)).check(matches(withText("Main Menu")));

        // Go to SO Favorites page
        onView(withId(R.id.viewSOFavsBtn)).perform(click());

        // Wait for page to load & confirm you're at the page
        SystemClock.sleep(1000);
        onView(withId(R.id.textView_spouseFVLocation)).check(matches(withText("Spouse Favorite Locations:")));
        onView(withId(R.id.viewSOFavsBtn)).check(matches(withText("View in Map")));
    }

    /*
     * Second Test: Check if SO Favorite Places Page Map is working
     * Input: Valid account w/SO
     */
    @Test
    public void TestOpeningPlacesMap () {

        // Login on Vinhs account
        onView(withId(R.id.emailTV_Login)).perform(typeText("quangvinh1192@gmail.com"), closeSoftKeyboard());
        onView(withId(R.id.password_Login)).perform(typeText("123456"), closeSoftKeyboard());
        onView(withId(R.id.submitButton_Login)).perform(click());

        // Wait for menu screen to load & confirm you are at menu
        SystemClock.sleep(4000);
        onView(withId(R.id.title_main_menu)).check(matches(withText("Main Menu")));

        // Go to SO Favorites page
        onView(withId(R.id.viewSOFavsBtn)).perform(click());

        // Wait for page to load & confirm you're at the page
        SystemClock.sleep(1000);
        onView(withId(R.id.textView_spouseFVLocation)).check(matches(withText("Spouse Favorite Locations:")));
        onView(withId(R.id.viewSOFavsBtn)).check(matches(withText("View in Map")));

        // Click on page to go to map
        onView(withId(R.id.viewSOFavsBtn)).perform(click());

        // Wait to go to map & confirm that you're on the map
        SystemClock.sleep(1000);
        onView(withId(R.id.SOFavmap)).check(matches(isDisplayed()));
    }
}
