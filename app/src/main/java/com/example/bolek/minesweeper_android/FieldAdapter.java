package com.example.bolek.minesweeper_android;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;

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

//    private class ViewHolder {
//        Button bt;
//    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

//        ViewHolder viewHolder;
//        if (convertView == null) {
//            convertView = LayoutInflater.from(mContext).inflate(R.layout.grid_item, parent, false);
//
//            viewHolder = new ViewHolder();
//            viewHolder.bt = (Button) convertView.findViewById(R.id.field);
//            convertView.setTag(viewHolder);
//        } else {
//            viewHolder = (ViewHolder) convertView.getTag();
//        }
//        viewHolder.bt.setText(String.valueOf(fields[position].getValue()));

        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.grid_item, parent, false);
        }
        if (fields[position].getBt() == null) {
            fields[position].setBt((Button) convertView.findViewById(R.id.field));
        }

        Button bt = fields[position].getBt();
//        bt.setBackground(mContext.getResources().getDrawable(R.drawable.field_odkryte));
//        bt.setText(String.valueOf(fields[position].getValue()));

        return convertView;
    }
}
