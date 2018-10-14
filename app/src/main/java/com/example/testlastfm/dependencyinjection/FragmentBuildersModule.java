package com.example.testlastfm.dependencyinjection;

import com.example.testlastfm.ui.AlbumDetails.AlbumDetailsFragment;
import com.example.testlastfm.ui.SearchAlbums.SearchAlbumFragment;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class FragmentBuildersModule {

    @ContributesAndroidInjector
    abstract SearchAlbumFragment searchAlbumFragment();

    @ContributesAndroidInjector
    abstract AlbumDetailsFragment albumDetailsFragment();


}
