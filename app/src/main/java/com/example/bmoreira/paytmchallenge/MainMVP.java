package com.example.bmoreira.paytmchallenge;

import java.util.Map;

/**
 * Created by bruco on 2017-12-11.
 */

public interface MainMVP {

    interface View {
        void updateExchangeRatesList();
        void updateBaseCurrencyList(String[] items);
        void showErrorDialog();
        void hideErrorDialog();
    }

    interface Presenter {
        void onCreate();
        void onDestroy();
        void onBaseCurrencyChange(String currency);
        void onAmountChange(double amount);
    }

    interface Interactor {
        interface OnGetExchangeRatesFinishedListener {
            void onErrorGetExchangeRates();
            void onSuccessGetExchangeRates(Map<String, Float> exchangeRates);
        }
        void getExchangeRates(String baseCurrency, OnGetExchangeRatesFinishedListener listener);

        interface OnGetBaseCurrencyListFinishedListener {
            void onErrorGetBaseCurrency();
            void onSuccessGetBaseCurrency(String[] items);
        }
        void getBaseCurrencyList(OnGetBaseCurrencyListFinishedListener listener);
    }
}
