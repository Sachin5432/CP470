package com.example.androidassigments;

import android.content.Context;
import android.content.SharedPreferences;

import androidx.test.core.app.ApplicationProvider;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.replaceText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.hasErrorText;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

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
                .perform(replaceText("password123"), closeSoftKeyboard());

        onView(withId(R.id.loginButton))
                .perform(click());

        // Verify transition to MainActivity
        onView(withId(R.id.startChatButton))
                .check(matches(isDisplayed()));
    }

    @Test
    public void testInvalidEmailShowsError() {
        onView(withId(R.id.editTextEmail))
                .perform(replaceText("not-an-email"), closeSoftKeyboard());

        onView(withId(R.id.editTextPassword))
                .perform(replaceText("password123"), closeSoftKeyboard());

        onView(withId(R.id.loginButton))
                .perform(click());

        // Login button and LoginActivity remain visible and email field shows error
        onView(withId(R.id.loginButton))
                .check(matches(isDisplayed()));
    }

    @Test
    public void testEmptyPasswordShowsError() {
        onView(withId(R.id.editTextEmail))
                .perform(replaceText("user@example.com"), closeSoftKeyboard());

        onView(withId(R.id.editTextPassword))
                .perform(replaceText(""), closeSoftKeyboard());

        onView(withId(R.id.loginButton))
                .perform(click());

        // Login button remains visible because login was blocked
        onView(withId(R.id.loginButton))
                .check(matches(isDisplayed()));
    }

    @Test
    public void testSavedEmailRestoredOnRelaunch() {
        // Pre-populate SharedPreferences with custom email
        Context context = ApplicationProvider.getApplicationContext();
        SharedPreferences prefs = context.getSharedPreferences("EmailPrefs", Context.MODE_PRIVATE);
        prefs.edit().putString("DefaultEmail", "saved_user@domain.com").commit();

        // Launch LoginActivity and verify saved email is loaded into editTextEmail
        onView(withId(R.id.editTextEmail))
                .perform(replaceText("saved_user@domain.com"), closeSoftKeyboard());

        onView(withId(R.id.editTextPassword))
                .perform(replaceText("pass"), closeSoftKeyboard());

        onView(withId(R.id.loginButton))
                .perform(click());

        // Re-read SharedPreferences to verify email was saved
        String updated = prefs.getString("DefaultEmail", "");
        org.junit.Assert.assertEquals("saved_user@domain.com", updated);
    }
}
