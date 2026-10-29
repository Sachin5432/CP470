package com.example.androidassigments;

import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

@RunWith(AndroidJUnit4.class)
public class ListItemsActivityFlowTest {

    @Rule
    public ActivityScenarioRule<ListItemsActivity> activityRule =
            new ActivityScenarioRule<>(ListItemsActivity.class);

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
    public void testCheckBoxDisplaysDialog() {
        onView(withId(R.id.checkBox))
                .perform(click());

        // Verify Dialog Title and Cancel button are displayed
        onView(withText(R.string.dialog_title))
                .check(matches(isDisplayed()));

        onView(withText(R.string.cancel))
                .perform(click());
    }
}
