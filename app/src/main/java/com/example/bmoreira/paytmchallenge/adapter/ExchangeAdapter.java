package com.example.bmoreira.paytmchallenge.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.bmoreira.paytmchallenge.R;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by bruco on 2017-12-14.
 */

public class ExchangeAdapter extends BaseAdapter implements ExchangeAdapterData {
    public static final int BASE_TO_DECIMAL_CONVERSION = 100;
    private Context mContext;
    private Map<String, Float> exchanges;
    private double baseValue = 0;

    public ExchangeAdapter(Context c) {
        mContext = c;
    }

    public int getCount() {
        int ret = 0;
        if (exchanges != null) {
            ret = exchanges.size();
        }
        return ret;
    }

    public Object getItem(int position) {
        List<String> list = new ArrayList<>(exchanges.keySet());
        return list.get(position);
    }

    public long getItemId(int position) {
        return 0;
    }

    // create a new ImageView for each item referenced by the Adapter
    public View getView(int position, View convertView, ViewGroup parent) {
        LinearLayout viewLayout;

        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) mContext
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            viewLayout = (LinearLayout) inflater.inflate(
                    R.layout.grid_item, null);
        } else {
            viewLayout = (LinearLayout) convertView;
        }

        List<String> list = new ArrayList<>(exchanges.keySet());
        List<Float> listValue = new ArrayList<>(exchanges.values());
        TextView textCurrency = viewLayout.findViewById(R.id.tv_currency);
        TextView textRate = viewLayout.findViewById(R.id.tv_rate);
        TextView textValue = viewLayout.findViewById(R.id.tv_value);

        textCurrency.setText(list.get(position));
        DecimalFormat format = new DecimalFormat("#0.00");
        textRate.setText("" + format.format(listValue.get(position)));
        textValue.setText("" + format.format(listValue.get(position) * baseValue / BASE_TO_DECIMAL_CONVERSION));

        return viewLayout;
    }

    @Override
    public void setExchangeMap(Map<String, Float> exchangeMap) {
        exchanges = exchangeMap;
    }

    @Override
    public void setBaseValue(double value) {
        this.baseValue = value;
    }
}
