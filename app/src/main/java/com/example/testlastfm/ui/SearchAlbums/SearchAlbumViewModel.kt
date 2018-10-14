package com.example.testlastfm.ui.SearchAlbums

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.Transformations
import android.arch.lifecycle.ViewModel;
import com.example.testlastfm.model.Album
import com.example.testlastfm.model.Resource
import com.example.testlastfm.repository.SearchRepository
import com.example.testlastfm.util.AbsentLiveData
import java.util.*
import javax.inject.Inject

class SearchAlbumViewModel @Inject constructor(private val searchRepository: SearchRepository) : ViewModel() {


    private val query = MutableLiveData<String>()

    val results: LiveData<Resource<List<Album>>> = Transformations
            .switchMap(query) { search ->
                if (search.isNullOrBlank()) {
                    AbsentLiveData.create()
                } else {
                    searchRepository.searchAlbums(search)
                }
            }


    fun setQuery(originalInput: String) {
        val input = originalInput.toLowerCase(Locale.getDefault()).trim()
        if (input == query.value) {
            return
        }
        query.value = input
    }



    fun refresh() {
        query.value?.let {
            query.value = it
        }
    }

    class LoadMoreState(val isRunning: Boolean, val errorMessage: String?) {
        private var handledError = false

        val errorMessageIfNotHandled: String?
            get() {
                if (handledError) {
                    return null
                }
                handledError = true
                return errorMessage
            }
    }


}
