package com.agileandflexible.soundboard;

import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Environment;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.PopupMenu;

import androidx.core.content.FileProvider;

import com.google.android.material.snackbar.Snackbar;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;

public class EventHandler {
    private static final String LOG_TAG = "EVENTHANDLER";
    private static MediaPlayer mediaPlayer;

    public static void startMediaPlayer(View view, int soundId){
        try{
            if(mediaPlayer != null) mediaPlayer.reset();
            mediaPlayer = MediaPlayer.create(view.getContext(), soundId);
            mediaPlayer.start();
        }catch (Exception e){
            Log.e(LOG_TAG, "Failed to initialize MediaPlayer");
        }
    }

    public static void releaseMediaPlayer(){
        if(mediaPlayer != null) mediaPlayer.release();
        mediaPlayer = null;
    }

    public static void popupMenu(final View view, final SoundItem soundItem){
        PopupMenu popupMenu = new PopupMenu(view.getContext(), view);
        popupMenu.getMenuInflater().inflate(R.menu.popup_menu, popupMenu.getMenu());

        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                final String fileName = soundItem.getSoundName() + ".mp3";
                final File soundFile = new File(GetDirectory(view), fileName);

                switch (item.getItemId()) {
                    case R.id.menu_save_as:
                       SaveFile(view, soundItem, soundFile);
                       break;
                    case R.id.menu_share:
                        SaveFile(view, soundItem, soundFile);
                        final String AUTHORITY = "com.agileandflexible.fileprovider";
                        Uri contentUri = FileProvider.getUriForFile(view.getContext(), AUTHORITY, soundFile);
                        final Intent shareIntent = new Intent(Intent.ACTION_SEND);
                        shareIntent.putExtra(Intent.EXTRA_STREAM, contentUri);
                        shareIntent.setType("audio/mp3");
                        view.getContext().startActivity(Intent.createChooser(shareIntent, "Share sound tile"));
                        break;
                }
                return true;
            }
        });

        popupMenu.show();
    }

    private static void SaveFile(View view, SoundItem soundItem, File soundFile){
        try (InputStream inputStream = view.getContext().getResources().openRawResource(soundItem.getSoundId()); OutputStream outputStream = new FileOutputStream(soundFile)){
            byte[] buffer = new byte[1024];
            int len;
            while((len = inputStream.read(buffer, 0, buffer.length)) != -1){
                outputStream.write(buffer, 0, len);
            }
            Snackbar.make(view, "Saved file to: " + GetDirectory(view).getAbsolutePath(), Snackbar.LENGTH_SHORT);
        } catch (IOException e){
            Log.e(LOG_TAG, "Failed to save file");
        }
    }

    private static File GetDirectory(View view){
        File directory = new File(view.getContext().getExternalFilesDir(null) + "/flexibleSoundBoard");

        if(!directory.mkdir()){
            Log.wtf(LOG_TAG, "Directory not created!");
        }

        return directory;
    }
}
