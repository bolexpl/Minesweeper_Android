package com.example.bolek.minesweeper_android;


import android.widget.Button;

public class Field {

    public static final int ZAKRYTE = 0;
    public static final int ODKRYTE = 1;
    public static final int FLAGA = 2;

    public static final int MINA = -1;
    public static final int PUSTE = 0;

    private int state;
    private int value;

    private Button bt;

    public Field(int state, int value, Button bt) {
        this.state = state;
        this.value = value;
        this.bt = bt;
    }

    public int getState() {
        return state;
    }

    public int getValue() {
        return value;
    }

    public void setBt(Button bt) {
        this.bt = bt;
    }

    public Button getBt() {
        return bt;
    }
}
