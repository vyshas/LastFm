package com.example.testlastfm.dependencyinjection;

import com.example.testlastfm.ui.albumDetails.AlbumDetailsFragment;
import com.example.testlastfm.ui.searchAlbums.SearchAlbumFragment;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class FragmentBuildersModule {

    @ContributesAndroidInjector
    abstract SearchAlbumFragment searchAlbumFragment();

    @ContributesAndroidInjector
    abstract AlbumDetailsFragment albumDetailsFragment();

}
