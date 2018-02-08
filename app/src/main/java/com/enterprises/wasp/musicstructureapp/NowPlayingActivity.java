package com.enterprises.wasp.musicstructureapp;

import android.content.pm.ActivityInfo;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

public class NowPlayingActivity extends AppCompatActivity {

    ArrayList<String> artists = new ArrayList<>();
    ArrayList<String> songs = new ArrayList<>();
    ArrayList<String> albums = new ArrayList<>();
    int index;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.now_playing_layout);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        scanDeviceForMp3Files();

        final TextView artistView = findViewById(R.id.artist_now_playing);
        final TextView songView = findViewById(R.id.song_name_now_playing);
        final TextView albumView = findViewById(R.id.album_now_playing);
        final Button previousButton = findViewById(R.id.previous_button);
        final Button nextButton = findViewById(R.id.next_button);

        if (!artists.isEmpty() && !songs.isEmpty() && !albums.isEmpty()) {
            artistView.setText(artists.get(index));
            songView.setText(songs.get(index));
            albumView.setText(albums.get(index));
        }

        previousButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (index != 0)
                    index--;
                if (!artists.isEmpty() && !songs.isEmpty() && !albums.isEmpty()) {
                    artistView.setText(artists.get(index));
                    songView.setText(songs.get(index));
                    albumView.setText(albums.get(index));
                }
            }
        });

        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (index < songs.size())
                    index++;
                if (!artists.isEmpty() && !songs.isEmpty() && !albums.isEmpty()) {
                    artistView.setText(artists.get(index));
                    songView.setText(songs.get(index));
                    albumView.setText(albums.get(index));
                }
            }
        });
    }

    private void scanDeviceForMp3Files() {
        String selection = MediaStore.Audio.Media.IS_MUSIC + " != 0";
        String[] projection = {
                MediaStore.Audio.Media.ARTIST,
                MediaStore.Audio.Media.DATA,
                MediaStore.Audio.Media.TITLE,
                MediaStore.Audio.Media.ALBUM
        };
        final String sortOrder = MediaStore.Audio.AudioColumns.TITLE + " COLLATE LOCALIZED ASC";

        Cursor cursor = null;
        try {
            Uri uri = android.provider.MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
            cursor = getContentResolver().query
                    (uri, projection, selection, null, sortOrder);
            if (cursor != null) {
                cursor.moveToFirst();

                while (!cursor.isAfterLast()) {
                    String artist = cursor.getString(0);
                    String path = cursor.getString(1);
                    String title = cursor.getString(2);
                    String album = cursor.getString(3);
                    cursor.moveToNext();
                    if (path != null && (path.endsWith(".mp3") ||
                            path.endsWith(".flac") || path.endsWith(".m4a"))) {
                        artists.add(artist);
                        songs.add(title);
                        albums.add(album);
                    }
                }
            }
        } catch (Exception e) {
            Log.e("TAG", e.toString());
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
    }
}

