package com.example.bolek.minesweeper_android.activity;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;

import com.example.bolek.minesweeper_android.model.ApiService;
import com.example.bolek.minesweeper_android.model.ApiUtils;
import com.example.bolek.minesweeper_android.R;
import com.example.bolek.minesweeper_android.RecordAdapter;
import com.example.bolek.minesweeper_android.pojo.*;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RecordsActivity extends AppCompatActivity {

    private RecordAdapter adapter;
    private ApiService service;
    private SwipeRefreshLayout swipeRefreshLayout;
    private int filterId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_records);
        Spinner spinner = (Spinner) findViewById(R.id.spinner);

        ArrayAdapter<String> filterAdapter = new ArrayAdapter<String>(RecordsActivity.this,
                android.R.layout.simple_list_item_1,
                getResources().getStringArray(R.array.filter));
        filterAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinner.setAdapter(filterAdapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                Log.d("spinner", ApiUtils.FILTERS[i]);
                filterId = i;
                loadData();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });

        adapter = new RecordAdapter(new ArrayList<Record>(0), this);
        service = ApiUtils.getApiService();
        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipe_refresh);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                loadData();
            }
        });

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setHasFixedSize(true);
        RecyclerView.ItemDecoration decoration = new DividerItemDecoration(this, DividerItemDecoration.HORIZONTAL);
        recyclerView.addItemDecoration(decoration);
        recyclerView.setAdapter(adapter);

//        loadData();
    }

    private void loadData() {
        service.getRecords(ApiUtils.FILTERS[filterId]).enqueue(new Callback<JSONResponse>() {
            @Override
            public void onResponse(Call<JSONResponse> call, Response<JSONResponse> response) {
                if (response.isSuccessful()) {
                    adapter.update(response.body().getData());
                } else {
                    Toast.makeText(getApplicationContext(), getResources().getString(R.string.api_error,
                            response.code()), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<JSONResponse> c, Throwable t) {
                Toast.makeText(getApplicationContext(), getResources().getString(R.string.api_error2),
                        Toast.LENGTH_SHORT).show();
            }
        });
        swipeRefreshLayout.setRefreshing(false);
    }
}
