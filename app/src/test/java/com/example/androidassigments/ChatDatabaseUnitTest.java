package com.example.androidassigments;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * Unit tests for ChatDatabaseHelper constants and schema definitions.
 */
public class ChatDatabaseUnitTest {

    @Test
    public void testDatabaseHelperStaticConstants() {
        assertEquals("Messages.db", ChatDatabaseHelper.DATABASE_NAME);
        assertEquals("MessagesTable", ChatDatabaseHelper.TABLE_NAME);
        assertEquals("_id", ChatDatabaseHelper.KEY_ID);
        assertEquals("MESSAGE", ChatDatabaseHelper.KEY_MESSAGE);
        assertEquals("ChatDatabaseHelper", ChatDatabaseHelper.ACTIVITY_NAME);
        assertEquals(1, ChatDatabaseHelper.VERSION_NUM);
    }

    @Test
    public void testTableCreationQueryFormat() {
        String query = "CREATE TABLE " + ChatDatabaseHelper.TABLE_NAME + " ("
                + ChatDatabaseHelper.KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + ChatDatabaseHelper.KEY_MESSAGE + " TEXT);";
        assertNotNull(query);
        assertEquals("CREATE TABLE MessagesTable (_id INTEGER PRIMARY KEY AUTOINCREMENT, MESSAGE TEXT);", query);
    }
}