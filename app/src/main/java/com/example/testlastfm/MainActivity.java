package com.example.testlastfm;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;

import com.example.testlastfm.api.LastFmService;
import com.example.testlastfm.ui.common.NavigationController;

import javax.inject.Inject;

import dagger.android.AndroidInjection;
import dagger.android.AndroidInjector;
import dagger.android.DispatchingAndroidInjector;

import dagger.android.support.HasSupportFragmentInjector;

public class MainActivity extends AppCompatActivity implements HasSupportFragmentInjector {

  @Inject
  DispatchingAndroidInjector<Fragment> dispatchingAndroidInjector;

  @Inject LastFmService lastFmService;

  @Inject NavigationController navigationController;


  @Override protected void onCreate(Bundle savedInstanceState) {

    AndroidInjection.inject(this);
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    if (savedInstanceState == null)
      navigationController.navigateToSearchAlbumFragment();

  }

  @Override
  public AndroidInjector<Fragment> supportFragmentInjector() {
    return dispatchingAndroidInjector;
  }
}
