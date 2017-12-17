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
