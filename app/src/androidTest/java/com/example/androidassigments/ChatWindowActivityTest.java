package com.example.androidassigments;

import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.onData;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.anything;

@RunWith(AndroidJUnit4.class)
public class ChatWindowActivityTest {

    @Rule
    public ActivityScenarioRule<ChatWindow> activityRule =
            new ActivityScenarioRule<>(ChatWindow.class);

    @Test
    public void testSendMessageAndClearInput() {
        String testMessage = "Hello from Espresso Test";

        onView(withId(R.id.chatEditText))
                .perform(typeText(testMessage), closeSoftKeyboard());

        onView(withId(R.id.sendButton))
                .perform(click());

        // Verify EditText is cleared after sending
        onView(withId(R.id.chatEditText))
                .check(matches(withText("")));

        // Verify message is present in ListView at position 0
        onData(anything())
                .inAdapterView(withId(R.id.chatListView))
                .atPosition(0)
                .onChildView(withId(R.id.messageText))
                .check(matches(withText(testMessage)));
    }

    @Test
    public void testSendMultipleMessages() {
        String message1 = "First message";
        String message2 = "Second message";

        onView(withId(R.id.chatEditText))
                .perform(typeText(message1), closeSoftKeyboard());
        onView(withId(R.id.sendButton))
                .perform(click());

        onView(withId(R.id.chatEditText))
                .perform(typeText(message2), closeSoftKeyboard());
        onView(withId(R.id.sendButton))
                .perform(click());

        onData(anything())
                .inAdapterView(withId(R.id.chatListView))
                .atPosition(0)
                .onChildView(withId(R.id.messageText))
                .check(matches(withText(message1)));

        onData(anything())
                .inAdapterView(withId(R.id.chatListView))
                .atPosition(1)
                .onChildView(withId(R.id.messageText))
                .check(matches(withText(message2)));
    }
}
