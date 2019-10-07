

package com.example.testlastfm.ui.common;

import android.support.v4.app.FragmentManager;

import com.example.testlastfm.MainActivity;
import com.example.testlastfm.R;
import com.example.testlastfm.ui.albumDetails.AlbumDetailsFragment;
import com.example.testlastfm.ui.searchAlbums.SearchAlbumFragment;

import javax.inject.Inject;

/**
 * A utility class that handles navigation in {@link MainActivity}.
 */
public class NavigationController {
    private final int containerId;
    private final FragmentManager fragmentManager;

    @Inject
    public NavigationController(MainActivity mainActivity) {
        this.containerId = R.id.container;
        this.fragmentManager = mainActivity.getSupportFragmentManager();
    }

    public void navigateToSearchAlbumFragment() {
        String tag = "searchAlbum";
        SearchAlbumFragment searchAlbumFragment = new SearchAlbumFragment();
        fragmentManager.beginTransaction()
                       .replace(containerId,searchAlbumFragment,tag)
                       .addToBackStack(null)
                       .commitAllowingStateLoss();
    }


    public void navigateToAlbumDetails(Long albumId) {
        AlbumDetailsFragment fragment = AlbumDetailsFragment.Companion.create(albumId);
        String tag = "albumdetails" + "/" + albumId;
        fragmentManager.beginTransaction()
                       .replace(containerId, fragment, tag)
                       .addToBackStack(null)
                       .commitAllowingStateLoss();
    }

}
