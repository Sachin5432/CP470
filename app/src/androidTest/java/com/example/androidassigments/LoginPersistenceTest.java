package com.example.androidassigments;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

import android.content.Context;

import androidx.test.core.app.ActivityScenario;
import androidx.test.core.app.ApplicationProvider;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * No ActivityScenarioRule here on purpose: the preference must be written BEFORE
 * LoginActivity starts, otherwise onCreate has already read the old value.
 */
@RunWith(AndroidJUnit4.class)
public class LoginPersistenceTest {

    private Context context;

    @Before
    public void setUp() {
        context = ApplicationProvider.getApplicationContext();
        context.getSharedPreferences("EmailPrefs", Context.MODE_PRIVATE).edit().clear().commit();
    }

    @Test
    public void testSavedEmailIsRestoredOnLaunch() {
        context.getSharedPreferences("EmailPrefs", Context.MODE_PRIVATE)
                .edit().putString("DefaultEmail", "saved_user@domain.com").commit();

        try (ActivityScenario<LoginActivity> scenario = ActivityScenario.launch(LoginActivity.class)) {
            onView(withId(R.id.editTextEmail)).check(matches(withText("saved_user@domain.com")));
        }
    }

    @Test
    public void testDefaultEmailShownWhenNothingSaved() {
        try (ActivityScenario<LoginActivity> scenario = ActivityScenario.launch(LoginActivity.class)) {
            onView(withId(R.id.editTextEmail)).check(matches(withText("email@domain.com")));
        }
    }
}
