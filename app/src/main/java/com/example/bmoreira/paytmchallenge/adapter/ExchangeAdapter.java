package com.example.bmoreira.paytmchallenge.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import android.widget.TwoLineListItem;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by bruco on 2017-12-14.
 */

public class ExchangeAdapter extends BaseAdapter implements ExchangeAdapterData {
    private Context mContext;
    private Map<String, Float> exchanges;

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
        return null;
    }

    public long getItemId(int position) {
        return 0;
    }

    // create a new ImageView for each item referenced by the Adapter
    public View getView(int position, View convertView, ViewGroup parent) {
        TwoLineListItem twoLineListItem;

        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) mContext
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            twoLineListItem = (TwoLineListItem) inflater.inflate(
                    android.R.layout.simple_list_item_2, null);
        } else {
            twoLineListItem = (TwoLineListItem) convertView;
        }

        List<String> list = new ArrayList<>(exchanges.keySet());
        List<Float> listValue = new ArrayList<>(exchanges.values());
        TextView text1 = twoLineListItem.getText1();
        TextView text2 = twoLineListItem.getText2();

        text1.setText(list.get(position));
        DecimalFormat format = new DecimalFormat("#0.00");
        text2.setText("" + format.format(listValue.get(position)));

        return twoLineListItem;
    }

    @Override
    public void setExchangeMap(Map<String, Float> exchangeMap) {
        exchanges = exchangeMap;
    }
}
