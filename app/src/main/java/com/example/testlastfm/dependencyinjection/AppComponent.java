package com.example.testlastfm.dependencyinjection;

import android.app.Application;

import com.example.testlastfm.TestLastFm;

import javax.inject.Singleton;

import dagger.BindsInstance;
import dagger.Component;
import dagger.android.AndroidInjectionModule;

@Singleton @Component(modules = {
    AndroidInjectionModule.class, MainActivityModule.class, AppModule.class
}) public interface AppComponent {

  @Component.Builder interface Builder {

    @BindsInstance
    Builder application(Application application);

    AppComponent build();
  }

  void inject(TestLastFm testLastFm);
}
