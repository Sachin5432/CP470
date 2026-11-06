package com.example.androidassigments;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Unit tests directly testing {@link ToolbarMessageManager} production class.
 */
public class TestToolbarUnitTest {

    private ToolbarMessageManager messageManager;

    @Before
    public void setUp() {
        messageManager = new ToolbarMessageManager("You selected item 1");
    }

    @Test
    public void testDefaultMessage() {
        assertEquals("You selected item 1", messageManager.getMessage());
        assertEquals("You selected item 1", messageManager.getDefaultMessage());
        assertTrue(messageManager.isDefaultMessage());
    }

    @Test
    public void testUpdateValidMessage() {
        boolean updated = messageManager.updateMessage("New Custom Message");
        assertTrue(updated);
        assertEquals("New Custom Message", messageManager.getMessage());
        assertFalse(messageManager.isDefaultMessage());
    }

    @Test
    public void testUpdateEmptyOrNullMessageIgnored() {
        assertFalse(messageManager.updateMessage(""));
        assertEquals("You selected item 1", messageManager.getMessage());
        assertTrue(messageManager.isDefaultMessage());

        assertFalse(messageManager.updateMessage("   "));
        assertEquals("You selected item 1", messageManager.getMessage());

        assertFalse(messageManager.updateMessage(null));
        assertEquals("You selected item 1", messageManager.getMessage());
    }

    @Test
    public void testNullDefaultMessageHandledGracefully() {
        ToolbarMessageManager nullManager = new ToolbarMessageManager(null);
        assertEquals("", nullManager.getMessage());
        assertEquals("", nullManager.getDefaultMessage());
    }
}