package com.agileandflexible.soundboard;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;

import com.agileandflexible.soundboard.Helpers.SoundItemHelper;

public class MainActivity extends AppCompatActivity {
    RecyclerView soundTilesRecyclerView;
    RecyclerViewAdapter soundTilesRecyclerViewAdapter;
    RecyclerView.LayoutManager recyclerViewLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        soundTilesRecyclerView = findViewById(R.id.soundboardRecyclerView);

        recyclerViewLayoutManager = new GridLayoutManager(this, 3);
        soundTilesRecyclerViewAdapter = new RecyclerViewAdapter(SoundItemHelper.soundList);
        soundTilesRecyclerView.setLayoutManager(recyclerViewLayoutManager);
        soundTilesRecyclerView.setAdapter(soundTilesRecyclerViewAdapter);

        requestPermission();
        EventHandler.SetRecordButton(findViewById(R.id.activity_main), (Button) findViewById(R.id.record_button), soundTilesRecyclerViewAdapter);
        SoundItemHelper.GetSoundListFromFolder(findViewById(R.id.activity_main));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return false;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventHandler.releaseMediaPlayer();
    }

    private void requestPermission(){
        if(ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 0);
        }
        if(ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 0);
        }
        if(ContextCompat.checkSelfPermission(this, Manifest.permission.RECORD_AUDIO) != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.RECORD_AUDIO}, 0);
        }
    }
}
