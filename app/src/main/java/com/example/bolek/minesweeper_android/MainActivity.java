package com.example.bolek.minesweeper_android;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;

import java.util.Random;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private int width = 16;
    private int height = 30;
    private int hardline = 30;
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

        fields = new Field[height][width];
        bt = new Button[height][width];

        vScroll = (VScroll) findViewById(R.id.vScroll);
        hScroll = (HScroll) findViewById(R.id.hScroll);

        TableLayout table = (TableLayout) findViewById(R.id.grid);
        generateBoard(table);

//        generateMines(0, 0);
    }

    private void generateBoard(TableLayout table) {

        for (int i = 0; i < height; i++) {
            TableRow row = new TableRow(this);

            for (int c = 0; c < width; c++) {
                View v = LayoutInflater.from(this).inflate(R.layout.grid_item, null, false);
                bt[i][c] = (Button) v.findViewById(R.id.field);
                bt[i][c].setOnClickListener(this);

                fields[i][c] = new Field(Field.ZAKRYTE, Field.NIEOKRESLONE, c, i);

                bt[i][c].setTag(fields[i][c]);
                row.addView(v);
            }
            table.addView(row);
        }
    }

    @Override
    public void onClick(View view) {
        Field f = (Field) view.getTag();

        if (f.getValue() == Field.NIEOKRESLONE) {
            generateMines(f.getX(), f.getY());
        }
//        Button button = (Button) view;
//        button.setBackground(ResourcesCompat.getDrawable(getResources(), R.drawable.field_odkryte, null));
//        Field f = (Field) button.getTag();
//        button.setText(f.getX() + " " + f.getY());
    }

    private void generateMines(int xx, int yy) {
        int x;
        int y;
        Random rand = new Random();
        int i = hardline;
        while (i > 0) {
            x = rand.nextInt(width);
            y = rand.nextInt(height);
            if (fields[y][x].getValue() != Field.MINA
                    && !((x == xx && y == yy)
                    || (x == xx - 1 && y == yy - 1)
                    || (x == xx + 1 && y == yy + 1)
                    || (x == xx - 1 && y == yy)
                    || (x == xx + 1 && y == yy)
                    || (x == xx && y == yy - 1)
                    || (x == xx && y == yy + 1)
                    || (x == xx + 1 && y == yy - 1)
                    || (x == xx - 1 && y == yy + 1))
                    ) {
                fields[y][x].setValue(Field.MINA);
                bt[y][x].setBackground(ContextCompat.getDrawable(this, R.drawable.field_mine));
                i--;
            }
        }
        generateNumbers();
    }

    private void generateNumbers() {
        int xmin, xmax, ymin, ymax;
        int countMines;
        for (int i = 0; i < width; i++) {
            for (int c = 0; c < height; c++) {

                if (fields[c][i].getValue() != Field.MINA) {
                    countMines = 0;
                    xmin = (i == 0) ? 0 : i - 1;
                    xmax = (i == width - 1) ? width - 1 : i + 1;

                    ymin = (c == 0) ? 0 : c - 1;
                    ymax = (c == height - 1) ? height - 1 : c + 1;

                    for (int j = xmin; j <= xmax; j++) {
                        for (int z = ymin; z <= ymax; z++) {

                            if (fields[z][j].getValue() == Field.MINA) {
                                countMines++;
                            }
                        }
                    }

                    bt[c][i].setBackground(ContextCompat.getDrawable(this, R.drawable.field_odkryte));
                    fields[c][i].setValue(countMines);
                    if (countMines != 0) {
                        bt[c][i].setText(String.valueOf(countMines));
                        bt[c][i].setTextColor(Field.getColor(countMines));
                    }
                }
            }
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

    private boolean down = false;

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float curX, curY;

        switch (event.getAction()) {
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
