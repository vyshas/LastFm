package com.example.testlastfm.ui.albumDetails

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import com.example.testlastfm.model.Album
import com.example.testlastfm.repository.SearchRepository
import javax.inject.Inject

class AlbumDetailsViewModel @Inject constructor(
    private var searchRepository: SearchRepository
) : ViewModel() {
    // TODO: Implement the ViewModel

    internal val album: MutableLiveData<Album> = MutableLiveData()

    fun loadAlbumById(albumId: Long?): LiveData<Album?>? {
        return searchRepository.loadAlbum(albumId)
    }
}