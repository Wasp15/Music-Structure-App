package com.enterprises.wasp.musicstructureapp;

public class Info {

    private String songName;
    private String albumName;
    private String artistName;

    public Info(String songNames, String artistNames, String albumNames) {
        this.songName = songNames;
        this.albumName = albumNames;
        this.artistName = artistNames;
    }

    public Info(String albumNames, String artistNames) {
        this.albumName = albumNames;
        this.artistName = artistNames;
    }

    public Info(String artistNames) {
        this.artistName = artistNames;
    }

    public String getSongNames() {
        return songName;
    }

    public String getAlbumName() {
        return albumName;
    }

    public String getArtistName() {
        return albumName;
    }
}

