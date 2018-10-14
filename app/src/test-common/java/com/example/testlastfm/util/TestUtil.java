package com.example.testlastfm.util;

import android.support.annotation.NonNull;

import com.example.testlastfm.model.Album;
import com.example.testlastfm.model.AlbumResults;
import com.example.testlastfm.model.Albummatches;

import java.util.ArrayList;
import java.util.List;

public class TestUtil {

    public static List<Album> createAlbums(long id, String name, String artist, @NonNull String mbid, int count) {
        List<Album> albums = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            albums.add(createAlbum(id + i, name + i, artist + i, mbid + i));
        }
        return albums;
    }


    public static Album createAlbum(long id, String name, String artist, @NonNull String mbid) {

        return new Album(id, name, artist, mbid);
    }

    public static AlbumResults createAlbumResults(String opensearchTotalResults, Albummatches albummatches){
        return new AlbumResults(opensearchTotalResults,albummatches);
    }

}
