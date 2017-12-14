package com.example.bmoreira.paytmchallenge;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.Spinner;

import com.example.bmoreira.paytmchallenge.adapter.ExchangeAdapter;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements MainMVP.View {

    private MainPresenter presenter;
    @BindView(R.id.tv_value)
    EditText value;
    @BindView(R.id.sp_currency)
    Spinner spinnerCurrency;
    @BindView(R.id.gl_exchanges)
    GridView gridView;

    private ExchangeAdapter exchangeAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        exchangeAdapter = new ExchangeAdapter(this);
        gridView.setAdapter(exchangeAdapter);

        presenter = new MainPresenter(this, new MainInteractor(), exchangeAdapter);
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
        exchangeAdapter.notifyDataSetChanged();
    }

    @Override
    public void updateBaseCurrencyList(String[] items) {
        Log.d("Paytm", "updateBaseCurrencyList Here");

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_dropdown_item, items);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerCurrency.setAdapter(adapter);
        spinnerCurrency.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                presenter.onBaseCurrencyChange(parent.getItemAtPosition(position).toString());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                //Make gridview invisible?
            }
        });
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
