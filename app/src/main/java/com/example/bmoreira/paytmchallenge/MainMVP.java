package com.example.bmoreira.paytmchallenge;


/**
 * Created by bruco on 2017-12-11.
 */

public interface MainMVP {

    interface View {
        void updateExchangeRatesList(/*list of exchanges+values*/);
        void updateBaseCurrencyList(/*Array of strings*/);
        void showErrorDialog();
        void hideErrorDialog();
    }

    interface Presenter {
        void onCreate();
        void onDestroy();
        void onBaseCurrencyChange(/*currency selected*/);
        void onAmountChange(/*amount*/);
    }

    interface Interactor {
        interface OnGetExchangeRatesFinishedListener {
            void onErrorGetExchangeRates();
            void onSuccessGetExchangeRates(/*list of exchances+values*/);
        }

        void getExchangeRates(String baseCurrency, OnGetExchangeRatesFinishedListener listener);

        interface OnGetBaseCurrencyListFinishedListener {
            void onErrorGetBaseCurrency();
            void onSuccessGetBaseCurrency(/*list of exchances+values*/);

        }
        void getBaseCurrencyList(OnGetBaseCurrencyListFinishedListener listener);
    }
}
