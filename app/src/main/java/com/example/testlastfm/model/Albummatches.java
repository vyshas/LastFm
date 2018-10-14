
package com.example.testlastfm.model;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class Albummatches {

    @SerializedName("album")
    @Expose
    private List<Album> album = null;

    public List<Album> getAlbum() {
        return album;
    }

    public void setAlbum(List<Album> album) {
        this.album = album;
    }


    public Albummatches(List<Album> album) {
        this.album = album;
    }
}
