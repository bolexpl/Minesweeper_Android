package com.example.bolek.minesweeper_android.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.widget.TableLayout;

import java.util.ArrayList;

import com.example.bolek.minesweeper_android.R;
import com.example.bolek.minesweeper_android.RecordAdapter;
import com.example.bolek.minesweeper_android.pojo.*;

public class RecordsActivity extends AppCompatActivity {

    private TableLayout table;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_records);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(new RecordAdapter(getData(), this));
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
