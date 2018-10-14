package com.example.testlastfm.repository;

import static org.mockito.ArgumentMatchers.anyObject;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

import android.arch.core.executor.testing.InstantTaskExecutorRule;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.Observer;

import com.example.testlastfm.BuildConfig;
import com.example.testlastfm.InstantAppExecutors;
import com.example.testlastfm.api.ApiResponse;
import com.example.testlastfm.api.LastFmService;
import com.example.testlastfm.db.LastFmDb;
import com.example.testlastfm.db.LastFmDao;
import com.example.testlastfm.model.Album;
import com.example.testlastfm.model.AlbumResults;
import com.example.testlastfm.model.AlbumSearchRes;
import com.example.testlastfm.model.AlbumSearchResponse;
import com.example.testlastfm.model.Albummatches;
import com.example.testlastfm.model.Resource;
import com.example.testlastfm.util.AbsentLiveData;
import com.example.testlastfm.util.TestUtil;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import retrofit2.Response;

@SuppressWarnings("unchecked")
@RunWith(JUnit4.class)
public class SearchRepositoryTest {

    private SearchRepository repository;
    private LastFmDao dao;
    private LastFmService service;


    @Rule
    public InstantTaskExecutorRule instantExecutorRule = new InstantTaskExecutorRule();

    @Before
    public void init() {
        dao = mock(LastFmDao.class);
        service = mock(LastFmService.class);
        LastFmDb db = mock(LastFmDb.class);
        when(db.lastFmDao()).thenReturn(dao);
        repository = new SearchRepository(new InstantAppExecutors(), db, service,dao);
    }

    @Test
    public void searchAlbum_fromDb(){
        long[] arr = {1,2};
        List<Long> ids = Arrays.stream(arr).boxed().collect(Collectors.toList());

        Observer<Resource<List<Album>>> observer = mock(Observer.class);
        MutableLiveData<AlbumSearchRes> dbSearchResult = new MutableLiveData<>();
        MutableLiveData<List<Album>> albums = new MutableLiveData<>();


        when(dao.search("foo")).thenReturn(dbSearchResult);

        repository.searchAlbums("foo").observeForever(observer);

        verify(observer).onChanged(Resource.loading(null));

        AlbumSearchRes dbResult = new AlbumSearchRes("foo", ids, 2);
        when(dao.loadOrderedAlbums(ids)).thenReturn(albums);

        dbSearchResult.postValue(dbResult);

        List<Album> repoList = new ArrayList<>();
        albums.postValue(repoList);
        verify(observer).onChanged(Resource.success(repoList));
        verifyNoMoreInteractions(service);


    }

    @Test
    public void search_fromServer() {
        long[] arr = {1,2};
        List<Long> ids = Arrays.stream(arr).boxed().collect(Collectors.toList());

        Album album1 = TestUtil.createAlbum(1, "shakiraAlbum", "shakira", "mbid1");
        Album album2 = TestUtil.createAlbum(2, "eminemAlbum", "eminem", "mbid2");

        Observer<Resource<List<Album>>> observer = mock(Observer.class);
        MutableLiveData<AlbumSearchRes> dbSearchResult = new MutableLiveData<>();
        MutableLiveData<List<Album>> albums = new MutableLiveData<>();

        AlbumSearchResponse apiResponse = new AlbumSearchResponse();
        List<Album> albumList = Arrays.asList(album1, album2);
        AlbumResults albumResults = TestUtil.createAlbumResults("2",new Albummatches(albumList));
        apiResponse.setAlbumResults(albumResults);


        MutableLiveData<ApiResponse<AlbumSearchResponse>> callLiveData = new MutableLiveData<>();
        when(service.getAlbum("shakiraAlbum",BuildConfig.ApiKey)).thenReturn(callLiveData);

        when(dao.search("shakiraAlbum")).thenReturn(dbSearchResult);

        repository.searchAlbums("shakiraAlbum").observeForever(observer);

        verify(observer).onChanged(Resource.loading(null));
        verifyNoMoreInteractions(service);
        reset(observer);

        when(dao.loadOrderedAlbums(ids)).thenReturn(albums);
        dbSearchResult.postValue(null);
        verify(dao, never()).loadOrderedAlbums(anyObject());

        verify(service).getAlbum("shakiraAlbum",BuildConfig.ApiKey);
        MutableLiveData<AlbumSearchRes> updatedResult = new MutableLiveData<>();
        when(dao.search("shakiraAlbum")).thenReturn(updatedResult);
        updatedResult.postValue(new AlbumSearchRes("shakiraAlbum", ids,  2));

        //FIXME
 /*       callLiveData.postValue(new ApiResponse<>(Response.success(apiResponse)));
        verify(dao).insertAlbum(albumList);
        albums.postValue(albumList);
        verify(observer).onChanged(Resource.success(albumList));
        verifyNoMoreInteractions(service);*/
    }


    @Test
    public void search_fromServer_error() {
        when(dao.search("shakiraalbum")).thenReturn(AbsentLiveData.create());
        MutableLiveData<ApiResponse<AlbumSearchResponse>> apiResponse = new MutableLiveData<>();
        when(service.getAlbum("shakiraalbum",BuildConfig.ApiKey)).thenReturn(apiResponse);

        Observer<Resource<List<Album>>> observer = mock(Observer.class);
        repository.searchAlbums("shakiraalbum").observeForever(observer);
        verify(observer).onChanged(Resource.loading(null));

        apiResponse.postValue(new ApiResponse<>(new Exception("idk")));
        verify(observer).onChanged(Resource.error("idk", null));
    }

}