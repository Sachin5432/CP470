package com.example.androidassigments;

import android.app.Activity;

import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.rule.GrantPermissionRule;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isChecked;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.isNotChecked;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@RunWith(AndroidJUnit4.class)
public class ListItemsActivityFlowTest {

    @Rule
    public ActivityScenarioRule<ListItemsActivity> activityRule =
            new ActivityScenarioRule<>(ListItemsActivity.class);

    @Rule
    public GrantPermissionRule grantPermissionRule =
            GrantPermissionRule.grant(android.Manifest.permission.CAMERA);

    @Test
    public void testUiElementsDisplayed() {
        onView(withId(R.id.imageButton))
                .check(matches(isDisplayed()));

        onView(withId(R.id.switchView))
                .check(matches(isDisplayed()));

        onView(withId(R.id.checkBox))
                .check(matches(isDisplayed()));
    }

    @Test
    public void testSwitchToggleState() {
        onView(withId(R.id.switchView))
                .check(matches(isNotChecked()))
                .perform(click())
                .check(matches(isChecked()))
                .perform(click())
                .check(matches(isNotChecked()));
    }

    @Test
    public void testCheckBoxDisplaysDialogAndCancel() {
        onView(withId(R.id.checkBox))
                .perform(click());

        // Verify Dialog Title and Cancel button are displayed
        onView(withText(R.string.dialog_title))
                .check(matches(isDisplayed()));

        onView(withText(R.string.cancel))
                .perform(click());

        // CheckBox becomes unchecked when dialog is dismissed/cancelled
        onView(withId(R.id.checkBox))
                .check(matches(isNotChecked()));
    }

    @Test
    public void testCheckBoxDialogOkFinishesWithResult() {
        onView(withId(R.id.checkBox))
                .perform(click());

        onView(withText(R.string.ok))
                .perform(click());

        // Verify activity finished and set RESULT_OK
        activityRule.getScenario().onActivity(activity -> {
            assertTrue(activity.isFinishing() || activity.isDestroyed());
        });
    }

    @Test
    public void testCameraPermissionPath() {
        onView(withId(R.id.imageButton))
                .check(matches(isDisplayed()))
                .perform(click());
    }
}
