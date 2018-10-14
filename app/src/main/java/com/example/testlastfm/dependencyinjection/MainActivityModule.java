package com.example.testlastfm.dependencyinjection;

import com.example.testlastfm.MainActivity;
import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module public abstract class MainActivityModule {
  @ContributesAndroidInjector(modules = FragmentBuildersModule.class)
  abstract MainActivity contributeMainActivity();
}
