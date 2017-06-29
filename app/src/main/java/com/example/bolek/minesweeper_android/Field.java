package com.example.bolek.minesweeper_android;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.AppCompatButton;

public class Field extends AppCompatButton {

    private int state;
    public static final int ZAKRYTE = 0;
    public static final int ODKRYTE = 1;
    public static final int FLAGA = 3;

    private int value;
    public static final int MINA = -1;
    public static final int PUSTE = 0;

    public Field(Context context, int state, int value) {
        super(context, null, R.style.Field);
        this.state = state;
        this.value = value;
        init();
    }

    public void init(){
        setText(String.valueOf(value));
        if(state == ODKRYTE){
            setBackgroundColor(Color.LTGRAY);
        }else{
            setBackgroundColor(Color.DKGRAY);
        }
        setPadding(0,10,0,10);
    }

    public int getState() {
        return state;
    }

    public int getValue() {
        return value;
    }
}
