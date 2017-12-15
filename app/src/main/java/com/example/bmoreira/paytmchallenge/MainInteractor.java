package com.example.bmoreira.paytmchallenge;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

import com.example.bmoreira.paytmchallenge.data.FixerAPIService;
import com.example.bmoreira.paytmchallenge.data.Latest;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import butterknife.internal.Utils;
import okhttp3.Cache;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
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

    // *Rates should be persisted locally and refreshed no more frequently than every 30 minutes (to limit bandwidth usage)
    public static final int MAX_ONLINE_CACHE_TIME = 60 * 30; // 30 min tolerance
    public static final int MAX_OFFLINE_CACHE_TIME = 60 * 60 * 24; // 1 day tolerance
    public static final int MAX_CACHE_SIZE = 10 * 1024 * 1024; // 10 MB
    private FixerAPIService fixerAPIService;
    private static Context mContext;

    public MainInteractor(Context context) {
        mContext = context;
        OkHttpClient client = new OkHttpClient
                .Builder()
                .cache(new Cache(new File(context.getCacheDir(), "responses"), MAX_CACHE_SIZE)) // 10 MB
                .addNetworkInterceptor(REWRITE_CACHE_CONTROL_INTERCEPTOR)
                .build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(API_URL)
                .client(client)
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

    private static final Interceptor REWRITE_CACHE_CONTROL_INTERCEPTOR = new Interceptor() {
        @Override
        public okhttp3.Response intercept(Chain chain) throws IOException {
            okhttp3.Response originalResponse = chain.proceed(chain.request());
            if (isNetworkAvailable(mContext)) {
                int maxAge = MAX_ONLINE_CACHE_TIME; // read from cache for 1 minute - time in seconds
                return originalResponse.newBuilder()
                        .header("cache-control", "public, max-age=" + maxAge)
                        .build();
            } else {
                int maxStale = MAX_OFFLINE_CACHE_TIME; // tolerate 1 day stale
                return originalResponse.newBuilder()
                        .header("cache-control", "public, only-if-cached, max-stale=" + maxStale)
                        .build();
            }
        }
    };

    public static boolean isNetworkAvailable(Context context) {
        ConnectivityManager cm =
                (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        return activeNetwork != null && activeNetwork.isConnectedOrConnecting();
    }
}
