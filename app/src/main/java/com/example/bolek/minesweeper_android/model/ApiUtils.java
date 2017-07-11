package com.example.bolek.minesweeper_android.model;

public class ApiUtils {

    public static String BASE_URL = "http://192.168.0.11/Minesweeper_PHP/api";
    public static final String[] FILTERS = {"all", "8x8", "16x16", "30x16", "custom"};

    public static ApiService getApiService() {
        return RetrofitClient.getClient().create(ApiService.class);
    }

    public static String getAvatarUrl(String param) {
        return BASE_URL + "Minesweeper_PHP/avatars/" + param;
    }
}
