package com.example.bolek.minesweeper_android;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

public class PromptActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText width;
    private EditText height;
    private EditText mines;

    private RadioButton radio8x8;
    private RadioButton radio16x16;
    private RadioButton radio30x16;
    private RadioButton custom;

    private Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prompt);

        width = (EditText) findViewById(R.id.width);
        height = (EditText) findViewById(R.id.height);
        mines = (EditText) findViewById(R.id.mines);

        radio8x8 = (RadioButton) findViewById(R.id.radio_8x8);
        radio16x16 = (RadioButton) findViewById(R.id.radio_16x16);
        radio30x16 = (RadioButton) findViewById(R.id.radio_30x16);
        custom = (RadioButton) findViewById(R.id.radio_custom);

        button = (Button) findViewById(R.id.button_begin);

        width.setEnabled(false);
        height.setEnabled(false);
        mines.setEnabled(false);

        View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (v.getId() == R.id.radio_custom) {
                    enableCustom();
                } else {
                    disableCustom();
                }
            }
        };

        radio8x8.setOnClickListener(listener);
        radio16x16.setOnClickListener(listener);
        radio30x16.setOnClickListener(listener);
        custom.setOnClickListener(listener);

        button.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        finish();
    }

    @Override
    public void finish() {
        Intent i = new Intent();

        if (radio8x8.isChecked()) {
            i.putExtra("width", 8);
            i.putExtra("height", 8);
            i.putExtra("mines", 10);
        } else if (radio16x16.isChecked()) {
            i.putExtra("width", 16);
            i.putExtra("height", 16);
            i.putExtra("mines", 40);
        } else if (radio30x16.isChecked()) {
            i.putExtra("width", 30);
            i.putExtra("height", 16);
            i.putExtra("mines", 99);
        } else {
            if (width.getText().toString().equals("") ||
                    height.getText().toString().equals("") ||
                    mines.getText().toString().equals("")) {
                Toast.makeText(this, "Trzeba wypełnić wszystkie pola", Toast.LENGTH_SHORT).show();
                return;
            }

            int w = Integer.parseInt(width.getText().toString());
            int h = Integer.parseInt(height.getText().toString());
            int m = Integer.parseInt(mines.getText().toString());

            if (w <= 3 || h <= 3 || (m >= (w * h) - 9)) {
                Toast.makeText(this, "Złe dane", Toast.LENGTH_SHORT).show();
                return;
            }

            i.putExtra("width", w);
            i.putExtra("height", h);
            i.putExtra("mines", m);
        }
        setResult(1, i);
        super.finish();
    }

    private void enableCustom() {
        width.setEnabled(true);
        height.setEnabled(true);
        mines.setEnabled(true);
    }

    private void disableCustom() {
        width.setEnabled(false);
        height.setEnabled(false);
        mines.setEnabled(false);
    }
}
