package com.example.testlastfm.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;



public class ArtistResults {
    @SerializedName("artistmatches")
    @Expose
    private ArtistMatches artistMatches;

    public ArtistMatches getArtistMatches() {
        return artistMatches;
    }

    public void setArtistMatches(ArtistMatches artistMatches) {
        this.artistMatches = artistMatches;
    }
}
