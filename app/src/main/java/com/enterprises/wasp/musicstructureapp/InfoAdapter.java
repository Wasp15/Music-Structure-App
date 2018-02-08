package com.enterprises.wasp.musicstructureapp;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class InfoAdapter extends ArrayAdapter<Info> {

    private Context Context;
    private ArrayList<Info> songList = new ArrayList<>();

    public InfoAdapter(@NonNull Context context, ArrayList<Info> list) {
        super(context, 0, list);
        Context = context;
        songList = list;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View listItem = convertView;
        if (listItem == null)
            listItem = LayoutInflater.from(Context).inflate
                    (R.layout.list_item_songs, parent, false);

        Info currentSong = songList.get(position);

        String temp;

        TextView name = listItem.findViewById(R.id.songs);
        temp = "\nSong: " + currentSong.getSongNames() + "\nAlbum: " + currentSong.getAlbumName() +
                "\nArtist: " + currentSong.getArtistName() + "\n";
        name.setText(temp);

        return listItem;
    }
}

