package com.example.bmoreira.paytmchallenge.di.component;

import android.app.Application;

import com.example.bmoreira.paytmchallenge.di.module.AppModule;

import dagger.Component;

/**
 * Created by bruco on 2017-12-16.
 */

@Component(modules = {AppModule.class})
public interface AppComponent {
    Application application();
}