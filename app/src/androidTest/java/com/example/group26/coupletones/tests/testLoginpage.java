package com.example.group26.coupletones.tests;

import android.support.test.espresso.action.ViewActions;
import android.support.test.filters.LargeTest;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.example.group26.coupletones.R;
import com.example.group26.coupletones.loginPage;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.InstrumentationRegistry.getInstrumentation;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static junit.framework.Assert.assertTrue;

/**
 * Created by admin_me on 5/31/16.
 */
@RunWith(AndroidJUnit4.class)
@LargeTest
public class testLoginpage {
    @Rule
    public ActivityTestRule<loginPage> testloginPage= new ActivityTestRule<>(loginPage.class);

    @Test
    public void testLogin(){
        onView(withId(R.id.emailTV)).perform(typeText("quangvinh1192@gmail.com"), closeSoftKeyboard());
        onView(withId(R.id.passwordTV)).perform(typeText(""), closeSoftKeyboard());
        onView(withId(R.id.submitButton)).perform(click());
        onView(withText("Email or password is missing")).check(matches(isDisplayed()));
    }
}
