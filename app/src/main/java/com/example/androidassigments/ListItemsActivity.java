package com.example.androidassigments;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.MenuItem;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.Switch;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.view.WindowCompat;

public class ListItemsActivity extends AppCompatActivity {

    protected static final String ACTIVITY_NAME = "ListItemsActivity";
    private static final int REQUEST_IMAGE_CAPTURE = 1;
    private static final int REQUEST_CAMERA_PERMISSION = 2;

    private ImageButton imageButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i(ACTIVITY_NAME, "onCreate() called");
        WindowCompat.setDecorFitsSystemWindows(getWindow(), true);
        setContentView(R.layout.activity_list_items);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        imageButton = findViewById(R.id.imageButton);
        Switch switchView = findViewById(R.id.switchView);
        CheckBox checkBox = findViewById(R.id.checkBox);

        if (imageButton != null) {
            imageButton.setOnClickListener(v -> launchCamera());
        }

        if (switchView != null) {
            switchView.setOnCheckedChangeListener((buttonView, isChecked) -> {
                CharSequence text = isChecked ? getString(R.string.switch_on) : getString(R.string.switch_off);
                int duration = isChecked ? Toast.LENGTH_SHORT : Toast.LENGTH_LONG;
                Toast toast = Toast.makeText(ListItemsActivity.this, text, duration);
                toast.show();
            });
        }

        if (checkBox != null) {
            checkBox.setOnCheckedChangeListener((buttonView, isChecked) -> {
                if (isChecked) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(ListItemsActivity.this);
                    builder.setMessage(R.string.dialog_message)
                            .setTitle(R.string.dialog_title)
                            .setPositiveButton(R.string.ok, (dialog, id) -> {
                                Intent resultIntent = new Intent();
                                resultIntent.putExtra("Response", getString(R.string.response_default));
                                setResult(Activity.RESULT_OK, resultIntent);
                                finish();
                            })
                            .setNegativeButton(R.string.cancel, (dialog, id) -> {
                                checkBox.setChecked(false);
                                dialog.dismiss();
                            })
                            .setOnCancelListener(dialog -> checkBox.setChecked(false))
                            .show();
                }
            });
        }
    }

    private void launchCamera() {
        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{android.Manifest.permission.CAMERA}, REQUEST_CAMERA_PERMISSION);
            return;
        }
        try {
            startActivityForResult(new Intent(MediaStore.ACTION_IMAGE_CAPTURE), REQUEST_IMAGE_CAPTURE);
        } catch (ActivityNotFoundException e) {
            print("No camera app available");
        } catch (Exception e) {
            print("Unable to launch camera");
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_CAMERA_PERMISSION && grantResults.length > 0
                && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            launchCamera();
        } else if (requestCode == REQUEST_CAMERA_PERMISSION) {
            print("Camera permission denied");
        }
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            androidx.core.app.NavUtils.navigateUpFromSameTask(this);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void finish() {
        Log.i(ACTIVITY_NAME, "onFinish() called");
        super.finish();
    }

    public void print(String message) {
        Log.i(ACTIVITY_NAME, message);
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            if (data != null && data.getExtras() != null) {
                Bitmap imageBitmap = (Bitmap) data.getExtras().get("data");
                if (imageBitmap != null && imageButton != null) {
                    imageButton.setImageBitmap(imageBitmap);
                }
            }
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
