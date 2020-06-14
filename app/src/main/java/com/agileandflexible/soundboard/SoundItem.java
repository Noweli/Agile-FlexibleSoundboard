package com.agileandflexible.soundboard;

/**
 * Class representing sounds in app.
 */
public class SoundItem {
    private int soundId;
    private String soundName;

    public SoundItem(int soundId, String soundName) {
        this.soundId = soundId;
        this.soundName = soundName;
    }

    public int getSoundId() {
        return soundId;
    }

    public String getSoundName() {
        return soundName;
    }
}
