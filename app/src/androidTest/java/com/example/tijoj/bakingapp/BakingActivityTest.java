package com.example.tijoj.bakingapp;

import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

/**
 * Created by tijoj on 3/2/2018.
 */

@RunWith(AndroidJUnit4.class)

public class BakingActivityTest {


    @Rule
    public ActivityTestRule<BakingActivity> mActivityTestRule
            = new ActivityTestRule<>(BakingActivity.class);


    @Test
    public void clickCardButton_Calls_RecipesActivity() {
        //GOES INTO THE RECYCLER VIEW AND CLICKS FIRST ELEMENT
        onView(withId(R.id.recipes_recyclerView)).perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));
        //THIS PART CAN CHECK FOR ANY TEXT BUT THE SIMPLEST WAS TO CHECK FOR RECIPE INGREDIENTS
        onView(withText("RECIPE INGREDIENTS")).check(matches(isDisplayed()));
    }

}
