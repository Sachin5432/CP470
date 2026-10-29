package com.example.androidassigments;

import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.replaceText;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;

@RunWith(AndroidJUnit4.class)
public class LoginActivityFlowTest {

    @Rule
    public ActivityScenarioRule<LoginActivity> activityRule =
            new ActivityScenarioRule<>(LoginActivity.class);

    @Test
    public void testSuccessfulLoginFlow() {
        onView(withId(R.id.editTextEmail))
                .perform(replaceText("test@example.com"), closeSoftKeyboard());

        onView(withId(R.id.editTextPassword))
                .perform(typeText("password123"), closeSoftKeyboard());

        onView(withId(R.id.loginButton))
                .perform(click());

        // Verify transition to MainActivity
        onView(withId(R.id.startChatButton))
                .check(matches(isDisplayed()));
    }
}
