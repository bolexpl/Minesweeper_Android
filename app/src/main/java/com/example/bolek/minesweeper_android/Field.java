package com.example.bolek.minesweeper_android;

import android.graphics.Color;

public class Field {

    public static final int ZAKRYTE = 0;
    public static final int ODKRYTE = 1;
    public static final int FLAGA = 2;

    public static final int MINA = -1;
    public static final int PUSTE = 0;

    public static final int COLORS[] = {
            Color.BLUE,
            Color.GREEN,
            Color.RED,
            Color.YELLOW,
            Color.CYAN,
            Color.MAGENTA,
            Color.rgb(0xff, 0x7d, 0x0),
            Color.DKGRAY
    };

    private int state;
    private int value;
    private int x;
    private int y;

    public Field(int state, int value, int x, int y) {
        this.state = state;
        this.value = value;
        this.x = x;
        this.y = y;
    }

    public int getState() {
        return state;
    }

    public int getValue() {
        return value;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void setState(int state) {
        this.state = state;
    }

    public void setValue(int value) {
        this.value = value;
    }
}
