package com.example.testlastfm.db;


import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.TypeConverters;


import com.example.testlastfm.model.Album;

import com.example.testlastfm.model.AlbumSearchRes;
import com.example.testlastfm.model.Image;
import com.example.testlastfm.util.Converters;

/**
 * Main database description.
 */
@Database(entities = {Album.class,AlbumSearchRes.class,Image.class}, version = 1)
@TypeConverters({Converters.class})
public abstract class LastFmDb extends RoomDatabase {

    abstract public LastFmDao lastFmDao();

}
