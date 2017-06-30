package com.example.bolek.minesweeper_android;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;

public class DebugActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_debug);

        Button bt[] = new Button[8];

        bt[0] = (Button) findViewById(R.id.b1);
        bt[1] = (Button) findViewById(R.id.b2);
        bt[2] = (Button) findViewById(R.id.b3);
        bt[3] = (Button) findViewById(R.id.b4);
        bt[4] = (Button) findViewById(R.id.b5);
        bt[5] = (Button) findViewById(R.id.b6);
        bt[6] = (Button) findViewById(R.id.b7);
        bt[7] = (Button) findViewById(R.id.b8);

        for(int i=0; i<bt.length;i++){
            bt[i].setTextColor(Field.colors[i]);
        }
    }
}
