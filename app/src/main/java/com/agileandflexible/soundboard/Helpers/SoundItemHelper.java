package com.agileandflexible.soundboard.Helpers;

import android.view.View;

import com.agileandflexible.soundboard.RecyclerViewAdapter;
import com.agileandflexible.soundboard.SoundItem;

import java.io.File;
import java.util.ArrayList;

/**
 * Helper getting sounditems from files.
 */
public class SoundItemHelper {
    public static int soundIndex = 0;
    public static ArrayList<SoundItem> soundList = new ArrayList<>();

    /**
     * Gets list of sounds from folder.
     * @param view View
     */
    public static void GetSoundListFromFolder(final View view){
        File[] soundFiles = EventHelper.GetDirectory(view).listFiles();
        for (File soundFile : soundFiles) {
            soundList.add(new SoundItem(soundIndex, soundFile.getName()));
        }
    }

    /**
     * Updates list of sounds on view.
     * @param view View
     * @param adapter Adapter representing sounds.
     */
    public static void UpdateSoundListFromFolder(final View view, RecyclerViewAdapter adapter){
        soundList.clear();
        GetSoundListFromFolder(view);
        adapter.notifyDataSetChanged();
    }
}
