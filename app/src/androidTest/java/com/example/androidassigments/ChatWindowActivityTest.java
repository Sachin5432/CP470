package com.example.androidassigments;

import static androidx.test.espresso.Espresso.onData;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.replaceText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.hasChildCount;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.anything;

import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
public class ChatWindowActivityTest {

    @Rule
    public ActivityScenarioRule<ChatWindow> activityRule =
            new ActivityScenarioRule<>(ChatWindow.class);

    private void send(String text) {
        onView(withId(R.id.chatEditText)).perform(replaceText(text), closeSoftKeyboard());
        onView(withId(R.id.sendButton)).perform(click());
    }

    @Test
    public void testSendMessageAndClearInput() {
        String testMessage = "Hello from Espresso Test";
        send(testMessage);

        onView(withId(R.id.chatEditText)).check(matches(withText("")));

        onData(anything())
                .inAdapterView(withId(R.id.chatListView))
                .atPosition(0)
                .onChildView(withId(R.id.messageText))
                .check(matches(withText(testMessage)));
    }

    @Test
    public void testEmptyAndWhitespaceMessagesAreNotAdded() {
        send("");
        send("   ");

        // No rows were added to the list
        onView(withId(R.id.chatListView)).check(matches(hasChildCount(0)));
    }

    @Test
    public void testSendMultipleMessagesKeepsOrder() {
        send("First message");
        send("Second message");

        onData(anything()).inAdapterView(withId(R.id.chatListView)).atPosition(0)
                .onChildView(withId(R.id.messageText)).check(matches(withText("First message")));
        onData(anything()).inAdapterView(withId(R.id.chatListView)).atPosition(1)
                .onChildView(withId(R.id.messageText)).check(matches(withText("Second message")));
    }

    @Test
    public void testRowsAlternateBetweenIncomingAndOutgoingLayouts() {
        send("one");
        send("two");
        send("three");

        // position 0 and 2 -> incoming row, position 1 -> outgoing row
        onData(anything()).inAdapterView(withId(R.id.chatListView)).atPosition(0)
                .onChildView(withId(R.id.imageIncoming)).check(matches(isDisplayed()));
        onData(anything()).inAdapterView(withId(R.id.chatListView)).atPosition(1)
                .onChildView(withId(R.id.imageOutgoing)).check(matches(isDisplayed()));
        onData(anything()).inAdapterView(withId(R.id.chatListView)).atPosition(2)
                .onChildView(withId(R.id.imageIncoming)).check(matches(isDisplayed()));
    }
}
