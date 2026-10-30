package com.example.androidassigments;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.replaceText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.hasErrorText;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static org.junit.Assert.assertEquals;

import android.content.Context;

import androidx.test.core.app.ApplicationProvider;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
public class LoginActivityFlowTest {

    @Rule
    public ActivityScenarioRule<LoginActivity> activityRule =
            new ActivityScenarioRule<>(LoginActivity.class);

    private final Context context = ApplicationProvider.getApplicationContext();

    private void typeCredentials(String email, String password) {
        onView(withId(R.id.editTextEmail)).perform(replaceText(email), closeSoftKeyboard());
        onView(withId(R.id.editTextPassword)).perform(replaceText(password), closeSoftKeyboard());
    }

    @Test
    public void testSuccessfulLoginFlow() {
        typeCredentials("test@example.com", "password123");
        onView(withId(R.id.loginButton)).perform(click());

        // MainActivity is showing
        onView(withId(R.id.startChatButton)).check(matches(isDisplayed()));
    }

    @Test
    public void testSuccessfulLoginSavesEmail() {
        typeCredentials("saved_after_login@example.com", "password123");
        onView(withId(R.id.loginButton)).perform(click());

        String stored = context.getSharedPreferences("EmailPrefs", Context.MODE_PRIVATE)
                .getString("DefaultEmail", "");
        assertEquals("saved_after_login@example.com", stored);
    }

    @Test
    public void testInvalidEmailShowsErrorAndStaysOnLogin() {
        typeCredentials("not-an-email", "password123");
        onView(withId(R.id.loginButton)).perform(click());

        onView(withId(R.id.editTextEmail))
                .check(matches(hasErrorText(context.getString(R.string.invalid_email))));
        onView(withId(R.id.loginButton)).check(matches(isDisplayed()));
    }

    @Test
    public void testEmptyPasswordShowsErrorAndStaysOnLogin() {
        typeCredentials("user@example.com", "");
        onView(withId(R.id.loginButton)).perform(click());

        onView(withId(R.id.editTextPassword))
                .check(matches(hasErrorText(context.getString(R.string.empty_password))));
        onView(withId(R.id.loginButton)).check(matches(isDisplayed()));
    }
}
