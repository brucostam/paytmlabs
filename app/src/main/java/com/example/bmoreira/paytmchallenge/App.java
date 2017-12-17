package com.example.bmoreira.paytmchallenge;

import android.app.Application;

import com.example.bmoreira.paytmchallenge.di.component.AppComponent;
import com.example.bmoreira.paytmchallenge.di.component.DaggerAppComponent;
import com.example.bmoreira.paytmchallenge.di.module.AppModule;

/**
 * Created by bruco on 2017-12-16.
 */

public class App extends Application {
    private AppComponent mAppComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        mAppComponent = DaggerAppComponent.builder()
                .appModule(new AppModule(this))
                .build();
    }

    public AppComponent getAppComponent() {
        return mAppComponent;
    }
}