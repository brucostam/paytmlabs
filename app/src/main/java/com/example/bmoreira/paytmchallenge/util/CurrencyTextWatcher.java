package com.example.bmoreira.paytmchallenge.util;

import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

import java.text.NumberFormat;

/**
 * Created by bruco on 2017-12-17.
 */

public class CurrencyTextWatcher implements TextWatcher {

    public static final int BASE_TO_DECIMAL_CONVERSION = 100;
    private EditText mCurrencyEdittext;
    private CleanStringChangeListener cleanStringChangeListener;

    public CurrencyTextWatcher(EditText editText, CleanStringChangeListener listener) {
        mCurrencyEdittext = editText;
        cleanStringChangeListener = listener;
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    private String current = "";
    private double lastCleanAmount = 0;

    // This onTextChanged function will handle the conversion of a not well-formatted string to a currency
    // formatted string on every character typed.
    // It also notifies a listener with the current clean decimal value typed.
    // Step 1 - replace non numerical characters to clear the updated string
    // Step 2 - convert the string to a double value
    // Step 3 - format the value as a currency string
    // Step 4 - set the new formatted string to the view
    // Step 5 - call the listener with the cleaned double value
    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        if (!s.toString().equals(current)) {
            mCurrencyEdittext.removeTextChangedListener(this);

            String cleanString = s.toString().replaceAll("[$,.]", "");

            lastCleanAmount = Double.parseDouble(cleanString);
            current = NumberFormat.getCurrencyInstance().format((lastCleanAmount / BASE_TO_DECIMAL_CONVERSION));

            mCurrencyEdittext.setText(current);
            mCurrencyEdittext.setSelection(current.length());

            mCurrencyEdittext.addTextChangedListener(this);
        }
        cleanStringChangeListener.cleanValueChanged(lastCleanAmount);
    }

    @Override
    public void afterTextChanged(Editable s) {

    }
}
