package com.example.testlastfm.db;


import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Transformations;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.util.SparseLongArray;

import com.example.testlastfm.model.Album;
import com.example.testlastfm.model.AlbumSearchRes;
import com.example.testlastfm.model.Image;


import java.util.Collections;
import java.util.List;

@Dao
public abstract class LastFmDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public abstract List<Long> insertAlbum(List<Album> albums);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public abstract List<Long> insertImages(List<Image> images);

    public LiveData<List<Album>> loadOrderedAlbums(List<Long> albumIds) {
        SparseLongArray order = new SparseLongArray();
        int index = 0;
        for (Long albumId : albumIds) {
            order.put(albumId.intValue(), index++);
        }
        return Transformations.map(loadAlbumById(albumIds), albums -> {
            Collections.sort(albums, (r1, r2) -> {
                int pos1 = (int) order.get(r1.getAlbumId().intValue());
                int pos2 = (int) order.get(r2.getAlbumId().intValue());
                return pos1 - pos2;
            });
            return albums;
        });
    }

    @Query("SELECT * FROM Album WHERE albumId in (:albumId)")
    protected abstract LiveData<List<Album>> loadAlbumById(List<Long> albumId);

    @Query("SELECT * FROM Album WHERE albumId in (:albumId)")
    public abstract LiveData<Album> loadAlbumById(Long albumId);


    @Query("SELECT * FROM AlbumSearchRes WHERE query = :query")
    public abstract LiveData<AlbumSearchRes> search(String query);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public abstract void insertAlbumSearchResult(AlbumSearchRes albumSearchRes);


}
