package com.example.bolek.minesweeper_android.model;

import com.example.bolek.minesweeper_android.pojo.JSONResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiService {

    @GET("/Minesweeper_PHP/api/records.php")
    Call<JSONResponse> getRecords();

    @GET("/Minesweeper_PHP/api/records.php")
    Call<JSONResponse> getRecords(@Query("board") String board);

    @GET("/Minesweeper_PHP/api/records.php")
    Call<JSONResponse> getRecords(@Query("page") int page, @Query("limit") int limit);

    @GET("/Minesweeper_PHP/api/records.php")
    Call<JSONResponse> getRecords(@Query("board") String board, @Query("page") int page, @Query("limit") int limit);
}
