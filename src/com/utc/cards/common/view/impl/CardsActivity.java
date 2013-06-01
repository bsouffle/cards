package com.utc.cards.common.view.impl;

import com.utc.cards.R;
import com.utc.cards.R.menu;
import com.utc.cards.player.view.PlayerMenuActivity;
import com.utc.cards.table.view.TableSelectGameActivity;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;

public class CardsActivity extends Activity
{
    public final static String EXTRA_MESSAGE = "com.utc.cards.EXTRA_MESSAGE";

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
	super.onCreate(savedInstanceState);
	setContentView(R.layout.activity_cards);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
	// Inflate the menu; this adds items to the action bar if it is present.
	getMenuInflater().inflate(R.menu.cards, menu);
	return true;
    }

    public void playerMode(View view)
    {
	Intent intent = new Intent(this, PlayerMenuActivity.class);
	startActivity(intent);
    }

    public void hostMode(View view)
    {
	Intent intent = new Intent(this, TableSelectGameActivity.class);
	startActivity(intent);
    }
}
