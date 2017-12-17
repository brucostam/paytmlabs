package com.example.bmoreira.paytmchallenge.di.module;

import android.app.Application;

import com.example.bmoreira.paytmchallenge.util.NetworkStatus;

import java.io.IOException;
import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.Cache;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by bruco on 2017-12-16.
 */

@Module
public class NetModule {

    private String mBaseUrl;

    // *Rates should be persisted locally and refreshed no more
    // frequently than every 30 minutes (to limit bandwidth usage)
    public static final int MAX_ONLINE_CACHE_TIME = 60 * 30; // 30 min tolerance
    public static final int MAX_OFFLINE_CACHE_TIME = 60 * 60 * 24; // 1 day tolerance
    public static final int MAX_CACHE_SIZE = 10 * 1024 * 1024; // 10 MB

    // Constructor needs one parameter to instantiate.
    public NetModule(String baseUrl) {
        this.mBaseUrl = baseUrl;
    }

    @Provides
    @Singleton
    Cache provideOkHttpCache(Application application) {
        Cache cache = new Cache(application.getCacheDir(), MAX_CACHE_SIZE);
        return cache;
    }

    @Provides
    @Singleton
    Interceptor provideOkHttpInterceptor(final Application application) {
        Interceptor interceptor = new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                okhttp3.Response originalResponse = chain.proceed(chain.request());
                if (NetworkStatus.isNetworkAvailable(application)) {
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

        return interceptor;
    }

    @Provides
    @Singleton
    OkHttpClient provideOkHttpClient(Cache cache, Interceptor interceptor) {
        OkHttpClient client = new OkHttpClient
                .Builder()
                .cache(cache)
                .addNetworkInterceptor(interceptor)
                .build();
        return client;
    }

    @Provides
    @Singleton
    Retrofit provideRetrofit(OkHttpClient okHttpClient) {
        Retrofit retrofit = new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(mBaseUrl)
                .client(okHttpClient)
                .build();
        return retrofit;
    }
}
