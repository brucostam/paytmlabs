package com.example.bmoreira.paytmchallenge;

/**
 * Created by bruco on 2017-12-12.
 */

public class MainPresenter implements MainMVP.Presenter,
        MainMVP.Interactor.OnGetExchangeRatesFinishedListener,
        MainMVP.Interactor.OnGetBaseCurrencyListFinishedListener {

    private MainMVP.View mainView;
    private MainMVP.Interactor mainInteractor;

    public MainPresenter(MainMVP.View mainView, MainMVP.Interactor mainInteractor) {
        this.mainView = mainView;
        this.mainInteractor = mainInteractor;
    }

    @Override
    public void onCreate() {

    }

    @Override
    public void onDestroy() {

    }

    @Override
    public void onBaseCurrencyChange() {

    }

    @Override
    public void onAmountChange() {

    }

    /* Interector Listener functions */
    @Override
    public void onErrorGetExchangeRates() {

    }

    @Override
    public void onSuccessGetExchangeRates() {

    }

    @Override
    public void onErrorGetBaseCurrency() {

    }

    @Override
    public void onSuccessGetBaseCurrency() {

    }
}
