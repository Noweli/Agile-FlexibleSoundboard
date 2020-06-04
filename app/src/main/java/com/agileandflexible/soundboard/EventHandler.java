package com.agileandflexible.soundboard;

import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.PopupMenu;

import com.agileandflexible.soundboard.Helpers.EventHelper;
import com.agileandflexible.soundboard.Helpers.EventMediaHelper;
import com.agileandflexible.soundboard.Helpers.SoundItemHelper;

import java.io.File;

public class EventHandler {
    public static void startMediaPlayer(String filePath){
        EventMediaHelper.PlayMedia(filePath);
    }

    public static void releaseMediaPlayer(){
        EventMediaHelper.ReleaseMediaPlayer();
    }

    public static void popupMenu(final View view, final SoundItem soundItem){
        PopupMenu popupMenu = new PopupMenu(view.getContext(), view);
        popupMenu.getMenuInflater().inflate(R.menu.popup_menu, popupMenu.getMenu());

        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                final String fileName = soundItem.getSoundName();
                final File soundFile = new File(EventHelper.GetDirectory(view), fileName);

                switch (item.getItemId()) {
                    case R.id.menu_save_as:
                       EventHelper.SaveFile(view, soundItem, soundFile);
                       break;
                    case R.id.menu_share:
                        EventHelper.SaveFile(view, soundItem, soundFile);
                        EventHelper.ShareFile(view, soundFile);
                        break;
                }
                return true;
            }
        });
        popupMenu.show();
    }

    public static void SetRecordButton(final View view, final Button button, final RecyclerViewAdapter recyclerViewAdapter){
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EventMediaHelper.RecordMedia(view);
                if(EventMediaHelper.isRecording){
                    button.setText(R.string.stopRecording);
                }else{
                    SoundItemHelper.UpdateSoundListFromFolder(view, recyclerViewAdapter);
                    button.setText(R.string.recordNewSound);
                }
            }
        });
    }
}
