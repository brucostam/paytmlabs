package com.example.bmoreira.paytmchallenge.di.module;

import com.example.bmoreira.paytmchallenge.MainInteractor;
import com.example.bmoreira.paytmchallenge.MainMVP;
import com.example.bmoreira.paytmchallenge.data.FixerAPIService;
import com.example.bmoreira.paytmchallenge.di.CustomScope;

import dagger.Module;
import dagger.Provides;

/**
 * Created by bruco on 2017-12-16.
 */

@Module
public class MainInteractorModel {

    @Provides
    @CustomScope
    public MainMVP.Interactor providesMainInteractor(FixerAPIService fixerAPIService) {
        return new MainInteractor(fixerAPIService);
    }
}
