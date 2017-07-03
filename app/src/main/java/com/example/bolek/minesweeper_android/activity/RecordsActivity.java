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
import android.widget.ProgressBar;
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
    private LinearLayoutManager layoutManager;
    private ProgressBar progressBar;
    private int filterId;
    private int pastVisiblesItems, visibleItemCount, totalItemCount;
    private boolean loading = true;
    private int page = 0;
    private int limit = 20;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_records);
        Spinner spinner = (Spinner) findViewById(R.id.spinner);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);

        ArrayAdapter<String> filterAdapter = new ArrayAdapter<String>(RecordsActivity.this,
                android.R.layout.simple_list_item_1,
                getResources().getStringArray(R.array.filter));
        filterAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinner.setAdapter(filterAdapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
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
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setHasFixedSize(true);
        RecyclerView.ItemDecoration decoration = new DividerItemDecoration(this, DividerItemDecoration.HORIZONTAL);
        recyclerView.addItemDecoration(decoration);

        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                if (dy > 0) {
                    visibleItemCount = layoutManager.getChildCount();
                    totalItemCount = layoutManager.getItemCount();
                    pastVisiblesItems = layoutManager.findFirstVisibleItemPosition();

                    if (loading) {
                        if (visibleItemCount + pastVisiblesItems >= totalItemCount) {
                            loading = false;
                            updateData();
                        }
                    }
                }
            }
        });

        recyclerView.setAdapter(adapter);
    }

    public void setLoading() {
        loading = true;
    }

    private void loadData() {
        page = 0;
        service.getRecords(ApiUtils.FILTERS[filterId], 0, limit).enqueue(new Callback<JSONResponse>() {
            @Override
            public void onResponse(Call<JSONResponse> call, Response<JSONResponse> response) {
                if (response.isSuccessful()) {
                    adapter.setData(response.body().getData());
                } else {
                    Toast.makeText(getApplicationContext(), getResources().getString(R.string.api_error,
                            response.code()), Toast.LENGTH_SHORT).show();
                }
                setLoading();
            }

            @Override
            public void onFailure(Call<JSONResponse> c, Throwable t) {
                Toast.makeText(getApplicationContext(), getResources().getString(R.string.api_error2),
                        Toast.LENGTH_SHORT).show();
                setLoading();
            }
        });

        swipeRefreshLayout.setRefreshing(false);
    }

    private void updateData() {
        progressBar.setVisibility(ProgressBar.VISIBLE);
        service.getRecords(ApiUtils.FILTERS[filterId], page, limit).enqueue(new Callback<JSONResponse>() {
            @Override
            public void onResponse(Call<JSONResponse> call, Response<JSONResponse> response) {
                Log.d("retrofit", response.toString());
                if (response.isSuccessful()) {
                    if(response.body().getData().size() != 0){
                        adapter.updateData(response.body().getData());
                        page++;
                    }
                    setLoading();
                } else {
                    Toast.makeText(getApplicationContext(), getResources().getString(R.string.api_error,
                            response.code()), Toast.LENGTH_SHORT).show();
                }
                progressBar.setVisibility(ProgressBar.INVISIBLE);
            }

            @Override
            public void onFailure(Call<JSONResponse> c, Throwable t) {
                Toast.makeText(getApplicationContext(), getResources().getString(R.string.api_error2),
                        Toast.LENGTH_SHORT).show();
                progressBar.setVisibility(ProgressBar.INVISIBLE);
            }
        });
    }
}
