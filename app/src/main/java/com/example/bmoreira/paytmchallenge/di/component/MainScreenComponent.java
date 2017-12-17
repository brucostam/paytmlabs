package com.example.bmoreira.paytmchallenge.di.component;

import com.example.bmoreira.paytmchallenge.MainActivity;
import com.example.bmoreira.paytmchallenge.adapter.ExchangeAdapter;
import com.example.bmoreira.paytmchallenge.adapter.ExchangeAdapterData;
import com.example.bmoreira.paytmchallenge.di.CustomScope;
import com.example.bmoreira.paytmchallenge.di.module.FixerApiModule;
import com.example.bmoreira.paytmchallenge.di.module.MainAdapterModule;
import com.example.bmoreira.paytmchallenge.di.module.MainInteractorModel;
import com.example.bmoreira.paytmchallenge.di.module.MainViewModule;


import dagger.Component;

/**
 * Created by bruco on 2017-12-16.
 */

@CustomScope
@Component(dependencies = {NetComponent.class},
        modules = {MainViewModule.class,
                MainAdapterModule.class,
                FixerApiModule.class,
                MainInteractorModel.class})
public interface MainScreenComponent {
    void inject(MainActivity activity);
    ExchangeAdapter exchangeAdapter();
    ExchangeAdapterData exchangeAdapterData();
}