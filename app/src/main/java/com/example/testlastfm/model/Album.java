
package com.example.testlastfm.model;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Entity
public class Album {

    @PrimaryKey(autoGenerate = true)
    private Long albumId ;

    @SerializedName("name")
    @Expose
    private String name;

    @SerializedName("artist")
    @Expose
    private String artist;

    @SerializedName("url")
    @Expose
    private String url;

    @SerializedName("image")
    @Expose
    private List<Image> image = null;

    @SerializedName("streamable")
    @Expose
    private String streamable;

    @SerializedName("mbid")
    @Expose
    private String mbid;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getArtist() {
        return artist;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public List<Image> getImage() {
        return image;
    }

    public void setImage(List<Image> image) {
        this.image = image;
    }

    public String getStreamable() {
        return streamable;
    }

    public void setStreamable(String streamable) {
        this.streamable = streamable;
    }

    public String getMbid() {
        return mbid;
    }

    public void setMbid(String mbid) {
        this.mbid = mbid;
    }

    public Long getAlbumId() {
        return albumId;
    }

    public void setAlbumId(Long albumId) {
        this.albumId = albumId;
    }

    public String albumImage;

    public String getAlbumImage() {
        return albumImage;
    }

    public void setAlbumImage(String albumImage) {
        this.albumImage = albumImage;
    }

    public Album(Long albumId, String name, String artist, @NonNull String mbid) {
        this.albumId = albumId;
        this.name = name;
        this.artist = artist;
        this.mbid = mbid;
    }
}
