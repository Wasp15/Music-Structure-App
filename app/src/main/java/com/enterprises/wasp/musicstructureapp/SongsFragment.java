package com.enterprises.wasp.musicstructureapp;

import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class SongsFragment extends Fragment {

    private ArrayList<String> songNames = new ArrayList<>();
    private ArrayList<String> albumNames = new ArrayList<>();
    private ArrayList<String> artistNames = new ArrayList<>();

    public SongsFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.universal_layout, container, false);
        super.onCreate(savedInstanceState);
        ListView listView;
        InfoAdapter adapter;
        scanDeviceForMp3Files();

        ArrayList<Info> iSongs = new ArrayList<>();

        listView = rootView.findViewById(R.id.songList);
        for (int i = 0; i < songNames.size(); i++) {
            iSongs.add(new Info(songNames.get(i), artistNames.get(i), albumNames.get(i)));
        }

        adapter = new InfoAdapter(getActivity(), iSongs);
        listView.setAdapter(adapter);

        return rootView;
    }

    private void scanDeviceForMp3Files() {
        String selection = MediaStore.Audio.Media.IS_MUSIC + " != 0";
        String[] projection = {
                MediaStore.Audio.Media.TITLE,
                MediaStore.Audio.Media.ARTIST,
                MediaStore.Audio.Media.DATA,
                MediaStore.Audio.Media.ALBUM
        };
        final String sortOrder = MediaStore.Audio.AudioColumns.TITLE + " COLLATE LOCALIZED ASC";

        Cursor cursor = null;
        try {
            Uri uri = android.provider.MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
            cursor = getActivity().getContentResolver().query
                    (uri, projection, selection, null, sortOrder);
            if (cursor != null) {
                cursor.moveToFirst();

                while (!cursor.isAfterLast()) {
                    String title = cursor.getString(0);
                    String artist = cursor.getString(1);
                    String path = cursor.getString(2);
                    String album = cursor.getString(3);
                    cursor.moveToNext();
                    if (path != null && (path.endsWith(".mp3") ||
                            path.endsWith(".flac") || path.endsWith(".m4a"))) {
                        songNames.add(title);
                        albumNames.add(album);
                        artistNames.add(artist);
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

