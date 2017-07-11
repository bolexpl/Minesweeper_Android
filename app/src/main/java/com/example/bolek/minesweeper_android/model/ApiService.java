package com.example.bolek.minesweeper_android.model;

import com.example.bolek.minesweeper_android.pojo.JSONResponse;

import retrofit2.Call;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Field;
import retrofit2.http.Query;

public interface ApiService {

    @GET("records.php")
    Call<JSONResponse> getRecords(@Query("board") String board, @Query("page") int page, @Query("limit") int limit);

    @POST("login.php")
    @FormUrlEncoded
    Call<JSONResponse> login(@Field("login") String login, @Field("pass") String pass);

}
