package com.example.bmoreira.paytmchallenge;

import android.support.test.rule.ActivityTestRule;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import static android.support.test.espresso.Espresso.onData;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withSpinnerText;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.core.AllOf.allOf;

/**
 * Created by bruco on 2017-12-15.
 */

public class MainActivityUITest {

    @Rule
    public ActivityTestRule<MainActivity> activityTestRule = new ActivityTestRule<>(MainActivity.class);

    MainActivity activity = null;

    @Before
    public void setUp() throws Exception {
        activity = activityTestRule.getActivity();
    }

    @Test
    public void checkIfLaunched() {
        onView(withId(R.id.sp_currency))
                .check(matches(isDisplayed()));
        onView(withText("$0.00"))
                .check(matches(isDisplayed()));
    }

    @Test
    public void updateBaseCurrencyList() throws Throwable {
        //check if the spinner was updated
        final String[] items = {"TEST2", "TEST1", "TEST3"};

        activityTestRule.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                activity.updateBaseCurrencyList(items);
            }
        });

        onView(withId(R.id.sp_currency)).perform(click());
        onData(allOf(is(instanceOf(String.class)), is("TEST3"))).perform(click());
        onView(withId(R.id.sp_currency)).check(matches(withSpinnerText("TEST3")));
    }
}