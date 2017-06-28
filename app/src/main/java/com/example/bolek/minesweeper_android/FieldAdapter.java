package com.example.bolek.minesweeper_android;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class FieldAdapter extends BaseAdapter {

    private Context mContext;
    private Field[] fields;

    public FieldAdapter(Context mContext, Field[] fields) {
        this.mContext = mContext;
        this.fields = fields;
    }

    @Override
    public int getCount() {
        return fields.length;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return fields[position];
//        TextView tv = new TextView(mContext);
//        tv.setText("aaaaaa");
//        return tv;
    }
}
