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
            void onError();
            void onSuccess(/*list of exchances+values*/);
        }

        void getExchangeRates(/*currency*/);
    }
}
