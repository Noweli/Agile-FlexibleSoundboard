package com.agileandflexible.soundboard;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.agileandflexible.soundboard.Helpers.EventHelper;

import java.util.ArrayList;

/**
 * Adapter representing sounds in app.
 */
public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.SoundboardViewHolder> {
    private ArrayList<SoundItem> soundList;

    /**
     * Constructs adpater based on list of sounds.
     * @param soundList List to initialize adapter.
     */
    public RecyclerViewAdapter(ArrayList<SoundItem> soundList){
        this.soundList = soundList;
    }

    /**
     * Base method overridden. For more check android documentation.
     * @param parent
     * @param viewType
     * @return
     */
    @NonNull
    @Override
    public SoundboardViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.sound_tile, null);
        return new SoundboardViewHolder(view);
    }

    /**
     * Base method overridden. Sets path to files for media player. For more check android documentation.
     * @param holder
     * @param position
     */
    @Override
    public void onBindViewHolder(@NonNull SoundboardViewHolder holder, final int position) {
        holder.textView.setText(soundList.get(position).getSoundName().substring(0, soundList.get(position).getSoundName().indexOf('.')));
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EventHandler.startMediaPlayer(EventHelper.GetDirectory(v) + "/" + soundList.get(position).getSoundName());
            }
        });

        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                EventHandler.popupMenu(v, soundList.get(position));
                return true;
            }
        });
    }

    /**
     * Returns number of sounds in list.
     * @return Number of sounds in list.
     */
    @Override
    public int getItemCount() {
        return soundList.size();
    }

    public class SoundboardViewHolder extends RecyclerView.ViewHolder{
        TextView textView;

        public SoundboardViewHolder(@NonNull View itemView) {
            super(itemView);

            textView = itemView.findViewById(R.id.textView);
        }
    }
}
