package com.example.websitetoapp.Adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.websitetoapp.Model.Song;
import com.example.websitetoapp.MusicPlay;
import com.example.websitetoapp.R;

import java.util.List;

public class SongAdapter extends RecyclerView.Adapter<SongAdapter.ViewHolder> {

    private Context context;
    private List<Song> allSongs;


    public SongAdapter(List<Song> allSongs, Context context){
        this.allSongs = allSongs;
        this.context = context;
    }

    @NonNull
    @Override
    public SongAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.musiclist,parent,false);
        return new SongAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SongAdapter.ViewHolder holder, final int position) {
        final Song song = allSongs.get(position);
        holder.songName.setText(song.getSnogName());
        holder.songArtist.setText(song.getArtist());

        holder.songName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, "Playing "+ allSongs.get(position).getSnogName(), Toast.LENGTH_SHORT).show();
                Intent musicPlayer = new Intent(context, MusicPlay.class);
                musicPlayer.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                musicPlayer.putExtra("song",  allSongs.get(position));
                musicPlayer.putExtra("songPos",position);
                context.startActivity(musicPlayer);


            }
        });
    }

    @Override
    public int getItemCount() {
        return allSongs.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder{

        private TextView songName;
        private TextView songArtist;
        //private CircleImageView songImage;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            songName = itemView.findViewById(R.id.song_name);
            songArtist = itemView.findViewById(R.id.artist);
        }

    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public void updateList(List<Song> list){
        allSongs = list;
        notifyDataSetChanged();
    }

}
