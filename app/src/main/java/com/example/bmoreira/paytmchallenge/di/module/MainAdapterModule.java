package com.example.bmoreira.paytmchallenge.di.module;

import android.app.Application;

import com.example.bmoreira.paytmchallenge.adapter.ExchangeAdapter;
import com.example.bmoreira.paytmchallenge.adapter.ExchangeAdapterData;
import com.example.bmoreira.paytmchallenge.di.CustomScope;

import dagger.Module;
import dagger.Provides;

/**
 * Created by bruco on 2017-12-16.
 */
@Module
public class MainAdapterModule {

    private ExchangeAdapter exchangeAdapter;

    @Provides
    @CustomScope
    public ExchangeAdapter providesMyExchangeAdapter(Application application) {
        if (exchangeAdapter == null) {
            exchangeAdapter = new ExchangeAdapter(application.getApplicationContext());
        }
        return exchangeAdapter;
    }

    @Provides
    @CustomScope
    public ExchangeAdapterData providesMyExchangeAdapterData(Application application) {
        if (exchangeAdapter == null) {
            exchangeAdapter = new ExchangeAdapter(application.getApplicationContext());
        }
        return exchangeAdapter;
    }
}
