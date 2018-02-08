package com.enterprises.wasp.musicstructureapp;

import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class SearchFragment extends Fragment {

    private ArrayList<String> songNames = new ArrayList<>();
    private ArrayList<String> albumNames = new ArrayList<>();
    private ArrayList<String> artistNames = new ArrayList<>();
    private String searchString;
    private ListView listView;
    private InfoAdapter adapter;

    public SearchFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.search_layout, container, false);
        super.onCreate(savedInstanceState);
        getActivity().getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        final EditText edit_text = rootView.findViewById(R.id.editText);
        edit_text.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                TextWatcher inputTextWatcher = new TextWatcher() {
                    public void afterTextChanged(Editable s) {
                        songNames.clear();
                        albumNames.clear();
                        artistNames.clear();
                        searchString = edit_text.getText().toString();
                        scanDeviceForMp3Files();
                        ArrayList<Info> iSongs = new ArrayList<>();

                        listView = rootView.findViewById(R.id.searchList);
                        for (int i = 0; i < songNames.size(); i++) {
                            iSongs.add(new Info(songNames.get(i), artistNames.get(i), albumNames.get(i)));
                        }

                        adapter = new InfoAdapter(getActivity(), iSongs);
                        listView.setAdapter(adapter);
                    }

                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                    }

                    public void onTextChanged(CharSequence s, int start, int before, int count) {
                    }
                };

                edit_text.addTextChangedListener(inputTextWatcher);
            }
        });

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
                    if (path != null && (title.contains(searchString) ||
                            album.contains(searchString) ||
                            artist.contains(searchString) ||
                            path.contains(searchString))) {
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

