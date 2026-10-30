package com.example.androidassigments;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.intent.Intents.intended;
import static androidx.test.espresso.intent.Intents.intending;
import static androidx.test.espresso.intent.matcher.IntentMatchers.hasAction;
import static androidx.test.espresso.matcher.ViewMatchers.isChecked;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.isNotChecked;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.junit.Assert.assertEquals;

import android.app.Activity;
import android.app.Instrumentation;
import android.provider.MediaStore;

import androidx.test.core.app.ActivityScenario;
import androidx.test.core.app.ApplicationProvider;
import androidx.test.espresso.intent.rule.IntentsRule;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.rule.GrantPermissionRule;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.RuleChain;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
public class ListItemsActivityFlowTest {

    private final ActivityScenarioRule<ListItemsActivity> scenarioRule =
            new ActivityScenarioRule<>(ListItemsActivity.class);

    // Order matters: grant permission first, record intents, then launch the activity
    @Rule
    public RuleChain chain = RuleChain
            .outerRule(GrantPermissionRule.grant(android.Manifest.permission.CAMERA))
            .around(new IntentsRule())
            .around(scenarioRule);

    @Test
    public void testUiElementsDisplayed() {
        onView(withId(R.id.imageButton)).check(matches(isDisplayed()));
        onView(withId(R.id.switchView)).check(matches(isDisplayed()));
        onView(withId(R.id.checkBox)).check(matches(isDisplayed()));
        onView(withId(R.id.radioButton)).check(matches(isDisplayed()));
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
    public void testCheckBoxDialogCancelUnchecksBox() {
        onView(withId(R.id.checkBox)).perform(click());

        onView(withText(R.string.dialog_title)).check(matches(isDisplayed()));
        onView(withText(R.string.cancel)).perform(click());

        onView(withId(R.id.checkBox)).check(matches(isNotChecked()));
    }

    @Test
    public void testCheckBoxDialogOkFinishesWithResult() {
        try (ActivityScenario<ListItemsActivity> scenario =
                     ActivityScenario.launchActivityForResult(ListItemsActivity.class)) {
            onView(withId(R.id.checkBox)).perform(click());
            onView(withText(R.string.ok)).perform(click());

            Instrumentation.ActivityResult result = scenario.getResult();
            assertEquals(Activity.RESULT_OK, result.getResultCode());
            assertEquals(
                    ApplicationProvider.getApplicationContext()
                            .getString(R.string.response_default),
                    result.getResultData().getStringExtra("Response"));
        }
    }

    @Test
    public void testImageButtonLaunchesCameraIntent() {
        // Stub the camera so the real camera app never opens
        intending(hasAction(MediaStore.ACTION_IMAGE_CAPTURE))
                .respondWith(new Instrumentation.ActivityResult(Activity.RESULT_CANCELED, null));

        onView(withId(R.id.imageButton)).perform(click());

        intended(hasAction(MediaStore.ACTION_IMAGE_CAPTURE));
    }
}
