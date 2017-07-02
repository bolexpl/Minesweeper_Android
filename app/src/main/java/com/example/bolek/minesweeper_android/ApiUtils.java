package com.example.bolek.minesweeper_android;

import com.example.bolek.minesweeper_android.model.RetrofitClient;

public class ApiUtils {

    public static String BASE_URL = "http://192.168.0.11/";

    public static ApiService getApiService() {
        return RetrofitClient.getClient().create(ApiService.class);
    }
}
