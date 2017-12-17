package com.example.bmoreira.paytmchallenge.di.component;

import android.app.Application;

import com.example.bmoreira.paytmchallenge.di.module.NetModule;

import javax.inject.Singleton;

import dagger.Component;
import retrofit2.Retrofit;

/**
 * Created by bruco on 2017-12-16.
 */

@Singleton
@Component(dependencies = AppComponent.class, modules = {NetModule.class})
public interface NetComponent {
    Retrofit retrofit();
    Application application();
}
