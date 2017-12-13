package com.example.bmoreira.paytmchallenge.data;

import java.util.Map;

/**
 * Created by bruco on 2017-12-12.
 */

public class Latest {

    /*Class that represents the json object for the latest currencies//
    {
            "base": "EUR",
            "date": "2017-12-11",
            "rates": {
                "AUD": 1.5674,
                "BGN": 1.9558,
                "BRL": 3.8654,
                "CAD": 1.5168,
                "USD": 1.1796,
                "ZAR": 16.062
    } */

    private String base;
    private String date;
    private Map<String, Float> rates;

    @Override
    public String toString() {
        return "Latest{"
                + "base='" + base + '\''
                + ", date='" + date + '\''
                + ", rates=" + rates
                + '}';
    }

    public String getBase() {
        return base;
    }

    public void setBase(String base) {
        this.base = base;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Map<String, Float> getRates() {
        return rates;
    }

    public void setRates(Map<String, Float> rates) {
        this.rates = rates;
    }
}
