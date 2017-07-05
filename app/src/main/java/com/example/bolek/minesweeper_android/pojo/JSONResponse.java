package com.example.bolek.minesweeper_android.pojo;

import java.util.ArrayList;
import java.util.List;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class JSONResponse {

    @SerializedName("error")
    @Expose
    private String error;
    @SerializedName("empty")
    @Expose
    private String empty;
    @SerializedName("success")
    @Expose
    private boolean success;
    @SerializedName("user")
    @Expose
    private User user;
    @SerializedName("data")
    @Expose
    private List<Record> data = null;

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public String getEmpty() {
        return empty;
    }

    public void setEmpty(String empty) {
        this.empty = empty;
    }

    public boolean getSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<Record> getData() {
        if (data == null) {
            return new ArrayList<>(0);
        }
        return data;
    }

    public void setData(List<Record> data) {
        this.data = data;
    }

}
