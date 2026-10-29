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

@RunWith(AndroidJUnit4.class)
public class MainActivityFlowTest {

    @Rule
    public ActivityScenarioRule<MainActivity> activityRule =
            new ActivityScenarioRule<>(MainActivity.class);

    @Test
    public void testStartChatButtonLaunchesChatWindow() {
        onView(withId(R.id.startChatButton))
                .check(matches(isDisplayed()))
                .perform(click());

        // Verify ChatWindow is launched by checking chatListView and sendButton visibility
        onView(withId(R.id.chatListView))
                .check(matches(isDisplayed()));

        onView(withId(R.id.sendButton))
                .check(matches(isDisplayed()));
    }

    @Test
    public void testListItemsButtonLaunchesListItemsActivity() {
        onView(withId(R.id.Button))
                .check(matches(isDisplayed()))
                .perform(click());

        // Verify ListItemsActivity is launched by checking imageButton visibility
        onView(withId(R.id.imageButton))
                .check(matches(isDisplayed()));
    }
}
