package com.example.websitetoapp;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ComponentName;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.websitetoapp.Adapters.SongAdapter;
import com.example.websitetoapp.Model.Song;
import com.example.websitetoapp.TemporaryDB.DataBase;

import java.util.ArrayList;
import java.util.List;

public class MusicPlay extends AppCompatActivity {

    private MediaPlayer player;
    private AudioManager audioManager;
    private ImageView playStop;
    private ImageView nextSong;
    private ImageView prevSong;
    private TextView songName;
    private SeekBar seekBar;
    private Handler handler;
    private TextView initialTimer;
    private TextView finalTimer;


    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_music_play);
        playStop = findViewById(R.id.playStop);
        nextSong = findViewById(R.id.nextsong);
        prevSong = findViewById(R.id.prevSong);
        songName = findViewById(R.id.songName);
        seekBar = findViewById(R.id.seekbar);
        initialTimer = findViewById(R.id.current);
        finalTimer = findViewById(R.id.duration);
        intent = getIntent();
        final Song song = intent.getParcelableExtra("song");
        stopMusic();
        player = MediaPlayer.create(this, Uri.parse(song.getSongData()));
        player.start();
        seekBar.setMax(player.getDuration()/1000);
        seekBar.setProgress(player.getCurrentPosition());
        songName.setText(song.getSnogName());
        finalTimer.setText(findTime(player.getDuration()/1000 + ""));
        final int[] songNumber = {intent.getIntExtra("songPos", 0)};
        Toast.makeText(this, "playing :" + song.getSnogName(), Toast.LENGTH_SHORT).show();


        handler = new Handler();
        MusicPlay.this.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if(player!=null){
                    seekBar.setProgress(player.getCurrentPosition()/1000);
                    initialTimer.setText(findTime(player.getCurrentPosition()/1000 + ""));
                }
                handler.postDelayed(this,1000);
            }
        });

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if(player!=null && fromUser){
                    player.seekTo(progress*1000);
                }

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        playStop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(player.isPlaying()){
                    player.pause();
                    playStop.setImageResource(R.drawable.musicstop);
                    Toast.makeText(MusicPlay.this, "pause", Toast.LENGTH_SHORT).show();
                }else{
                    player.start();
                    playStop.setImageResource(R.drawable.musicplay);
                    Toast.makeText(MusicPlay.this, "playing your tune", Toast.LENGTH_SHORT).show();
                }
            }
        });

        nextSong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(player != null){
                    player.stop();
                    player.reset();
                    player.release();
                }
                Song song1 = DataBase.getInstance().getSongById( 1 + songNumber[0]);
                String songNext = song1.getSongData();

                songName.setText(song1.getSnogName());
                playStop.setImageResource(R.drawable.musicplay);
                player = MediaPlayer.create(MusicPlay.this, Uri.parse(songNext));
                player.start();
                seekBar.setMax(player.getDuration()/1000);
                seekBar.setProgress(player.getCurrentPosition());
                finalTimer.setText(findTime(player.getDuration()/1000 + ""));
                songNumber[0]++;
            }
        });

        prevSong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(player != null){
                    player.stop();
                    player.reset();
                    player.release();
                }

                Song song1 = DataBase.getInstance().getSongById( - 1 + songNumber[0]);
                String songNext = song1.getSongData();
                playStop.setImageResource(R.drawable.musicplay);
                songName.setText(song1.getSnogName());
                player = MediaPlayer.create(MusicPlay.this, Uri.parse(songNext));
                player.start();
                seekBar.setMax(player.getDuration()/1000);
                seekBar.setProgress(player.getCurrentPosition());
                finalTimer.setText(findTime(player.getDuration()/1000 + ""));
                songNumber[0]--;
            }
          });


    }

    public String findTime(String timeInSec){

        int Sec = Integer.parseInt(timeInSec);
        final boolean b = String.valueOf(Sec % 60).length() == 1;
        if(b && (Sec%60)>9){
            return (String.valueOf(Sec/60) +":" + String.valueOf(Sec%60) + "0");
        }
        else if(b && (Sec%60)<9){
            return (String.valueOf(Sec/60) +":" + "0" + String.valueOf(Sec%60));
        }
        else {
            return (String.valueOf(Sec / 60) + ":" + String.valueOf(Sec % 60));
        }
    }


    public void stopMusic(){
        if(player != null){
            player.stop();
            player.reset();
        }
    }



}
