package com.mcbain.wyatt.blackjack;

import android.content.Context;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.app.Activity;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.HorizontalScrollView;
import android.widget.Button;

public class BlackJack extends ActionBarActivity implements View.OnClickListener {

    private TableLayout tableLayout;
    private TableRow tableRow0;
    private TextView titleTextView;
    private TableRow tableRow1;
    private TextView dealerTextView;
    private TableRow tableRow2;
    private HorizontalScrollView dealerCardsScrollView;
    private TableRow tableRow3;
    private TextView notificationTextView;
    private TableRow tableRow4;
    private HorizontalScrollView playerCardsScrollView;
    private TableRow tableRow5;
    private Gameplay gp;
    private View playersCardsView, dealersCardsView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_black_jack);

        tableLayout = (TableLayout) findViewById(R.id.tableLayout);
        tableRow0 = (TableRow) findViewById(R.id.tableRow0);
        titleTextView = (TextView) findViewById(R.id.titleTextView);
        tableRow1 = (TableRow) findViewById(R.id.tableRow1);
        dealerTextView = (TextView) findViewById(R.id.dealerTextView);
        tableRow2 = (TableRow) findViewById(R.id.tableRow2);
        dealerCardsScrollView = (HorizontalScrollView) findViewById(R.id.dealerCardsScrollView);
        tableRow3 = (TableRow) findViewById(R.id.tableRow3);
        notificationTextView = (TextView) findViewById(R.id.notificationTextView);
        tableRow4 = (TableRow) findViewById(R.id.tableRow4);
        playerCardsScrollView = (HorizontalScrollView) findViewById(R.id.playerCardsScrollView);
        tableRow5 = (TableRow) findViewById(R.id.tableRow5);
        findViewById(R.id.newGameButton).setOnClickListener(this);
        findViewById(R.id.hitButton).setOnClickListener(this);
        findViewById(R.id.stayButton).setOnClickListener(this);

        LayoutInflater inf = (LayoutInflater)getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        playersCardsView = inf.inflate(R.layout.card_layout, null);
        dealersCardsView = inf.inflate(R.layout.card_layout, null);

        gp = new Gameplay(this.getApplicationContext(), dealersCardsView, playersCardsView);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.newGameButton:
                gp.startGame();
                break;
            case R.id.hitButton:
                gp.hit();
                break;
            case R.id.stayButton:
                gp.stay();
                break;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_black_jack, menu);
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
        }

        return super.onOptionsItemSelected(item);
    }
}
