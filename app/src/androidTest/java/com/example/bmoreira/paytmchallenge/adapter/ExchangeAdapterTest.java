package com.example.bmoreira.paytmchallenge.adapter;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.view.View;
import android.widget.TextView;

import com.example.bmoreira.paytmchallenge.R;

import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.*;

/**
 * Created by bruco on 2017-12-15.
 */
public class ExchangeAdapterTest {

    private ExchangeAdapter adapter;
    private double baseValue;

    @Before
    public void setUp() throws Exception {

        Context appContext = InstrumentationRegistry.getTargetContext();
        baseValue = 123;
        Map<String,Float> map = new HashMap<>();
        map.put("USD",0.35f);
        map.put("BRL",0.72f);


        adapter = new ExchangeAdapter(appContext);
        adapter.setBaseValue(baseValue);
        adapter.setExchangeMap(map);
    }

    @Test
    public void checkIfGetCountReturnsMapSize() throws Exception {
        assertEquals(2, adapter.getCount());
    }

    @Test
    public void checkIfGetItemReturnsMapStringKey() throws Exception {
        assertEquals("USD", adapter.getItem(0));
    }

    @Test
    public void checkIfGetItemIdReturnsZero() throws Exception {
        assertEquals(0, adapter.getItemId(0));
    }

    @Test
    public void checkIfGetViewReturnsViewsWithCorrectValue() throws Exception {
        View gridItem = adapter.getView(0,null, null);

        TextView currency = gridItem.findViewById(R.id.tv_currency);
        TextView rate = gridItem.findViewById(R.id.tv_rate);
        TextView value = gridItem.findViewById(R.id.tv_value);

        assertEquals("USD", currency.getText());
        assertEquals("0.35", rate.getText());
        assertEquals("0.43", value.getText()); // 0.35*baseValue with 2 decimal places
    }

}