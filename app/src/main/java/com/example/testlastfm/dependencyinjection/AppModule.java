
package com.example.testlastfm.dependencyinjection;


import android.app.Application;
import android.arch.persistence.room.Room;

import com.example.testlastfm.api.LastFmService;
import com.example.testlastfm.db.LastFmDb;
import com.example.testlastfm.db.LastFmDao;
import com.example.testlastfm.ui.common.ViewModelModule;
import com.example.testlastfm.util.LiveDataCallAdapterFactory;
import com.facebook.stetho.okhttp3.StethoInterceptor;

import java.util.concurrent.TimeUnit;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


@Module(includes = ViewModelModule.class)
class AppModule {


    @Singleton
    @Provides
    LastFmService provideLastFmService(OkHttpClient okHttpClient) {
        return new Retrofit.Builder()
                .baseUrl("http://ws.audioscrobbler.com/2.0/")
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(new LiveDataCallAdapterFactory())
                .client(okHttpClient)
                .build()
                .create(LastFmService.class);

    }


    @Singleton
    @Provides
    OkHttpClient provideOkHttpClient() {

        HttpLoggingInterceptor.Level logLevel = HttpLoggingInterceptor.Level.BODY;
        HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor();
        httpLoggingInterceptor.setLevel(logLevel);

        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .connectTimeout(60, TimeUnit.SECONDS)
                .readTimeout(60, TimeUnit.SECONDS)
                .addInterceptor(httpLoggingInterceptor)
                .addNetworkInterceptor(new StethoInterceptor())
                .writeTimeout(10, TimeUnit.MINUTES)
                .build();

        return okHttpClient;
    }

    @Singleton
    @Provides
    LastFmDb provideDb(Application app) {
        return Room.databaseBuilder(app, LastFmDb.class, "github.db").build();
    }


    @Singleton
    @Provides
    LastFmDao provideLastFmDao(LastFmDb db) {
        return db.lastFmDao();
    }


}
