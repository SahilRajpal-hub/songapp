package com.example.websitetoapp.Model;

import android.os.Parcel;
import android.os.Parcelable;

public class Song implements Parcelable {

    private String snogName;
    private String Artist;
    private String Album;
    private String songData;
    private int duration;
    private int position;


    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public Song(String snogName, String artist, String album, String songData, int position, int duration) {
        this.snogName = snogName;
        Artist = artist;
        Album = album;
        this.songData = songData;
        this.position = position;
        this.duration = duration;
    }

    protected Song(Parcel in) {
        snogName = in.readString();
        Artist = in.readString();
        Album = in.readString();
        songData = in.readString();

    }

    public static final Creator<Song> CREATOR = new Creator<Song>() {
        @Override
        public Song createFromParcel(Parcel in) {
            return new Song(in);
        }

        @Override
        public Song[] newArray(int size) {
            return new Song[size];
        }
    };

    public String getSongData() {
        return songData;
    }

    public void setSongData(String songData) {
        this.songData = songData;
    }


    public String getSnogName() {
        return snogName;
    }

    public void setSnogName(String snogName) {
        this.snogName = snogName;
    }

    public String getArtist() {
        return Artist;
    }

    public void setArtist(String artist) {
        Artist = artist;
    }

    public String getAlbum() {
        return Album;
    }

    public void setAlbum(String album) {
        Album = album;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(snogName);
        dest.writeString(Artist);
        dest.writeString(Album);
        dest.writeString(songData);
    }
}
