package com.example.bmoreira.paytmchallenge.data;


import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by bruco on 2017-12-12.
 */

public interface FixerAPIService {

    /* Get default exchange rates - EUR used as default at Fixer */
    @GET("/latest")
    Call<Latest> defaultExchangeRates();

    /* Use a different exchange rate as the base. Ex: USD */
    @GET("/latest")
    Call<Latest> differentBaseExchangeRates(@Query("base") String base);

}
