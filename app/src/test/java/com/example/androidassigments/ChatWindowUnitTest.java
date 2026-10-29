package com.example.androidassigments;

import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Unit tests directly testing {@link ChatMessageManager} app class and activity constants.
 */
public class ChatWindowUnitTest {

    private ChatMessageManager chatMessageManager;

    @Before
    public void setUp() {
        chatMessageManager = new ChatMessageManager();
    }

    @Test
    public void testAddValidMessage() {
        boolean result = chatMessageManager.addMessage("Hello World");
        assertTrue(result);
        assertEquals(1, chatMessageManager.getMessageCount());
        assertEquals("Hello World", chatMessageManager.getMessage(0));
    }

    @Test
    public void testAddEmptyOrWhitespaceMessageIgnored() {
        assertFalse(chatMessageManager.addMessage(""));
        assertFalse(chatMessageManager.addMessage("   "));
        assertFalse(chatMessageManager.addMessage(null));
        assertEquals(0, chatMessageManager.getMessageCount());
    }

    @Test
    public void testRowLayoutTypeLogic() {
        // Even indices are incoming (true), odd indices are outgoing (false)
        assertTrue(chatMessageManager.isIncoming(0));
        assertFalse(chatMessageManager.isOutgoing(0));

        assertFalse(chatMessageManager.isIncoming(1));
        assertTrue(chatMessageManager.isOutgoing(1));

        assertTrue(chatMessageManager.isIncoming(2));
        assertFalse(chatMessageManager.isOutgoing(2));

        assertFalse(chatMessageManager.isIncoming(-1));
        assertFalse(chatMessageManager.isOutgoing(-1));
    }

    @Test
    public void testMultipleMessages() {
        assertTrue(chatMessageManager.addMessage("Msg 0"));
        assertTrue(chatMessageManager.addMessage("Msg 1"));
        assertTrue(chatMessageManager.addMessage("Msg 2"));

        assertEquals(3, chatMessageManager.getMessageCount());
        assertEquals("Msg 0", chatMessageManager.getMessage(0));
        assertEquals("Msg 1", chatMessageManager.getMessage(1));
        assertEquals("Msg 2", chatMessageManager.getMessage(2));
    }

    @Test
    public void testGetMessageOutOfBounds() {
        chatMessageManager.addMessage("Msg 0");
        assertEquals("", chatMessageManager.getMessage(-1));
        assertEquals("", chatMessageManager.getMessage(10));
    }

    @Test
    public void testGetMessagesCopyAndClear() {
        chatMessageManager.addMessage("Msg 1");
        chatMessageManager.addMessage("Msg 2");

        List<String> copy = chatMessageManager.getMessages();
        assertEquals(2, copy.size());

        chatMessageManager.clear();
        assertEquals(0, chatMessageManager.getMessageCount());
        // Verify copy list was unaffected by clear
        assertEquals(2, copy.size());
    }

    @Test
    public void testActivityNameConstant() {
        assertEquals("ChatWindow", ChatWindow.ACTIVITY_NAME);
        assertEquals("MainActivity", MainActivity.ACTIVITY_NAME);
        assertEquals("LoginActivity", LoginActivity.ACTIVITY_NAME);
        assertEquals("ListItemsActivity", ListItemsActivity.ACTIVITY_NAME);
    }
}
