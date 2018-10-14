package com.example.testlastfm.repository;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Transformations;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.example.testlastfm.AppExecutors;
import com.example.testlastfm.BuildConfig;
import com.example.testlastfm.api.ApiResponse;
import com.example.testlastfm.api.LastFmService;
import com.example.testlastfm.db.LastFmDb;
import com.example.testlastfm.db.LastFmDao;
import com.example.testlastfm.model.Album;
import com.example.testlastfm.model.AlbumSearchRes;
import com.example.testlastfm.model.AlbumSearchResponse;
import com.example.testlastfm.model.Resource;
import com.example.testlastfm.util.AbsentLiveData;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class SearchRepository {

    private final AppExecutors appExecutors;
    private LastFmDb db;
    private final LastFmService lastFmService;
    private final LastFmDao lastFmDao;

    @Inject
    public SearchRepository(AppExecutors appExecutors, LastFmDb db, LastFmService lastFmService, LastFmDao lastFmDao) {
        this.appExecutors = appExecutors;
        this.db = db;
        this.lastFmService = lastFmService;
        this.lastFmDao = lastFmDao;
    }


    public LiveData<Resource<List<Album>>> searchAlbums(String queryAlbumName) {
        return new NetworkBoundResource<List<Album>, AlbumSearchResponse>(appExecutors) {

            @Override
            protected void saveCallResult(@NonNull AlbumSearchResponse item) {

                List<Album> albums = item.getAlbumResults().getAlbummatches().getAlbum();
                //set Images
                for (Album album: albums) {
                    album.albumImage = album.getImage().get(2).getText();
                }

                db.beginTransaction();
                try {

                    List<Long> albumIds = lastFmDao.insertAlbum(albums);

                    AlbumSearchRes albumSearchRes = new AlbumSearchRes(queryAlbumName, albumIds, Integer.valueOf(item.getAlbumResults().getOpensearchTotalResults()));
                    lastFmDao.insertAlbumSearchResult(albumSearchRes);

                    db.setTransactionSuccessful();
                } finally {
                    db.endTransaction();
                }

            }

            @Override
            protected boolean shouldFetch(@Nullable List<Album> data) {
                return data == null;
            }


            @NonNull
            @Override
            protected LiveData<List<Album>> loadFromDb() {

                return Transformations.switchMap(lastFmDao.search(queryAlbumName), searchData -> {
                    if (searchData == null) {
                        return AbsentLiveData.create();
                    } else {
                        return lastFmDao.loadOrderedAlbums(searchData.albumIds);
                    }
                });

            }

            @NonNull
            @Override
            protected LiveData<ApiResponse<AlbumSearchResponse>> createCall() {
                return lastFmService.getAlbum(queryAlbumName, BuildConfig.ApiKey);
            }
        }.asLiveData();
    }


    public LiveData<Album> loadAlbum(Long albumId) {

       return lastFmDao.loadAlbumById(albumId);

    }
}
