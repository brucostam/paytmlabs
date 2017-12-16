package com.example.bmoreira.paytmchallenge;

import com.example.bmoreira.paytmchallenge.adapter.ExchangeAdapterData;

import java.util.Map;

/**
 * Created by bruco on 2017-12-12.
 */

public class MainPresenter implements MainMVP.Presenter,
        MainMVP.Interactor.OnGetExchangeRatesFinishedListener,
        MainMVP.Interactor.OnGetBaseCurrencyListFinishedListener {

    private MainMVP.View mainView;
    private MainMVP.Interactor mainInteractor;
    private ExchangeAdapterData exchangeAdapterData;

    public MainPresenter(MainMVP.View mainView,
                         MainMVP.Interactor mainInteractor,
                         ExchangeAdapterData exchangeAdapterData) {
        this.mainView = mainView;
        this.mainInteractor = mainInteractor;
        this.exchangeAdapterData = exchangeAdapterData;
    }

    @Override
    public void onCreate() {
        mainInteractor.getBaseCurrencyList(this);
    }

    @Override
    public void onDestroy() {
        mainView = null;
    }

    @Override
    public void onBaseCurrencyChange(String currency) {
        mainInteractor.getExchangeRates(currency, this);
    }

    @Override
    public void onAmountChange(double amount) {
        exchangeAdapterData.setBaseValue(amount);
        if (mainView != null) {
            mainView.updateExchangeRatesList();
        }
    }

    /* Interector Listener functions */
    @Override
    public void onErrorGetExchangeRates() {
        if (mainView != null) {
            mainView.hideGrid();
            mainView.showEmptyListState();
        }
    }

    @Override
    public void onSuccessGetExchangeRates(Map<String, Float> exchangeRates) {
        exchangeAdapterData.setExchangeMap(exchangeRates);
        if (mainView != null) {
            mainView.updateExchangeRatesList();
            mainView.showGrid();
            mainView.hideEmptyListState();
        }
    }

    @Override
    public void onErrorGetBaseCurrency() {
        if (mainView != null) {
            mainView.hideGrid();
            mainView.showEmptyListState();
        }
    }

    @Override
    public void onSuccessGetBaseCurrency(String[] items) {
        if (mainView != null) {
            mainView.updateBaseCurrencyList(items);
            mainView.hideEmptyListState();
        }
    }

    public MainMVP.View getMainView() {
        return mainView;
    }
}
