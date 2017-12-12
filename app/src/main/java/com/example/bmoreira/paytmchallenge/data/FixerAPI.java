package com.example.bmoreira.paytmchallenge.data;


import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by bruco on 2017-12-12.
 */

public interface FixerAPI {

    @GET("/latest")
    Call<Latest> baseCurrencies();

}
