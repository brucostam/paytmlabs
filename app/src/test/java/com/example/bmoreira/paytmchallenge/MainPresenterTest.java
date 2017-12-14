package com.example.bmoreira.paytmchallenge;

import android.widget.BaseAdapter;

import com.example.bmoreira.paytmchallenge.adapter.ExchangeAdapterData;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.junit.Assert.assertNull;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

/**
 * Created by bruco on 2017-12-13.
 */

@RunWith(MockitoJUnitRunner.class)
public class MainPresenterTest {

    @Mock
    MainActivity view;
    @Mock
    MainInteractor interactor;
    @Mock
    ExchangeAdapterData exchangeAdapterData;

    private MainPresenter presenter;

    @Before
    public void setUp() throws Exception {
        presenter = new MainPresenter(view, interactor, exchangeAdapterData);
    }

    @Test
    public void checkIfGetsBaseCurrencyListOnCretion() {
        presenter.onCreate();
        verify(interactor, times(1)).getBaseCurrencyList(presenter);
    }

    @Test
    public void checkIfViewIsReleasedOnDestroy() {
        presenter.onDestroy();
        assertNull(presenter.getMainView());
    }

    @Test
    public void checkIfGetExchangeRatesListOnBaseCurrencyChangeWithCurrencyBRL() {
        String currency = "BRL";
        presenter.onBaseCurrencyChange(currency);
        verify(interactor, times(1)).getExchangeRates(currency, presenter);
    }

    @Test
    public void checkIfGetExchangeRatesListOnBaseCurrencyChangeWithCurrencyUSD() {
        String currency = "USD";
        presenter.onBaseCurrencyChange(currency);
        verify(interactor, times(1)).getExchangeRates(currency, presenter);
    }

    @Test
    public void checkIfGetExchangeRatesListOnBaseCurrencyChangeWithCurrencyEUR() {
        String currency = "EUR";
        presenter.onBaseCurrencyChange(currency);
        verify(interactor, times(1)).getExchangeRates(currency, presenter);
    }

    @Test
    public void checkIfOnAmountChangeTheBaseValueUpdatesAndUpdateGridIsCalled() {
        double amount = 1023;
        presenter.onAmountChange(amount);
        verify(exchangeAdapterData, times(1)).setBaseValue(amount);
        verify(view, times(1)).updateExchangeRatesList();
    }

    /* Interector Listener functions */
    public void onErrorGetExchangeRates() {

    }

    public void onSuccessGetExchangeRates() {

    }

    public void onErrorGetBaseCurrency() {
//        if (mainView != null) {
//            mainView.showErrorDialog();
//        }
    }

    public void onSuccessGetBaseCurrency() {
//        if (mainView != null) {
//            mainView.updateBaseCurrencyList();
//        }
    }

}
