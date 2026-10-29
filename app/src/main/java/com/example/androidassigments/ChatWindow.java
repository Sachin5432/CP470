package com.example.androidassigments;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NavUtils;
import androidx.core.view.WindowCompat;

public class ChatWindow extends AppCompatActivity {

    protected static final String ACTIVITY_NAME = "ChatWindow";

    private ListView listView;
    private EditText textInput;
    private Button sendButton;
    private ChatMessageManager chatMessageManager;
    private ChatAdapter messageAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i(ACTIVITY_NAME, "onCreate() called");
        WindowCompat.setDecorFitsSystemWindows(getWindow(), true);
        setContentView(R.layout.activity_chat_window);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        listView = findViewById(R.id.chatListView);
        textInput = findViewById(R.id.chatEditText);
        sendButton = findViewById(R.id.sendButton);

        chatMessageManager = new ChatMessageManager();
        messageAdapter = new ChatAdapter(this);
        listView.setAdapter(messageAdapter);

        sendButton.setOnClickListener(v -> {
            String messageText = textInput.getText() != null ? textInput.getText().toString() : "";
            if (chatMessageManager.addMessage(messageText)) {
                messageAdapter.notifyDataSetChanged();
                textInput.setText("");
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            NavUtils.navigateUpFromSameTask(this);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private class ChatAdapter extends ArrayAdapter<String> {

        public ChatAdapter(Context ctx) {
            super(ctx, 0);
        }

        @Override
        public int getCount() {
            return chatMessageManager != null ? chatMessageManager.getMessageCount() : 0;
        }

        @Nullable
        @Override
        public String getItem(int position) {
            return chatMessageManager != null ? chatMessageManager.getMessage(position) : "";
        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            LayoutInflater inflater = ChatWindow.this.getLayoutInflater();
            View result;

            if (chatMessageManager != null && chatMessageManager.isIncoming(position)) {
                result = inflater.inflate(R.layout.chat_row_incoming, parent, false);
            } else {
                result = inflater.inflate(R.layout.chat_row_outgoing, parent, false);
            }

            TextView message = result.findViewById(R.id.messageText);
            if (message != null) {
                message.setText(getItem(position));
            }

            return result;
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.i(ACTIVITY_NAME, "onStart() called");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.i(ACTIVITY_NAME, "onResume() called");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.i(ACTIVITY_NAME, "onPause() called");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.i(ACTIVITY_NAME, "onStop() called");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.i(ACTIVITY_NAME, "onDestroy() called");
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        Log.i(ACTIVITY_NAME, "onSaveInstanceState() called");
    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        Log.i(ACTIVITY_NAME, "onRestoreInstanceState() called");
    }
}
