package com.example.bolek.minesweeper_android;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import java.util.ArrayList;

import com.example.bolek.minesweeper_android.pojo.*;

public class RecordsActivity extends AppCompatActivity {

    private TableLayout table;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_records);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

        table = (TableLayout) findViewById(R.id.table);

        ArrayList<Record> data = getData();

        TableRow row = (TableRow) getLayoutInflater().inflate(R.layout.record_item, null, false);

        TextView tv = (TextView) row.findViewById(R.id.nr);
        tv.setBackground(getResources().getDrawable(R.drawable.border_head));
        tv.setTextColor(Color.WHITE);

        tv = (TextView) row.findViewById(R.id.czas);
        tv.setBackground(getResources().getDrawable(R.drawable.border_head));
        tv.setTextColor(Color.WHITE);

        tv = (TextView) row.findViewById(R.id.plansza);
        tv.setBackground(getResources().getDrawable(R.drawable.border_head));
        tv.setTextColor(Color.WHITE);

        tv = (TextView) row.findViewById(R.id.gracz);
        tv.setBackground(getResources().getDrawable(R.drawable.border_head));
        tv.setTextColor(Color.WHITE);
        table.addView(row);

        int nr = 1;

        for (Record r : data) {
            row = (TableRow) getLayoutInflater().inflate(R.layout.record_item, null, false);

            tv = (TextView) row.findViewById(R.id.nr);
            tv.setText(String.valueOf(nr++) + ".");

            tv = (TextView) row.findViewById(R.id.czas);
            tv.setText(String.valueOf(r.getCzas()));

            tv = (TextView) row.findViewById(R.id.plansza);
            tv.setText(r.getBoard());

            tv = (TextView) row.findViewById(R.id.gracz);
            tv.setText(r.getLogin());
            table.addView(row);
        }
    }

    private ArrayList<Record> getData() {
        ArrayList<Record> list = new ArrayList<>();

        list.add(new Record(1, 1, 8, "8x8", "aaa"));
        list.add(new Record(2, 1, 45, "16x16", "wer"));
        list.add(new Record(3, 1, 12, "16x16", "dfg"));
        list.add(new Record(4, 1, 25, "8x8", "asda"));
        list.add(new Record(5, 1, 21, "16x16", "aaa"));
        list.add(new Record(6, 1, 13, "8x8", "dgdf"));
        list.add(new Record(7, 1, 12, "16x16", "sdfs"));
        list.add(new Record(8, 1, 5, "8x8", "aaa"));
        list.add(new Record(9, 1, 12, "8x8", "cvncn"));
        list.add(new Record(10, 1, 8, "8x8", "xfds"));
        list.add(new Record(11, 1, 45, "8x8", "xv"));

        return list;
    }
}
