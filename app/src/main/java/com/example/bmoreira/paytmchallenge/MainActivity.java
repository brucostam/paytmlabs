package com.example.bmoreira.paytmchallenge;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

public class MainActivity extends AppCompatActivity implements MainMVP.View {

    private MainPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        presenter = new MainPresenter(this, new MainInteractor());
        presenter.onCreate();
    }

    @Override
    protected void onDestroy() {
        presenter.onDestroy();
        super.onDestroy();
    }

    @Override
    public void updateExchangeRatesList() {
        Log.d("Paytm", "updateExchangeRatesList Here");
    }

    @Override
    public void updateBaseCurrencyList() {
        Log.d("Paytm", "updateBaseCurrencyList Here");
    }

    @Override
    public void showErrorDialog() {
        Log.d("Paytm", "showErrorDialog Here");
    }

    @Override
    public void hideErrorDialog() {
        Log.d("Paytm", "hideErrorDialog Here");
    }
}
