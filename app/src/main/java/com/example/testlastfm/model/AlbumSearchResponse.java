
package com.example.testlastfm.model;

import android.support.annotation.NonNull;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class AlbumSearchResponse {


    @SerializedName("results")
    @Expose
    private AlbumResults albumResults;

    public AlbumResults getAlbumResults() {
        return albumResults;
    }

    public void setAlbumResults(AlbumResults albumResults) {
        this.albumResults = albumResults;
    }

    @NonNull
    public List<Long> getAlbumIds() {
        List<Long> albumIds = new ArrayList<>();
        for (Album album : albumResults.getAlbummatches().getAlbum()) {
            albumIds.add(album.getAlbumId());
        }
        return albumIds;
    }

}
