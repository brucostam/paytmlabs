package com.example.bmoreira.paytmchallenge;

import android.support.test.rule.ActivityTestRule;
import android.support.v7.widget.AppCompatTextView;
import android.widget.TextView;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import static android.support.test.espresso.Espresso.onData;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.Visibility.INVISIBLE;
import static android.support.test.espresso.matcher.ViewMatchers.Visibility.VISIBLE;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withEffectiveVisibility;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withSpinnerText;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.core.AllOf.allOf;

/**
 * Created by bruco on 2017-12-15.
 */

public class MainActivityTest {

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
        onView(withId(R.id.tv_value))
                .check(matches(isDisplayed()));
    }

    @Test
    public void onCreate() throws Exception {
        //check if adapter is set to gridview
        //check if presenter.Oncreate is called
    }

    @Test
    public void onDestroy() throws Exception {
        //check if presenter is destroyed
    }

    @Test
    public void updateExchangeRatesList() throws Exception {
        //check if adapter was notified
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

    @Test
    public void checkIfHideEmptyListStateMakesViewInvisible() throws Throwable {
        //check if empty list state is invisible
        activityTestRule.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                activity.hideEmptyListState();
            }
        });

        onView(withId(R.id.img_empty_state))
                .check(matches(withEffectiveVisibility(INVISIBLE)));
    }

    @Test
    public void checkIfShowEmptyListStateMakesViewVisible() throws Throwable {
        //check if empty list state is visible
        activityTestRule.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                activity.showEmptyListState();
            }
        });

        onView(withId(R.id.img_empty_state))
                .check(matches(withEffectiveVisibility(VISIBLE)));
    }

    @Test
    public void checkIfHideGridMakesGridInvisible() throws Throwable {
        //check if grid list is invisible
        activityTestRule.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                activity.hideGrid();
            }
        });
        onView(withId(R.id.gl_exchanges))
                .check(matches(withEffectiveVisibility(INVISIBLE)));
    }

    @Test
    public void checkIfShowGridMakesGridVisible() throws Throwable {
        //check if grid list is visible
        activityTestRule.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                activity.showGrid();
            }
        });
        onView(withId(R.id.gl_exchanges))
                .check(matches(withEffectiveVisibility(VISIBLE)));
    }

    // Check user can change value and presenter is notified

    // Check user can change spinner value and presenter is notified

}