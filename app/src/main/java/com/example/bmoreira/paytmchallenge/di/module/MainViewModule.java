package com.example.bmoreira.paytmchallenge.di.module;

import com.example.bmoreira.paytmchallenge.MainMVP;
import com.example.bmoreira.paytmchallenge.di.CustomScope;

import dagger.Module;
import dagger.Provides;

/**
 * Created by bruco on 2017-12-16.
 */

@Module
public class MainViewModule {
    private final MainMVP.View mView;


    public MainViewModule(MainMVP.View mView) {
        this.mView = mView;
    }

    @Provides
    @CustomScope
    MainMVP.View providesMainViewContract() {
        return mView;
    }
}