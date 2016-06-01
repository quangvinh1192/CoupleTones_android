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

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

/**
 * Created by admin_me on 5/31/16.
 */
@RunWith(AndroidJUnit4.class)
@LargeTest
public class testLoginpage {
    @Rule
    public ActivityTestRule<loginPage> testloginPage= new ActivityTestRule<>(loginPage.class);

    @Test
    public void sayHello(){
        //onView(withId(R.id.editText)).perform(typeText("vinh"), ViewActions.closeSoftKeyboard());
        //onView(withId(R.id.button)).perform(click());
        //String expectedText= "vinh";
        //onView(withId(R.id.textView)).check(matches(withText(expectedText)));
        //onView(withId(R.id.editText)).check(matches(withText(expectedText)));
    }
}
