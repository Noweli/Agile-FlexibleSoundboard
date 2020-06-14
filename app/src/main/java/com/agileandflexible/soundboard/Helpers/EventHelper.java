package com.agileandflexible.soundboard.Helpers;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import androidx.core.content.FileProvider;

import com.agileandflexible.soundboard.SoundItem;
import com.google.android.material.snackbar.Snackbar;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 *  Helper used to handle file related actions
 */
public class EventHelper {
    private static final String LOG_TAG = "EVENTHELPER";

    /**
     * Saves file
     * @param view View
     * @param soundItem Sound item
     * @param soundFile Sound file to save
     */
    public static void SaveFile(View view, SoundItem soundItem, File soundFile){
        try (InputStream inputStream = view.getContext().getResources().openRawResource(soundItem.getSoundId()); OutputStream outputStream = new FileOutputStream(soundFile)){
            byte[] buffer = new byte[1024];
            int len;
            while((len = inputStream.read(buffer, 0, buffer.length)) != -1){
                outputStream.write(buffer, 0, len);
            }
            Snackbar.make(view, "Saved file to: " + GetDirectory(view).getAbsolutePath(), Snackbar.LENGTH_SHORT);
        } catch (IOException e){
            Log.e(LOG_TAG, "Failed to save file");
        } catch (Exception e){
            Log.e(LOG_TAG, e.getMessage());
        }
    }

    /**
     * Gets directory for app
     * @param view View
     * @return App directory as File
     */
    public static File GetDirectory(View view){
        File directory = new File(view.getContext().getExternalFilesDir(null) + "/flexibleSoundBoard");

        if(!directory.mkdir()){
            Log.wtf(LOG_TAG, "Directory not created!");
        }

        return directory;
    }

    /**
     * Shares sound
     * @param view View
     * @param soundFile File to share
     */
    public static void ShareFile(View view, File soundFile) {
        final String AUTHORITY = "com.agileandflexible.fileprovider";
        Uri contentUri = FileProvider.getUriForFile(view.getContext(), AUTHORITY, soundFile);
        final Intent shareIntent = new Intent(Intent.ACTION_SEND);
        shareIntent.putExtra(Intent.EXTRA_STREAM, contentUri);
        shareIntent.setType("audio/mp3");
        view.getContext().startActivity(Intent.createChooser(shareIntent, "Share sound tile"));
    }

    /**
     * Builds alert dialog after recording
     * @param view View
     */
    public static void BuildRecordFileAlertDialog(final View view){
        AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
        builder.setTitle("Sound name");

        final EditText input = new EditText(view.getContext());
        input.setInputType(InputType.TYPE_CLASS_TEXT);
        builder.setView(input);

        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                EventMediaHelper.StartRecording(EventHelper.GetDirectory(view) + "/" + input.getText().toString() + ".aac");
            }
        });

        builder.show();
    }
}
