package com.example.bmoreira.paytmchallenge.di.module;

import android.app.Application;

import dagger.Module;
import dagger.Provides;

/**
 * Created by bruco on 2017-12-16.
 */

@Module
public class AppModule {

    private Application mApplication;

    public AppModule(Application application) {
        mApplication = application;
    }

    @Provides
    Application providesApplication() {
        return mApplication;
    }
}