package com.example.bmoreira.paytmchallenge;

import com.example.bmoreira.paytmchallenge.data.FixerAPIService;
import com.example.bmoreira.paytmchallenge.data.Latest;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by bruco on 2017-12-12.
 */

public class MainInteractor implements MainMVP.Interactor {

    private FixerAPIService fixerAPIService;

    @Inject
    public MainInteractor(FixerAPIService localFixerAPIService) {
        this.fixerAPIService = localFixerAPIService;
    }

    @Override
    public void getExchangeRates(String baseCurrency, final OnGetExchangeRatesFinishedListener listener) {
        // Connect to the server to retrieve the list
        final Call<Latest> latestCall = fixerAPIService.differentBaseExchangeRates(baseCurrency);
        latestCall.enqueue(new Callback<Latest>() {
            @Override
            public void onResponse(Call<Latest> call, Response<Latest> response) {
                if (response.isSuccessful()) {
                    // tasks available
                    listener.onSuccessGetExchangeRates(response.body().getRates());
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
