package com.example.bolek.minesweeper_android;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.GridView;
import android.widget.TableLayout;
import android.widget.TableRow;

public class MainActivity extends AppCompatActivity {

    private int width = 7;
    private int height = 8;
    private int mines = 0;
    private Field[] fields;
    private Button[][] bt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        fields = new Field[width * height];
//        for (int i = 0; i < fields.length; i++) {
//            fields[i] = new Field(Field.ODKRYTE, i, null);
//        }

//        FieldAdapter fa = new FieldAdapter(this, fields);
//        GridView grid = (GridView) findViewById(R.id.grid);
//        grid.setNumColumns(width);
//        grid.setAdapter(fa);


        TableLayout table = (TableLayout) findViewById(R.id.grid);

        bt = new Button[height][width];
        for (int i = 0; i < height; i++) {

            TableRow row = new TableRow(this);
            for (int c = 0; c < width; c++) {
                View v = LayoutInflater.from(this).inflate(R.layout.grid_item, null, false);
                bt[i][c] = (Button) v.findViewById(R.id.field);
                row.addView(bt[i][c]);
            }
            table.addView(row);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        } else if (id == R.id.debug) {
            Intent i = new Intent(this, DebugActivity.class);
            startActivity(i);
        }

        return super.onOptionsItemSelected(item);
    }
}
