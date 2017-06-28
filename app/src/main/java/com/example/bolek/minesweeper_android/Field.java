package com.example.bolek.minesweeper_android;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.AppCompatButton;
import android.util.AttributeSet;

public class Field extends AppCompatButton {

    private int state;
    public static final int ZAKRYTE = 0;
    public static final int ODKRYTE = 1;
    public static final int FLAGA = 3;

    private int value;
    public static final int MINA = -1;
    public static final int PUSTE = 0;

    public Field(Context context) {
        super(context);
        this.state = 0;
        this.value = 0;
        init();
    }

    public Field(Context context, int state, int value) {
        super(context);
        this.state = state;
        this.value = value;
        init();
    }

    public Field(Context context, AttributeSet attrs, int state, int value) {
        super(context, attrs);
        this.state = state;
        this.value = value;
        init();
    }

    public Field(Context context, AttributeSet attrs, int defStyleAttr, int state, int value) {
        super(context, attrs, defStyleAttr);
        this.state = state;
        this.value = value;
        init();
    }

    public Field(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.state = 0;
        this.value = 0;
        init();
    }

    public Field(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.state = 0;
        this.value = 0;
        init();
    }

    public void init(){
        setText(String.valueOf(value));
        if(state == ODKRYTE){
            setBackgroundColor(Color.LTGRAY);
        }else{
            setBackgroundColor(Color.DKGRAY);
        }
    }

    public int getState() {
        return state;
    }

    public int getValue() {
        return value;
    }
}
