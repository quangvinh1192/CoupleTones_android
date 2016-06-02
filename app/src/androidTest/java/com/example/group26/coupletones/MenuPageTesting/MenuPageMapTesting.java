package com.example.group26.coupletones.MenuPageTesting;

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
public class MenuPageMapTesting {
    @Rule
    public ActivityTestRule<loginPage> testloginPage= new ActivityTestRule<>(loginPage.class);
    /*
     This test tests Sign Up button from log in page
     Input: When users choose sign up option, app will open a new layout and
     users can register a new account
     */
    @Test
    public void gotoMainPage() {
        onView(withId(R.id.emailTV_Login)).perform(typeText("quangvinh1192@gmail.com"), closeSoftKeyboard());
        onView(withId(R.id.password_Login)).perform(typeText("123456"), closeSoftKeyboard());
        onView(withId(R.id.submitButton_Login)).perform(click());
        SystemClock.sleep(4000);
        onView(withId(R.id.title_main_menu)).check(matches(withText("Main Menu")));
        onView(withId(R.id.goToMapBtn)).perform(click());
        SystemClock.sleep(1000);
        onView(withId(R.id.addPlaceBtnID)).check(matches(withText("Add Favorite Place")));
    }
}
