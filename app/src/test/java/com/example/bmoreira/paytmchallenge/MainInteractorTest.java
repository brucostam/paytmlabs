package com.example.bmoreira.paytmchallenge;

import com.example.bmoreira.paytmchallenge.data.FixerAPIService;
import com.example.bmoreira.paytmchallenge.data.Latest;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.HashMap;
import java.util.Map;


import okhttp3.MediaType;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import static org.mockito.Matchers.anyMap;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static retrofit2.Response.success;

/**
 * Created by bruco on 2017-12-12.
 */

@RunWith(MockitoJUnitRunner.class)
public class MainInteractorTest {

    MainMVP.Interactor interactor;

    @Mock
    FixerAPIService service;
    @Mock
    Call<Latest> latestCall;

    @Mock
    MainMVP.Interactor.OnGetExchangeRatesFinishedListener onGetExchangeRatesFinishedListener;

    @Mock
    MainMVP.Interactor.OnGetBaseCurrencyListFinishedListener onGetBaseCurrencyListFinishedListener;

    @Captor
    private ArgumentCaptor<Callback<Latest>> cb;

    @Before
    public void setUp() {

        interactor = new MainInteractor(service);
    }

    @Test
    public void checkIfGetExchangeRatesCallsOnSuccessGetExchangeRatesWhenResponseIsSuccessful() throws Exception {
        when(service.differentBaseExchangeRates(anyString())).thenReturn(latestCall);
        interactor.getExchangeRates("USD", onGetExchangeRatesFinishedListener);
        verify(latestCall).enqueue(cb.capture());

        Latest latest = new Latest();
        latest.setBase("CAD");
        Map<String, Float> map = new HashMap<>();
        map.put("USD", 0.34f);
        map.put("BRL", 0.12f);
        latest.setRates(map);
        cb.getValue().onResponse(latestCall, success(latest));
        verify(onGetExchangeRatesFinishedListener, times(1)).onSuccessGetExchangeRates(anyMap());
    }

    @Test
    public void checkIfGetExchangeRatesCallsonErrorGetExchangeRatesWhenResponseIsNotSuccessful() throws Exception {
        when(service.differentBaseExchangeRates(anyString())).thenReturn(latestCall);
        interactor.getExchangeRates("USD", onGetExchangeRatesFinishedListener);
        verify(latestCall).enqueue(cb.capture());

        cb.getValue().onResponse(latestCall, retrofit2.Response.<Latest>error(400,ResponseBody.create(
                MediaType.parse("application/json"),
                "{\"key\":[\"somestuff\"]}")));
        verify(onGetExchangeRatesFinishedListener, times(1)).onErrorGetExchangeRates();
    }

    @Test
    public void checkIfGetExchangeRatesCallsonErrorGetExchangeRatesWhenOnFailureHappens() throws Exception {
        when(service.differentBaseExchangeRates(anyString())).thenReturn(latestCall);
        interactor.getExchangeRates("USD", onGetExchangeRatesFinishedListener);
        verify(latestCall).enqueue(cb.capture());

        cb.getValue().onFailure(latestCall, new Throwable());
        verify(onGetExchangeRatesFinishedListener, times(1)).onErrorGetExchangeRates();
    }

//    -----------------------------------------------------------------------------------
    @Test
    public void checkIfGetBaseCurrencyListCallsOnSuccessGetBaseCurrencyWhenResponseIsSuccessful() throws Exception {
    when(service.defaultExchangeRates()).thenReturn(latestCall);
    interactor.getBaseCurrencyList(onGetBaseCurrencyListFinishedListener);
    verify(latestCall).enqueue(cb.capture());

    Latest latest = new Latest();
    latest.setBase("CAD");
    Map<String, Float> map = new HashMap<>();
    map.put("USD", 0.34f);
    map.put("BRL", 0.12f);
    latest.setRates(map);
    cb.getValue().onResponse(latestCall, success(latest));
    verify(onGetBaseCurrencyListFinishedListener, times(1)).onSuccessGetBaseCurrency(new String[]{"BRL","CAD","USD"});
}

    @Test
    public void checkIfGetBaseCurrencyListCallsOnErrorGetBaseCurrencyWhenResponseIsNotSuccessful() throws Exception {
        when(service.defaultExchangeRates()).thenReturn(latestCall);
        interactor.getBaseCurrencyList(onGetBaseCurrencyListFinishedListener);
        verify(latestCall).enqueue(cb.capture());

        cb.getValue().onResponse(latestCall, retrofit2.Response.<Latest>error(400,ResponseBody.create(
                MediaType.parse("application/json"),
                "{\"key\":[\"somestuff\"]}")));
        verify(onGetBaseCurrencyListFinishedListener, times(1)).onErrorGetBaseCurrency();
    }

    @Test
    public void checkIfGetBaseCurrencyListCallsOnErrorGetBaseCurrencyWhenOnFailureHappens() throws Exception {
        when(service.defaultExchangeRates()).thenReturn(latestCall);
        interactor.getBaseCurrencyList(onGetBaseCurrencyListFinishedListener);
        verify(latestCall).enqueue(cb.capture());

        cb.getValue().onFailure(latestCall, new Throwable());
        verify(onGetBaseCurrencyListFinishedListener, times(1)).onErrorGetBaseCurrency();
    }
}

