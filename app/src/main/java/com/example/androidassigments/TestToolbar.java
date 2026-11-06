package com.example.androidassigments;

import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

public class TestToolbar extends AppCompatActivity {

    private static final String TAG = "TestToolbar";

    private ToolbarMessageManager messageManager;

    public ToolbarMessageManager getMessageManager() {
        return messageManager;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_toolbar);

        Toolbar toolbar = findViewById(R.id.toolbar);
        if (toolbar != null) {
            setSupportActionBar(toolbar);
        }

        messageManager = new ToolbarMessageManager(getString(R.string.default_snackbar_message));

        FloatingActionButton fab = findViewById(R.id.fab);
        if (fab != null) {
            fab.setOnClickListener(v -> Snackbar.make(
                    findViewById(R.id.test_toolbar_root),
                    getString(R.string.fab_snackbar_message),
                    Snackbar.LENGTH_LONG).show());
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu m) {
        getMenuInflater().inflate(R.menu.toolbar_menu, m);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem mi) {
        int id = mi.getItemId();
        if (id == R.id.action_one) {
            Log.d(TAG, "Option 1 selected");
            Snackbar.make(findViewById(R.id.test_toolbar_root), messageManager.getMessage(), Snackbar.LENGTH_LONG).show();
            return true;
        } else if (id == R.id.action_two) {
            Log.d(TAG, "Option 2 selected");
            AlertDialog.Builder builder = new AlertDialog.Builder(TestToolbar.this);
            builder.setTitle(R.string.dialog_go_back_title);
            builder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    finish();
                }
            });
            builder.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    // Do nothing
                }
            });
            AlertDialog dialog = builder.create();
            dialog.show();
            return true;
        } else if (id == R.id.action_three) {
            Log.d(TAG, "Option 3 selected");
            View customView = getLayoutInflater().inflate(R.layout.dialog_custom_message, null);
            final EditText customEditText = customView.findViewById(R.id.customDialogEditText);

            AlertDialog.Builder builder = new AlertDialog.Builder(TestToolbar.this);
            builder.setView(customView);
            builder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    if (customEditText != null) {
                        messageManager.updateMessage(customEditText.getText().toString());
                    }
                }
            });
            builder.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    // Do nothing
                }
            });
            AlertDialog dialog = builder.create();
            dialog.show();
            return true;
        } else if (id == R.id.action_about) {
            Log.d(TAG, "About option selected");
            Toast.makeText(TestToolbar.this, getString(R.string.about_toast_message), Toast.LENGTH_LONG).show();
            return true;
        }
        return super.onOptionsItemSelected(mi);
    }
}