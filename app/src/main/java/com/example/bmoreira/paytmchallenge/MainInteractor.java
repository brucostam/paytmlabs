package com.example.bmoreira.paytmchallenge;

import android.util.Log;

import com.example.bmoreira.paytmchallenge.data.FixerAPI;
import com.example.bmoreira.paytmchallenge.data.Latest;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by bruco on 2017-12-12.
 */

public class MainInteractor implements MainMVP.Interactor {

    public static final String API_URL = "https://api.fixer.io";
    private FixerAPI fixerAPI;

    public MainInteractor() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(API_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        // Create an instance of our GitHub API interface.
        fixerAPI = retrofit.create(FixerAPI.class);
    }

    @Override
    public void getExchangeRates(OnGetExchangeRatesFinishedListener listener) {

    }

    @Override
    public void getBaseCurrencyList(final OnGetBaseCurrencyListFinishedListener listener) {
        // Connect to the server to retrieve the list
        //TODO: use cache if less then 30minutes from last call.
        final Call<Latest> latestCall = fixerAPI.baseCurrencies();

        Thread thread = new Thread() {
            @Override
            public void run() {
                Latest latest = null;
                try {
                    latest = latestCall.execute().body();
                    listener.onSuccessGetBaseCurrency();
                } catch (IOException e) {
                    e.printStackTrace();
                    listener.onErrorGetBaseCurrency();
                }
                Log.d("Paytm", "Latest:" + latest.base);
            }
        };

        thread.start();



    }
}
