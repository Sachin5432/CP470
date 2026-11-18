package com.example.androidassigments;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import androidx.test.core.app.ActivityScenario;
import androidx.test.core.app.ApplicationProvider;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.onData;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.replaceText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.anything;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@RunWith(AndroidJUnit4.class)
public class ChatDatabaseInstrumentedTest {

    private Context context;
    private ChatDatabaseHelper dbHelper;
    private SQLiteDatabase database;

    @Before
    public void setUp() {
        context = ApplicationProvider.getApplicationContext();
        context.deleteDatabase(ChatDatabaseHelper.DATABASE_NAME);
        dbHelper = new ChatDatabaseHelper(context);
        database = dbHelper.getWritableDatabase();
    }

    @After
    public void tearDown() {
        if (database != null && database.isOpen()) {
            database.close();
        }
        if (dbHelper != null) {
            dbHelper.close();
        }
        if (context != null) {
            context.deleteDatabase(ChatDatabaseHelper.DATABASE_NAME);
        }
    }

    @Test
    public void testDatabaseCreationAndColumnNames() {
        Cursor cursor = database.query(
                ChatDatabaseHelper.TABLE_NAME,
                null,
                null, null, null, null, null
        );

        assertEquals(2, cursor.getColumnCount());
        assertEquals(ChatDatabaseHelper.KEY_ID, cursor.getColumnName(0));
        assertEquals(ChatDatabaseHelper.KEY_MESSAGE, cursor.getColumnName(1));
        cursor.close();
    }

    @Test
    public void testDatabaseInsertAndQuery() {
        ContentValues cv = new ContentValues();
        String testMsg = "Test Persistent Message";
        cv.put(ChatDatabaseHelper.KEY_MESSAGE, testMsg);

        long id = database.insert(ChatDatabaseHelper.TABLE_NAME, null, cv);
        assertTrue(id != -1);

        Cursor cursor = database.query(
                ChatDatabaseHelper.TABLE_NAME,
                new String[]{ChatDatabaseHelper.KEY_MESSAGE},
                ChatDatabaseHelper.KEY_ID + " = ?",
                new String[]{String.valueOf(id)},
                null, null, null
        );

        assertTrue(cursor.moveToFirst());
        assertEquals(testMsg, cursor.getString(0));
        cursor.close();
    }

    @Test
    public void testDatabaseUpgradeClearsTable() {
        ContentValues cv = new ContentValues();
        cv.put(ChatDatabaseHelper.KEY_MESSAGE, "Old Message Before Upgrade");
        database.insert(ChatDatabaseHelper.TABLE_NAME, null, cv);

        // Call onUpgrade
        dbHelper.onUpgrade(database, 1, 2);

        Cursor cursor = database.query(
                ChatDatabaseHelper.TABLE_NAME,
                null, null, null, null, null, null
        );

        assertEquals(0, cursor.getCount());
        cursor.close();
    }

    @Test
    public void testChatWindowSendsAndPersistsMessageInDatabase() {
        try (ActivityScenario<ChatWindow> scenario = ActivityScenario.launch(ChatWindow.class)) {
            String msg = "Hello SQLite Database";

            onView(withId(R.id.chatEditText)).perform(replaceText(msg), closeSoftKeyboard());
            onView(withId(R.id.sendButton)).perform(click());

            // Verify message appears in ListView
            onData(anything())
                    .inAdapterView(withId(R.id.chatListView))
                    .atPosition(0)
                    .onChildView(withId(R.id.messageText))
                    .check(matches(withText(msg)));
        }
    }
}