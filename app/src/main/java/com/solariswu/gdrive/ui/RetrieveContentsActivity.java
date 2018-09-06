package com.solariswu.gdrive.ui;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.TextView;

import com.google.android.gms.drive.DriveContents;
import com.google.android.gms.drive.DriveFile;
import com.google.android.gms.drive.DriveId;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.solariswu.gdrive.R;
import com.solariswu.gdrive.ui.BaseGdriveActivity;

import java.io.BufferedReader;
import java.io.InputStreamReader;

/**
 * Created by solariswu on September 5 2018.
 *
 * Referred from Google Drive sample project.
 *
 * Activity to illustrate how to retrieve and read file contents.
 */
public class RetrieveContentsActivity extends BaseGdriveActivity {
    private static final String TAG = "RetrieveContents";

    /**
     * Text view for file contents
     */
    private TextView mFileContents;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contents);
        mFileContents = findViewById(R.id.fileContents);
        mFileContents.setText("");
    }

    @Override
    protected void onDriveClientReady() {
        pickTextFile()
                .addOnSuccessListener(this,
                        driveId -> retrieveContents(driveId.asDriveFile()))
                .addOnFailureListener(this, e -> {
                    Log.e(TAG, "No file selected", e);
                    showMessage(getString(R.string.file_not_selected));
                    finish();
                });
    }

    private void retrieveContents(DriveFile file) {
        // [START drive_android_open_file]
        Task<DriveContents> openFileTask =
                getDriveResourceClient().openFile(file, DriveFile.MODE_READ_ONLY);
        // [END drive_android_open_file]
        // [START drive_android_read_contents]
        openFileTask
                .continueWithTask(task -> {
                    DriveContents contents = task.getResult();
                    // Process contents..
                    // [START_EXCLUDE]
                    // [START drive_android_read_as_string]
                    try (BufferedReader reader = new BufferedReader(
                            new InputStreamReader(contents.getInputStream()))) {
                        StringBuilder builder = new StringBuilder();
                        String line;
                        while ((line = reader.readLine()) != null) {
                            builder.append(line).append("\n");
                        }
//                        showMessage(getString(R.string.content_loaded));
                        //mFileContents.setText(builder.toString());
                        startActivity(MainActivity.getStartIntent(this,builder.toString()));

                    }
                    // [END drive_android_read_as_string]
                    // [END_EXCLUDE]
                    // [START drive_android_discard_contents]
                    Task<Void> discardTask = getDriveResourceClient().discardContents(contents);
                    // [END drive_android_discard_contents]
                    return discardTask;
                })
                .addOnFailureListener(e -> {
                    // Handle failure
                    // [START_EXCLUDE]
                    Log.e(TAG, "Unable to read contents", e);
                    showMessage(getString(R.string.read_failed));
                    finish();
                    // [END_EXCLUDE]
                });
        // [END drive_android_read_contents]
    }
}
