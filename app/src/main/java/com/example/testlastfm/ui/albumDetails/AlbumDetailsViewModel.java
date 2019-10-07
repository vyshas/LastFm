package com.example.testlastfm.ui.albumDetails;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import com.example.testlastfm.model.Album;
import com.example.testlastfm.repository.SearchRepository;

import javax.inject.Inject;

public class AlbumDetailsViewModel extends ViewModel {
    // TODO: Implement the ViewModel

    final MutableLiveData<Album> album;

    SearchRepository searchRepository;

    @Inject
    public AlbumDetailsViewModel(SearchRepository searchRepository) {

        album = new MutableLiveData<>();
        this.searchRepository = searchRepository;
    }

    public LiveData<Album> loadAlbumById(Long albumId) {
        return searchRepository.loadAlbum(albumId);
    }

}
