package com.example.androidassigments;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Unit test for TestToolbar message management and action helper logic.
 */
public class TestToolbarUnitTest {

    public static class ToolbarMessageState {
        private String currentMessage;

        public ToolbarMessageState(String defaultMessage) {
            this.currentMessage = defaultMessage;
        }

        public String getCurrentMessage() {
            return currentMessage;
        }

        public boolean updateMessage(String newMessage) {
            if (newMessage == null || newMessage.trim().isEmpty()) {
                return false;
            }
            this.currentMessage = newMessage.trim();
            return true;
        }
    }

    private ToolbarMessageState messageState;

    @Before
    public void setUp() {
        messageState = new ToolbarMessageState("You selected item 1");
    }

    @Test
    public void testDefaultMessage() {
        assertEquals("You selected item 1", messageState.getCurrentMessage());
    }

    @Test
    public void testUpdateValidMessage() {
        boolean updated = messageState.updateMessage("New Custom Message");
        assertTrue(updated);
        assertEquals("New Custom Message", messageState.getCurrentMessage());
    }

    @Test
    public void testUpdateEmptyOrNullMessageIgnored() {
        assertFalse(messageState.updateMessage(""));
        assertEquals("You selected item 1", messageState.getCurrentMessage());

        assertFalse(messageState.updateMessage("   "));
        assertEquals("You selected item 1", messageState.getCurrentMessage());

        assertFalse(messageState.updateMessage(null));
        assertEquals("You selected item 1", messageState.getCurrentMessage());
    }
}