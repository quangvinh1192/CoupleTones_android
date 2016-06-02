package com.example.group26.coupletones.tests;

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
import static android.support.test.espresso.assertion.ViewAssertions.doesNotExist;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static android.test.MoreAsserts.assertEquals;
import static junit.framework.Assert.assertTrue;

/**
 * Created by admin_me on 5/31/16.
 */
@RunWith(AndroidJUnit4.class)
@LargeTest
public class testLoginpage {
    @Rule
    public ActivityTestRule<loginPage> testloginPage= new ActivityTestRule<>(loginPage.class);
    /*
     * First test:
     * Input: correct email but no password
     */
    @Test
    public void testnopass(){
        onView(withId(R.id.emailTV_Login)).perform(typeText("quangvinh1192@gmail.com"), closeSoftKeyboard());
        onView(withId(R.id.password_Login)).perform(typeText(""), closeSoftKeyboard());
        onView(withId(R.id.submitButton_Login)).perform(click());
        onView(withText("Email or password is missing")).check(matches(isDisplayed()));
    }
    /*
     * Second test:
     * Input: no email but enter password
     */
    @Test
    public void testnomail(){
        onView(withId(R.id.emailTV_Login)).perform(typeText(""), closeSoftKeyboard());
        onView(withId(R.id.password_Login)).perform(typeText("1234"), closeSoftKeyboard());
        onView(withId(R.id.submitButton_Login)).perform(click());
        onView(withText("Email or password is missing")).check(matches(isDisplayed()));
    }
    /*@Test
    public void incorrect(){
        onView(withId(R.id.emailTV_Login)).perform(typeText("dasdas@gmail.com"), closeSoftKeyboard());
        onView(withId(R.id.password_Login)).perform(typeText("1234dasdsa56"), closeSoftKeyboard());
        onView(withId(R.id.submitButton_Login)).perform(click());
        onView(withText("Incorrect email or password")).check(matches(isDisplayed()));
    }*/
    /*
     * Third test:
     * Enter correct email and pass and no message are displayed
     */
    @Test
    public void correct(){
        onView(withId(R.id.emailTV_Login)).perform(typeText("quangvinh1192@gmail.com"), closeSoftKeyboard());
        onView(withId(R.id.password_Login)).perform(typeText("123456"), closeSoftKeyboard());
        onView(withId(R.id.submitButton_Login)).perform(click());
        onView(withText("")).check(doesNotExist());
    }
}
