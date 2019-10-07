package com.example.testlastfm.ui.common

import android.support.v4.app.FragmentManager
import com.example.testlastfm.MainActivity
import com.example.testlastfm.R.id
import com.example.testlastfm.ui.albumDetails.AlbumDetailsFragment.Companion.create
import com.example.testlastfm.ui.searchAlbums.SearchAlbumFragment
import javax.inject.Inject

/**
 * A utility class that handles navigation in [MainActivity].
 */
class NavigationController @Inject constructor(mainActivity: MainActivity) {

    private val containerId: Int = id.container
    private val fragmentManager: FragmentManager = mainActivity.supportFragmentManager

    fun navigateToSearchAlbumFragment() {
        val tag = "searchAlbum"
        val searchAlbumFragment =
            SearchAlbumFragment()
        fragmentManager.beginTransaction()
            .replace(containerId, searchAlbumFragment, tag)
            .addToBackStack(null)
            .commitAllowingStateLoss()
    }

    fun navigateToAlbumDetails(albumId: Long) {
        val fragment =
            create(albumId)
        val tag = "albumdetails/$albumId"
        fragmentManager.beginTransaction()
            .replace(containerId, fragment, tag)
            .addToBackStack(null)
            .commitAllowingStateLoss()
    }
}