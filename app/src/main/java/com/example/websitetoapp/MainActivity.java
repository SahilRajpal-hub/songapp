package com.example.websitetoapp;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.media.AudioManager;
import android.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.database.Cursor;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;

import com.example.websitetoapp.Adapters.SongAdapter;
import com.example.websitetoapp.Model.Song;
import com.example.websitetoapp.TemporaryDB.DataBase;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private FloatingActionButton downloadButton;
    private RecyclerView recyclerView;
    private List<Song> allSongs;
    private SearchView searchView;


    private RecyclerView.LayoutManager layoutManager;
    public SongAdapter adapter;
    @RequiresApi(api = Build.VERSION_CODES.Q)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        downloadButton = findViewById(R.id.floatingBtn);
        toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("SareGama");
        searchView = findViewById(R.id.searchView);
        setSupportActionBar(toolbar);
        recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);
        String selection = MediaStore.Audio.Media.IS_MUSIC + " != 0";

        String[] projection = {
          MediaStore.Audio.Media._ID,
          MediaStore.Audio.Media.ARTIST,
          MediaStore.Audio.Media.TITLE,
          MediaStore.Audio.Media.DATA,
          MediaStore.Audio.Media.ALBUM_ID,
          MediaStore.Audio.Media.DURATION,
        };

        downloadButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this,DownloadActivity.class));
            }
        });

        Cursor cursor = getApplicationContext().getContentResolver().query(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,projection,selection,null,null);

        allSongs = new ArrayList<>();
        assert cursor != null;
        int k=0;
        allSongs.clear();
        while(cursor.moveToNext()){
            Song song = new Song("default","default","default","default", 0,0);
            int maxLength;
            if(cursor.getString(2).length() < 30){
                maxLength = cursor.getString(2).length();
            }else{
                maxLength = 30;
            }
            song.setSnogName(cursor.getString(2).toString().substring(0,maxLength) + "...");
            song.setArtist(cursor.getString(1));
            song.setSongData(cursor.getString(3));
            song.setDuration(Integer.parseInt(cursor.getString(5)));
            song.setPosition(k);
            song.setAlbum(cursor.getString(4));
            allSongs.add(song);
            k++;
        }

        adapter = new SongAdapter(allSongs,getApplicationContext());
        adapter.setHasStableIds(true);
        DataBase.getInstance().setAllSongs(allSongs);
        recyclerView.setAdapter(adapter);


        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                filter(newText);
                return true;
            }
        });

    }

    private void filter(String newText) {
        List<Song> temp = new ArrayList<>();
        for(Song song:allSongs){
            if(song.getSnogName().toLowerCase().contains(newText.toLowerCase())){
                temp.add(song);
            }
        }
        DataBase.getInstance().setAllSongs(temp);
        adapter.updateList(temp);

    }


}
