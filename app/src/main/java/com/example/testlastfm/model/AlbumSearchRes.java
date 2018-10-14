package com.example.testlastfm.model;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.TypeConverters;
import android.support.annotation.NonNull;

import com.example.testlastfm.util.Converters;

import java.util.List;

@Entity(primaryKeys = {"query"})
@TypeConverters(Converters.class)
public class AlbumSearchRes {

    @NonNull
    public final String query;
    public final List<Long> albumIds;
    public final int totalCount;


    public AlbumSearchRes(@NonNull String query, List<Long> albumIds, int totalCount) {
        this.query = query;
        this.albumIds = albumIds;
        this.totalCount = totalCount;
    }
}
