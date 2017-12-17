package com.example.bmoreira.paytmchallenge;

import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.Spinner;

import com.example.bmoreira.paytmchallenge.adapter.ExchangeAdapter;
import com.example.bmoreira.paytmchallenge.di.component.DaggerMainScreenComponent;
import com.example.bmoreira.paytmchallenge.di.component.DaggerNetComponent;
import com.example.bmoreira.paytmchallenge.di.component.NetComponent;
import com.example.bmoreira.paytmchallenge.di.module.MainViewModule;
import com.example.bmoreira.paytmchallenge.di.module.NetModule;
import com.example.bmoreira.paytmchallenge.util.CleanStringChangeListener;
import com.example.bmoreira.paytmchallenge.util.CurrencyTextWatcher;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements MainMVP.View,
        CleanStringChangeListener,
        AdapterView.OnItemSelectedListener {

    // Functional Requirement: Exchange rates must be fetched from: http://fixer.io/
    public static final String API_URL = "https://api.fixer.io";

    private MainPresenter presenter;
    private ExchangeAdapter exchangeAdapter;

    @BindView(R.id.tv_value)
    EditText value;
    @BindView(R.id.sp_currency)
    Spinner spinnerCurrency;
    @BindView(R.id.gl_exchanges)
    GridView gridView;
    @BindView(R.id.img_empty_state)
    ImageView imgEmptyState;

    @Inject
    public void setPresenter(MainPresenter presenter) {
        this.presenter = presenter;
    }


    @Inject
    public void setExchangeAdapter(ExchangeAdapter exchangeAdapter) {
        this.exchangeAdapter = exchangeAdapter;
        gridView.setAdapter(exchangeAdapter);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Drawable logo = getResources().getDrawable(R.drawable.paytm_logo);
        getSupportActionBar().setBackgroundDrawable(logo);
        getSupportActionBar().setElevation(0);
        getSupportActionBar().setTitle("");

        ButterKnife.bind(this);

        NetComponent netComponent = DaggerNetComponent.builder()
                .appComponent(((App) getApplication()).getAppComponent())
                .netModule(new NetModule(API_URL))
                .build();

        DaggerMainScreenComponent.builder()
                .netComponent(netComponent)
                .mainViewModule(new MainViewModule(this))
                .build().inject(this);


        presenter.onCreate();

        value.addTextChangedListener(new CurrencyTextWatcher(value, this));
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

        // Functional requirement: User must be able to select a currency from a list of currencies available from Fixer
        // The currencies are received in this function, where we set up a Spinner with the list of currencies values,
        // so that the user can select a currency from the list.
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_dropdown_item, items);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerCurrency.setAdapter(adapter);
        spinnerCurrency.setOnItemSelectedListener(this);
    }

    @Override
    public void hideEmptyListState() {
        imgEmptyState.setVisibility(View.INVISIBLE);
    }

    @Override
    public void showEmptyListState() {
        imgEmptyState.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideGrid() {
        gridView.setVisibility(View.INVISIBLE);
    }

    @Override
    public void showGrid() {
        gridView.setVisibility(View.VISIBLE);
    }

    @Override
    public void cleanValueChanged(double cleanValue) {
        presenter.onAmountChange(cleanValue);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        presenter.onBaseCurrencyChange(parent.getItemAtPosition(position).toString());
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
