package com.gapview.nume2.ui;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.drive.DriveContents;
import com.google.android.gms.drive.DriveFile;
import com.google.android.gms.drive.DriveFolder;
import com.google.android.gms.drive.DriveId;
import com.google.android.gms.drive.Metadata;
import com.google.android.gms.drive.MetadataBuffer;
import com.google.android.gms.drive.query.Filter;
import com.google.android.gms.drive.query.Filters;
import com.google.android.gms.drive.query.Query;
import com.google.android.gms.drive.query.SearchableField;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.gapview.nume2.R;
import com.gapview.nume2.ui.BaseGdriveActivity;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;

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
    private ArrayList<String> mAlFileConents;
    private Integer mIntFileCount;
    private Integer metaDataCount;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contents);
        mFileContents = findViewById(R.id.fileContents);
        mFileContents.setText("");
        mAlFileConents = new ArrayList<>();
        mIntFileCount = 0;
        metaDataCount = 0;
    }

    @Override
    protected void onDriveClientReady() {
        getDriveClient().requestSync()
                .addOnSuccessListener(this,
                        okay -> onRequesySyncReady())
                .addOnFailureListener(this, e -> {
                    Log.e(TAG, "Failure of get folder:" + e.getCause().getMessage());
                    Log.e(TAG, "No folder selected", e);
                    showMessage(getString(R.string.folder_not_selected));
                    finish();
                });


//        pickTextFile()
//                .addOnSuccessListener(this,
//                        driveId -> printFile(driveId.asDriveFile()))//retrieveContents(driveId.asDriveFile()))
//                .addOnFailureListener(this, e -> {
//                    Log.e(TAG, "No file selected", e);
//                    showMessage(getString(R.string.file_not_selected));
//                    finish();
//                });
    }

    private void onRequesySyncReady () {
        pickFolder()
                .addOnSuccessListener(this,
                        driveId -> queryFolder(driveId.asDriveFolder()))
                .addOnFailureListener(this, e -> {
                    Log.e(TAG, "No folder selected", e);
                    showMessage(getString(R.string.file_not_selected));
                    finish();
                });
    }

    private void printFile (DriveFile file) {
         getDriveResourceClient().getMetadata(file)
                 .addOnSuccessListener(this,
                         metaData -> logit(metaData))
                 .addOnFailureListener(this, e -> {
                     Log.e(TAG, "Error retrieving files", e);
                     showMessage(getString(R.string.query_failed));
                     finish();
                 });
    }

    private void logit (Metadata metadata) {
        Log.d (TAG, "metaTitle " + metadata.getTitle() + " Type: " + metadata.getMimeType());
    }

    private void queryFolder (DriveFolder folder)  {

//        Filter parentFilter = Filters.in(SearchableField.PARENTS, parentDriverId);
        // [START drive_android_query_title]
//        // Searching all text/plain files
        Query query = new Query.Builder()
//                .addFilter(Filters.eq(SearchableField.TITLE, "test.txt"))
//                .addFilter(Filters.eq(SearchableField.MIME_TYPE, "text/plain"))
                //.addFilter(Filters.contains(SearchableField.TITLE, ".txt"))
                .build();
        // [END drive_android_query_title]

        // Searching current folder
//         Task<MetadataBuffer> queryTask = getDriveResourceClient().query(query);
        // alter code
        // Search the folder
        Task<MetadataBuffer> queryTask = getDriveResourceClient().queryChildren(folder, query);
        queryTask
                        .addOnSuccessListener(this,
                                metadataBuffer -> retrieveAllContents(metadataBuffer))
                        .addOnFailureListener(this, e -> {
                            Log.e(TAG, "Error retrieving files", e);
                            showMessage(getString(R.string.query_failed));
                            finish();
                        });
    }

    private void retrieveAllContents(MetadataBuffer metadataBuffer) {
        if (0 == metadataBuffer.getCount()) {
            Log.e(TAG, "No text files found!");
            Toast.makeText(this, "No text files found!", Toast.LENGTH_LONG).show();
        }

        String s;
        metaDataCount = metadataBuffer.getCount();

        for (Metadata metadata : metadataBuffer) {
            Log.d(TAG, "metaTitle: " + metadata.getTitle() + "metaType: " + metadata.getMimeType());
            if (!metadata.isFolder() && metadata.getMimeType().contentEquals("text/plain")) {
                retrieveContents(metadata.getDriveId().asDriveFile());
            }
        }

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
                        //startActivity(MainActivity.getStartIntent(this,builder.toString()));
                        mAlFileConents.add(builder.toString());
                        mIntFileCount ++;
                        metaDataCount --;
                        if (metaDataCount == 0) {
                            startActivity(MainActivity.getStartIntent(this,
                                    mIntFileCount, mAlFileConents));
                        }
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
                    metaDataCount --;
                    if (metaDataCount == 0) {
                        startActivity(MainActivity.getStartIntent(this,
                                mIntFileCount,
                                mAlFileConents));
                    }
                    finish();
                    // [END_EXCLUDE]
                });
        // [END drive_android_read_contents]
    }
}
