package com.example.bolek.minesweeper_android;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private int width = 16;
    private int height = 16;
    private int mines = 0;
    private Field[][] fields;
    private Button[][] bt;

    private HScroll hScroll;
    private VScroll vScroll;

    private float mx, my;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        fields = new Field[width][height];
        bt = new Button[width][height];

        vScroll = (VScroll) findViewById(R.id.vScroll);
        hScroll = (HScroll) findViewById(R.id.hScroll);

        TableLayout table = (TableLayout) findViewById(R.id.grid);

        int licznik = 1;
        for (int i = 0; i < height; i++) {

            TableRow row = new TableRow(this);
            for (int c = 0; c < width; c++) {
                View v = LayoutInflater.from(this).inflate(R.layout.grid_item, null, false);
                bt[i][c] = (Button) v.findViewById(R.id.field);
                bt[i][c].setOnClickListener(this);
                fields[i][c] = new Field(Field.ZAKRYTE, 0, i, c);
                bt[i][c].setTag(fields[i][c]);
                bt[i][c].setText(String.valueOf(licznik % 5));
                licznik++;
                row.addView(v);
            }
            table.addView(row);
        }
    }

    @Override
    public void onClick(View view) {
        Button bt = (Button) view;
        bt.setBackground(ResourcesCompat.getDrawable(getResources(), R.drawable.field_odkryte, null));
        Field f = (Field) bt.getTag();
        bt.setText(f.getX() + " " + f.getY());
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

    private boolean down = false;

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float curX, curY;

        switch (event.getAction()) {
//            case MotionEvent.ACTION_DOWN:
//                mx = event.getX();
//                my = event.getY();
//                break;
            case MotionEvent.ACTION_MOVE:
                if (!down) {
                    mx = event.getX();
                    my = event.getY();
                }
                curX = event.getX();
                curY = event.getY();
                vScroll.scrollBy(0, (int) (my - curY));
                hScroll.scrollBy((int) (mx - curX), 0);
                mx = curX;
                my = curY;
                down = true;
                break;
            case MotionEvent.ACTION_UP:
                curX = event.getX();
                curY = event.getY();
                vScroll.scrollBy(0, (int) (my - curY));
                hScroll.scrollBy((int) (mx - curX), 0);
                down = false;
                break;
        }
        return true;
    }
}
