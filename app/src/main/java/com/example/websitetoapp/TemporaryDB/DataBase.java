package com.example.websitetoapp.TemporaryDB;

import com.example.websitetoapp.Model.Song;

import java.util.List;

public class DataBase {
    public static  DataBase db;

    public DataBase() {
    }

    public static DataBase getInstance(){
        if(db == null){
            db = new DataBase();
        }
        return db;
    }
    private List<Song> allSongs;

    public List<Song> getAllSongs() {
        return allSongs;
    }

    public void setAllSongs(List<Song> allSongs) {
        this.allSongs = allSongs;
    }

    public Song getSongById(int position){

        return allSongs.get(position);
    }
}
