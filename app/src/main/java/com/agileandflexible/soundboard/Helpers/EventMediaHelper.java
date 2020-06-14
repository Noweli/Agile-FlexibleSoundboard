package com.agileandflexible.soundboard.Helpers;

import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.util.Log;
import android.view.View;

import java.io.File;
import java.io.IOException;

/**
 * Helper used to handle media related things like playing sounds and recording.
 */
public class EventMediaHelper {
    private static final String LOG_TAG = "EVENTMEDIAHELPER";
    public static boolean isRecording = false;
    private static MediaPlayer mediaPlayer;
    private static MediaRecorder mediaRecorder;

    /**
     * Plays media
     * @param filePath Path to media to be played
     */
    public static void PlayMedia(String filePath) {
        try {
            if (mediaPlayer != null) mediaPlayer.reset();
            mediaPlayer = new MediaPlayer();
            mediaPlayer.setDataSource(filePath);
            mediaPlayer.prepare();
            mediaPlayer.start();
        } catch (Exception e) {
            Log.e(LOG_TAG, "Failed to initialize MediaPlayer - " + e.getMessage());
        }
    }

    /**
     * Releases media player. Used to release media player.
     */
    public static void ReleaseMediaPlayer() {
        if (mediaPlayer != null) mediaPlayer.release();
        mediaPlayer = null;
    }

    /**
     * Initiate recording media. Sets flags and builds dialog.
     * @param view View
     */
    public static void RecordMedia(View view) {
        if (!isRecording) {
            EventHelper.BuildRecordFileAlertDialog(view);
            isRecording = true;
        }else {
            StopRecording();
            isRecording = false;
        }
    }

    /**
     * Start recording sound. Sets media recorder params.
     * @param filePath Path to save file.
     */
    public static void StartRecording(String filePath) {
        if (mediaRecorder == null) {
            mediaRecorder = new MediaRecorder();
            mediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
            mediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.AAC_ADTS);
            mediaRecorder.setOutputFile(filePath);
            mediaRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AAC);
        }

        try {
            mediaRecorder.prepare();
        } catch (IOException e) {
            Log.wtf(LOG_TAG, "Failed on prepare media recorder - " + e.getMessage());
        }

        mediaRecorder.start();
    }

    /**
     * Stop recording. Releases media recorder.
     */
    public static void StopRecording(){
        mediaRecorder.stop();
        mediaRecorder.release();
        mediaRecorder = null;
    }
}
