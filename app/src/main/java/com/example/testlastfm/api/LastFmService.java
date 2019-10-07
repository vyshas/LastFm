package com.example.testlastfm.api;


import android.arch.lifecycle.LiveData;

import com.example.testlastfm.model.AlbumSearchResponse;
import com.example.testlastfm.model.ArtistsSearchResponse;

import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * REST API access points
 */
public interface LastFmService {

    @GET("?method=artist.search&format=json")
    LiveData<ApiResponse<ArtistsSearchResponse>> getArtist(@Query("artist") String artist, @Query("api_key") String api_key);

    @GET("?method=album.search&format=json")
    LiveData<ApiResponse<AlbumSearchResponse>> getAlbum(@Query("album") String album, @Query("api_key") String api_key);

}
