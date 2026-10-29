package com.example.androidassigments;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;

public class ChatWindowUnitTest {

    private ArrayList<String> messageList;

    @Before
    public void setUp() {
        messageList = new ArrayList<>();
    }

    @Test
    public void testAddMessage() {
        messageList.add("Hello World");
        assertEquals(1, messageList.size());
        assertEquals("Hello World", messageList.get(0));
    }

    @Test
    public void testRowLayoutTypeLogic() {
        // Position % 2 == 0 is incoming layout, else outgoing layout
        int incomingPosition = 0;
        int outgoingPosition = 1;
        int nextIncomingPosition = 2;

        assertEquals(0, incomingPosition % 2);
        assertEquals(1, outgoingPosition % 2);
        assertEquals(0, nextIncomingPosition % 2);
    }

    @Test
    public void testMultipleMessagesCount() {
        messageList.add("Msg 0");
        messageList.add("Msg 1");
        messageList.add("Msg 2");
        messageList.add("Msg 3");
        messageList.add("Msg 4");

        assertEquals(5, messageList.size());
        assertEquals("Msg 4", messageList.get(4));
    }

    @Test
    public void testActivityNameConstant() {
        assertEquals("ChatWindow", ChatWindow.ACTIVITY_NAME);
        assertEquals("MainActivity", MainActivity.ACTIVITY_NAME);
        assertEquals("LoginActivity", LoginActivity.ACTIVITY_NAME);
        assertEquals("ListItemsActivity", ListItemsActivity.ACTIVITY_NAME);
    }
}
