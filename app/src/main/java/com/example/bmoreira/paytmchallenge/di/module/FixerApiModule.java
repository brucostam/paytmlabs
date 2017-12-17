package com.example.bmoreira.paytmchallenge.di.module;

import com.example.bmoreira.paytmchallenge.data.FixerAPIService;
import com.example.bmoreira.paytmchallenge.di.CustomScope;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;

/**
 * Created by bruco on 2017-12-17.
 */

@Module
public class FixerApiModule {

    @Provides
    @CustomScope
    public FixerAPIService providesFixerAPIService(Retrofit retrofit) {
        return retrofit.create(FixerAPIService.class);
    }
}


