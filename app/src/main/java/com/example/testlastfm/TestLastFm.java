package com.example.testlastfm;

import android.app.Activity;
import android.app.Application;

import com.example.testlastfm.dependencyinjection.AppInjector;
import com.facebook.stetho.Stetho;

import javax.inject.Inject;

import dagger.android.DispatchingAndroidInjector;
import dagger.android.HasActivityInjector;

public class TestLastFm extends Application implements HasActivityInjector {

    @Inject
    DispatchingAndroidInjector<Activity> dispatchingAndroidInjector;

    @Override
    public void onCreate() {
        super.onCreate();

        AppInjector.init(this);

        initSonar();

    }

    @Override
    public DispatchingAndroidInjector<Activity> activityInjector() {
        return dispatchingAndroidInjector;
    }

    private void initSonar() {

        Stetho.initializeWithDefaults(this);

    }
}
