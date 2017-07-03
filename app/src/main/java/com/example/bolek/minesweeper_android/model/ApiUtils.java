package com.example.bolek.minesweeper_android.model;

public class ApiUtils {

    public static String BASE_URL = "http://192.168.0.11/";

    public static ApiService getApiService() {
        return RetrofitClient.getClient().create(ApiService.class);
    }

    public static String getAvatarUrl(String param) {
        return BASE_URL + "Minesweeper_PHP/avatars/" + param;
    }
}
