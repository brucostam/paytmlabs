package com.example.bmoreira.paytmchallenge;

import android.util.Log;

import com.example.bmoreira.paytmchallenge.data.FixerAPIService;
import com.example.bmoreira.paytmchallenge.data.Latest;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by bruco on 2017-12-12.
 */

public class MainInteractor implements MainMVP.Interactor {

    public static final String API_URL = "https://api.fixer.io";
    private FixerAPIService fixerAPIService;

    public MainInteractor() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(API_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        fixerAPIService = retrofit.create(FixerAPIService.class);
    }

    @Override
    public void getExchangeRates(String baseCurrency, final OnGetExchangeRatesFinishedListener listener) {
        // Connect to the server to retrieve the list
        //TODO: use cache if less then 30minutes from last call.
        final Call<Latest> latestCall = fixerAPIService.differentBaseExchangeRates(baseCurrency);
        latestCall.enqueue(new Callback<Latest>() {
            @Override
            public void onResponse(Call<Latest> call, Response<Latest> response) {
                if (response.isSuccessful()) {
                    // tasks available
                    listener.onSuccessGetExchangeRates(response.body().getRates());
                    Log.d("Paytm", "Latest:" + response.body().getBase());
                } else {
                    // error response, no access to resource?
                    listener.onErrorGetExchangeRates();
                }

            }

            @Override
            public void onFailure(Call<Latest> call, Throwable t) {
                listener.onErrorGetExchangeRates();
            }
        });
    }


    @Override
    public void getBaseCurrencyList(final OnGetBaseCurrencyListFinishedListener listener) {
        // Connect to the server to retrieve the list
        //TODO: use cache if less then 30minutes from last call.
        final Call<Latest> latestCall = fixerAPIService.defaultExchangeRates();
        latestCall.enqueue(new Callback<Latest>() {
            @Override
            public void onResponse(Call<Latest> call, Response<Latest> response) {
                if (response.isSuccessful()) {
                    // tasks available
                    Map<String, Float> rates = response.body().getRates();

                    List<String> sortedList = new ArrayList<>();
                    sortedList.addAll(rates.keySet());
                    sortedList.add(response.body().getBase());
                    Collections.sort(sortedList);

                    String[] items = sortedList.toArray(new String[sortedList.size()]);
                    listener.onSuccessGetBaseCurrency(items);
                    Log.d("Paytm", "Latest:" + response.body().getBase());
                } else {
                    // error response, no access to resource?
                    listener.onErrorGetBaseCurrency();
                }

            }

            @Override
            public void onFailure(Call<Latest> call, Throwable t) {
                listener.onErrorGetBaseCurrency();
            }
        });
    }
}
