package com.agileandflexible.soundboard;

import android.util.EventLog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.SoundboardViewHolder> {
    private ArrayList<SoundItem> soundList;

    public RecyclerViewAdapter(ArrayList<SoundItem> soundList){
        this.soundList = soundList;
    }

    @NonNull
    @Override
    public SoundboardViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.sound_tile, null);
        return new SoundboardViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SoundboardViewHolder holder, final int position) {
        holder.textView.setText(soundList.get(position).getSoundName());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EventHandler.startMediaPlayer(v, soundList.get(position).getSoundId());
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
