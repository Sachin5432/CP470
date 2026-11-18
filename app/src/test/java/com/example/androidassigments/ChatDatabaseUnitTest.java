package com.example.androidassigments;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

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
        assertTrue("Version num should be >= 1", ChatDatabaseHelper.VERSION_NUM >= 1);
    }

    @Test
    public void testTableCreationQueryFormat() {
        String query = ChatDatabaseHelper.getCreateTableQuery();
        assertNotNull(query);
        assertEquals("CREATE TABLE MessagesTable (_id INTEGER PRIMARY KEY AUTOINCREMENT, MESSAGE TEXT);", query);
    }
}