package com.example.bolek.minesweeper_android.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;

import com.example.bolek.minesweeper_android.ApiService;
import com.example.bolek.minesweeper_android.ApiUtils;
import com.example.bolek.minesweeper_android.R;
import com.example.bolek.minesweeper_android.RecordAdapter;
import com.example.bolek.minesweeper_android.pojo.*;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RecordsActivity extends AppCompatActivity {

    private RecordAdapter adapter;
    private ApiService service;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_records);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recycler);

        adapter = new RecordAdapter(new ArrayList<Record>(0), this);
        service = ApiUtils.getApiService();

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setHasFixedSize(true);
        RecyclerView.ItemDecoration decoration = new DividerItemDecoration(this, DividerItemDecoration.HORIZONTAL);
        recyclerView.addItemDecoration(decoration);
        recyclerView.setAdapter(adapter);

        loadData();
    }

    private void loadData() {
        service.getRecords().enqueue(new Callback<JSONResponse>() {
            @Override
            public void onResponse(Call<JSONResponse> call, Response<JSONResponse> response) {
                if (response.isSuccessful()) {
                    adapter.update(response.body().getData());
                } else {
                    Toast.makeText(getApplicationContext(), getResources().getString(R.string.api_error,
                            response.code()), Toast.LENGTH_SHORT).show();
                    Log.d("retrofit", response.toString());
                }
            }

            @Override
            public void onFailure(Call<JSONResponse> c, Throwable t) {
                Toast.makeText(getApplicationContext(), getResources().getString(R.string.api_error2),
                        Toast.LENGTH_SHORT).show();
                Log.d("retrofit", t.toString());
                Log.d("retrofit", t.getLocalizedMessage());
                Log.d("retrofit", t.getMessage());
            }
        });
    }
}
