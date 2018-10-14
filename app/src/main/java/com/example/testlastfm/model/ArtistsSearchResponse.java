package com.example.testlastfm.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;



public class ArtistsSearchResponse {
    @SerializedName("results")
    @Expose
    private ArtistResults results;

    public ArtistResults getResults() {
        return results;
    }

    public void setResults(ArtistResults results) {
        this.results = results;
    }
}

