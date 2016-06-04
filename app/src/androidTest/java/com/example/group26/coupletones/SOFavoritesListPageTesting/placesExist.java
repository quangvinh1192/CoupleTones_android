package com.example.group26.coupletones.SOFavoritesListPageTesting;

import android.support.test.filters.LargeTest;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.example.group26.coupletones.SOListOfPlaces;
import com.example.group26.coupletones.loginPage;

import org.junit.runner.RunWith;

/**
 * Created by jkapi on 6/3/2016.
 */
@RunWith(AndroidJUnit4.class)
@LargeTest
public class placesExist {
    public ActivityTestRule<loginPage> testloginPage= new ActivityTestRule<>(loginPage.class);


}
