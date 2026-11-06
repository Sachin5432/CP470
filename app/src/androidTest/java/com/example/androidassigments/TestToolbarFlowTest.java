package com.example.androidassigments;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.Espresso.openActionBarOverflowOrOptionsMenu;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.replaceText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static androidx.test.platform.app.InstrumentationRegistry.getInstrumentation;

import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
public class TestToolbarFlowTest {

    @Rule
    public ActivityScenarioRule<MainActivity> mainActivityRule =
            new ActivityScenarioRule<>(MainActivity.class);

    @Test
    public void testMainActivityLaunchesTestToolbar() {
        onView(withId(R.id.testToolbarButton))
                .check(matches(isDisplayed()))
                .perform(click());

        onView(withId(R.id.toolbar))
                .check(matches(isDisplayed()));

        onView(withId(R.id.fab))
                .check(matches(isDisplayed()));
    }

    @Test
    public void testToolbarThreeIconsAndOverflowAboutItem() {
        onView(withId(R.id.testToolbarButton)).perform(click());

        // Verify all 3 toolbar action items with icons are displayed
        onView(withId(R.id.action_one)).check(matches(isDisplayed()));
        onView(withId(R.id.action_two)).check(matches(isDisplayed()));
        onView(withId(R.id.action_three)).check(matches(isDisplayed()));

        // Open overflow settings menu
        openActionBarOverflowOrOptionsMenu(getInstrumentation().getTargetContext());

        // Verify About item is displayed in overflow menu and click it
        onView(withText("About"))
                .check(matches(isDisplayed()))
                .perform(click());
    }

    @Test
    public void testToolbarMenuItemOneShowsSnackbar() {
        onView(withId(R.id.testToolbarButton)).perform(click());

        onView(withId(R.id.action_one))
                .check(matches(isDisplayed()))
                .perform(click());

        onView(withText("You selected item 1"))
                .check(matches(isDisplayed()));
    }

    @Test
    public void testToolbarMenuItemTwoCancelButtonDoesNotFinishActivity() {
        onView(withId(R.id.testToolbarButton)).perform(click());

        onView(withId(R.id.action_two)).perform(click());

        // Dialog should be displayed with title "Do you want to go back?"
        onView(withText("Do you want to go back?"))
                .check(matches(isDisplayed()));

        // Click Cancel
        onView(withText("Cancel")).perform(click());

        // TestToolbar activity should still be displayed
        onView(withId(R.id.toolbar)).check(matches(isDisplayed()));
    }

    @Test
    public void testToolbarMenuItemThreeCustomDialogUpdatesSnackbarMessage() {
        onView(withId(R.id.testToolbarButton)).perform(click());

        // Click Item 3
        onView(withId(R.id.action_three)).perform(click());

        // Verify Custom Dialog components
        onView(withId(R.id.customDialogIcon)).check(matches(isDisplayed()));
        onView(withId(R.id.customDialogEditText)).check(matches(isDisplayed()));

        // Type new message
        String newMsg = "Custom Updated Message";
        onView(withId(R.id.customDialogEditText))
                .perform(replaceText(newMsg), closeSoftKeyboard());

        // Click OK
        onView(withText("OK")).perform(click());

        // Click Item 1 in toolbar
        onView(withId(R.id.action_one)).perform(click());

        // Verify updated Snackbar message
        onView(withText(newMsg)).check(matches(isDisplayed()));
    }

    @Test
    public void testToolbarMenuItemThreeEmptyMessageSubmissionIgnored() {
        onView(withId(R.id.testToolbarButton)).perform(click());

        // Click Item 3
        onView(withId(R.id.action_three)).perform(click());

        // Submit blank string
        onView(withId(R.id.customDialogEditText))
                .perform(replaceText("   "), closeSoftKeyboard());
        onView(withText("OK")).perform(click());

        // Click Item 1 in toolbar
        onView(withId(R.id.action_one)).perform(click());

        // Should retain default message
        onView(withText("You selected item 1")).check(matches(isDisplayed()));
    }

    @Test
    public void testToolbarMenuItemTwoOkButtonFinishesActivity() {
        onView(withId(R.id.testToolbarButton)).perform(click());

        onView(withId(R.id.action_two)).perform(click());

        // Click OK button on dialog
        onView(withText("OK")).perform(click());

        // Should return to MainActivity where testToolbarButton is displayed
        onView(withId(R.id.testToolbarButton)).check(matches(isDisplayed()));
    }

    @Test
    public void testFabClickShowsSnackbar() {
        onView(withId(R.id.testToolbarButton)).perform(click());

        onView(withId(R.id.fab)).perform(click());

        onView(withText("FAB clicked! Welcome to TestToolbar."))
                .check(matches(isDisplayed()));
    }
}