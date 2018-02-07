package com.enterprises.wasp.musicstructureapp;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        TextView songs = findViewById(R.id.songs_text_view);
        songs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent songsIntent = new Intent(MainActivity.this,
                        UniversalFragmentSelector.class);
                startActivity(songsIntent);
            }
        });

        TextView artists = findViewById(R.id.artists_text_view);
        artists.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent artistsIntent = new Intent(MainActivity.this,
                        UniversalFragmentSelector.class);
                startActivity(artistsIntent);
            }
        });

        TextView albums = findViewById(R.id.albums_text_view);
        albums.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent albumsIntent = new Intent(MainActivity.this,
                        UniversalFragmentSelector.class);
                startActivity(albumsIntent);
            }
        });

        TextView search = findViewById(R.id.search_text_view);
        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent searchIntent = new Intent(MainActivity.this,
                        UniversalFragmentSelector.class);
                startActivity(searchIntent);
            }
        });
    }
}

