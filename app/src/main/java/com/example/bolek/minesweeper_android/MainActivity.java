package com.example.bolek.minesweeper_android;

import android.content.Intent;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Random;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, View.OnLongClickListener {

    private int width = 0;
    private int height = 0;
    private int hardline = 0;
    private int minesFields;
    private int emptyFields;
    private boolean play = true;
    private int elapsedTime;

    private TimerThread timerThread;

    private Field[][] fields;
    private Button[][] bt;

    private HScroll hScroll;
    private VScroll vScroll;
    private TextView minesText;
    private TextView timerText;
    private ImageButton smile;

    private float mx, my;
    private boolean touchDown = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        vScroll = (VScroll) findViewById(R.id.vScroll);
        hScroll = (HScroll) findViewById(R.id.hScroll);
        minesText = (TextView) findViewById(R.id.mines_count);
        timerText = (TextView) findViewById(R.id.timer);
        smile = (ImageButton) findViewById(R.id.smile);
        smile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                newGame();
            }
        });

        if (width == 0 || height == 0 || hardline == 0) {
            Intent i = new Intent(this, PromptActivity.class);
            i.putExtra("setted", !(width == 0 || height == 0 || hardline == 0));
            startActivityForResult(i, 1);
        } else {
            newGame();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent i) {
        if (resultCode == 1 && requestCode == 1) {
            width = i.getIntExtra("width", 8);
            height = i.getIntExtra("height", 8);
            hardline = i.getIntExtra("mines", 8);
            newGame();
        }
    }

    @Override
    public void onClick(View view) {
        if (!play) {
            return;
        }
        Field f = (Field) view.getTag();

        if (f.getState() == Field.ODKRYTE && f.getValue() > Field.PUSTE) {
            discovery(f.getX(), f.getY(), true);
        } else if (f.getState() == Field.ZAKRYTE || f.getState() == Field.FLAGA) {
            flag(f.getX(), f.getY());
        }
        checkWin();
    }

    @Override
    public boolean onLongClick(View view) {
        if (!play) {
            return false;
        }
        Field f = (Field) view.getTag();
        if (f.getValue() == Field.NIEOKRESLONE && f.getState() == Field.ZAKRYTE) {
            generateMines(f.getX(), f.getY());
        }

        discovery(f.getX(), f.getY(), true);
        checkWin();
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            Intent i = new Intent(this, PromptActivity.class);
            i.putExtra("setted", !(width == 0 || height == 0 || hardline == 0));
            startActivityForResult(i, 1);
            return true;
        } else if (id == R.id.action_records) {
            Intent i = new Intent(this, RecordsActivity.class);
            startActivity(i);
            return true;
        } else if (id == R.id.debug) {
            Intent i = new Intent(this, DebugActivity.class);
            startActivity(i);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float curX, curY;

        switch (event.getAction()) {
            case MotionEvent.ACTION_MOVE:
                if (!touchDown) {
                    mx = event.getX();
                    my = event.getY();
                }
                curX = event.getX();
                curY = event.getY();
                vScroll.scrollBy(0, (int) (my - curY));
                hScroll.scrollBy((int) (mx - curX), 0);
                mx = curX;
                my = curY;
                touchDown = true;
                break;
            case MotionEvent.ACTION_UP:
                curX = event.getX();
                curY = event.getY();
                vScroll.scrollBy(0, (int) (my - curY));
                hScroll.scrollBy((int) (mx - curX), 0);
                touchDown = false;
                break;
        }
        return true;
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
                i--;
            }
        }
        generateNumbers();
        timerThread = new TimerThread();
        timerThread.execute();
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

                    fields[c][i].setValue(countMines);
                }
            }
        }
    }

    private void discovery(int x, int y, boolean recursive) {
        if (fields[y][x].getState() == Field.ZAKRYTE) {
            bt[y][x].setBackground(ContextCompat.getDrawable(this, R.drawable.field_odkryte));
            fields[y][x].setState(Field.ODKRYTE);

            if (fields[y][x].getValue() == Field.MINA) {

                onFail(x, y);
            } else if (fields[y][x].getValue() > Field.PUSTE) {

                bt[y][x].setText(String.valueOf(fields[y][x].getValue()));
                bt[y][x].setTextColor(Field.getColor(fields[y][x].getValue()));
                emptyFields--;
            } else {

                //rekurencja
                int xmin = (x == 0) ? 0 : x - 1;
                int xmax = (x == width - 1) ? width - 1 : x + 1;

                int ymin = (y == 0) ? 0 : y - 1;
                int ymax = (y == height - 1) ? height - 1 : y + 1;

                for (int i = ymin; i <= ymax; i++) {
                    for (int c = xmin; c <= xmax; c++) {
                        discovery(c, i, false);
                    }
                }
                emptyFields--;
            }
        } else if (fields[y][x].getState() == Field.ODKRYTE
                && fields[y][x].getValue() > Field.PUSTE && recursive) {

            discoveryNumber(x, y);
        }
    }

    private void discoveryNumber(int x, int y) {
        int xmin = (x == 0) ? 0 : x - 1;
        int xmax = (x == width - 1) ? width - 1 : x + 1;

        int ymin = (y == 0) ? 0 : y - 1;
        int ymax = (y == height - 1) ? height - 1 : y + 1;

        int countMines = 0, countFlags = 0;
        for (int i = ymin; i <= ymax; i++) {
            for (int c = xmin; c <= xmax; c++) {
                if (fields[i][c].getState() == Field.FLAGA) {
                    countFlags++;
                }
                if (fields[i][c].getValue() == Field.MINA) {
                    countMines++;
                }
            }
        }
        if (countFlags == countMines) {
            for (int i = ymin; i <= ymax; i++) {
                for (int c = xmin; c <= xmax; c++) {
                    if (fields[i][c].getState() == Field.ZAKRYTE) {
                        discovery(c, i, false);
                    }
                }
            }
        }
    }

    private void onFail(int x, int y) {
        bt[y][x].setBackground(ContextCompat.getDrawable(this, R.drawable.field_exploded));

        for (int i = 0; i < height; i++) {
            for (int c = 0; c < width; c++) {
                if (fields[i][c].getValue() == Field.MINA && i != y && c != x) {
                    bt[i][c].setBackground(ContextCompat.getDrawable(this, R.drawable.field_mine));
                }
            }
        }

        timerThread.cancel(true);
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setPositiveButton(getResources().getString(R.string.ok), null);
        builder.setMessage(getResources().getString(R.string.lose));

        builder.create().show();
        smile.setImageResource(R.drawable.smiley3);
        play = false;
    }

    private void checkWin() {
        if (emptyFields == 0) {
            timerThread.cancel(true);
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage(getResources().getString(R.string.win, elapsedTime));
            builder.setPositiveButton(getResources().getString(R.string.ok), null);
            builder.create().show();
            smile.setImageResource(R.drawable.smiley);
            play = false;
        }
    }

    private void flag(int x, int y) {

        if (fields[y][x].getState() == Field.FLAGA) {

            fields[y][x].setState(Field.ZAKRYTE);
            bt[y][x].setBackground(ContextCompat.getDrawable(this, R.drawable.field_zakryte));
            minesFields++;
        } else {

            fields[y][x].setState(Field.FLAGA);
            bt[y][x].setBackground(ContextCompat.getDrawable(this, R.drawable.field_flag));
            minesFields--;
        }
        minesText.setText(getResources().getString(R.string.left_mines, minesFields));
    }

    private void newGame() {
        minesFields = hardline;
        emptyFields = (width * height) - hardline;
        play = true;
        elapsedTime = 0;

        fields = new Field[height][width];
        bt = new Button[height][width];

        if(timerThread != null){
            timerThread.cancel(true);
        }

        minesText.setText(getResources().getString(R.string.left_mines, minesFields));
        timerText.setText(getResources().getString(R.string.time, 0));

        smile.setImageResource(R.drawable.smiley1);

        TableLayout table = (TableLayout) findViewById(R.id.grid);
        table.removeAllViews();
        generateBoard(table);
    }

    private void generateBoard(TableLayout table) {

        for (int i = 0; i < height; i++) {
            TableRow row = new TableRow(this);

            for (int c = 0; c < width; c++) {
                View v = LayoutInflater.from(this).inflate(R.layout.grid_item, null, false);
                bt[i][c] = (Button) v.findViewById(R.id.field);
                bt[i][c].setOnClickListener(this);
                bt[i][c].setOnLongClickListener(this);

                fields[i][c] = new Field(Field.ZAKRYTE, Field.NIEOKRESLONE, c, i);

                bt[i][c].setTag(fields[i][c]);
                row.addView(v);
            }
            table.addView(row);
        }

    }

    public void setElapsedTime() {
        elapsedTime++;
        timerText.setText(getResources().getString(R.string.time, elapsedTime));
    }

    private class TimerThread extends AsyncTask<Void, Void, Void> {
        @Override
        protected Void doInBackground(Void... voids) {
            try {
                while (true) {
                    Thread.sleep(1000);
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            setElapsedTime();
                        }
                    });

                    if (isCancelled()) break;
                }
            } catch (InterruptedException e) {
                e.printStackTrace();

            }
            return null;
        }
    }
}
