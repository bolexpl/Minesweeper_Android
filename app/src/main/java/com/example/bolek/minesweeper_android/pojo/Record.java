package com.example.bolek.minesweeper_android.pojo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Record {

    @SerializedName("id")
    @Expose
    private int id;
    @SerializedName("user_id")
    @Expose
    private int userId;
    @SerializedName("czas")
    @Expose
    private int czas;
    @SerializedName("board")
    @Expose
    private String board;
    @SerializedName("login")
    @Expose
    private String login;
    @SerializedName("avatar")
    @Expose
    private String avatar;

    public Record(int id, int userId, int czas, String board, String login, String avatar) {
        this.id = id;
        this.userId = userId;
        this.czas = czas;
        this.board = board;
        this.login = login;
        this.avatar = avatar;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getCzas() {
        return czas;
    }

    public void setCzas(int czas) {
        this.czas = czas;
    }

    public String getBoard() {
        return board;
    }

    public void setBoard(String board) {
        this.board = board;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }
}
