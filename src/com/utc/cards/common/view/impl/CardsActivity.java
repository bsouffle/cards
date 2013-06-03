package com.utc.cards.common.view.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.utc.cards.R;
import com.utc.cards.R.menu;
import com.utc.cards.player.jade.PlayerAgentManager;
import com.utc.cards.player.view.PlayerMenuActivity;
import com.utc.cards.table.view.TableSelectGameActivity;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;

public class CardsActivity extends Activity
{

    private static Logger _log = LoggerFactory.getLogger(CardsActivity.class);

    public final static String EXTRA_MESSAGE = "com.utc.cards.EXTRA_MESSAGE";
    public static final int PLAYER_REQUEST = 0;
    public static final int HOST_REQUEST = 1;

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
	
	startActivityForResult(intent, PLAYER_REQUEST);
    }

    public void hostMode(View view)
    {
	Intent intent = new Intent(this, TableSelectGameActivity.class);
	startActivityForResult(intent, HOST_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
	
	if (requestCode == PLAYER_REQUEST)
	{
	    if (resultCode == RESULT_CANCELED)
	    {
		// The player activity was closed.
		_log.info("onActivityResult() : Stopping Jade...");
		PlayerAgentManager.instance().stopAgentContainer();
	    }
	} 
	else if (requestCode == HOST_REQUEST)
	{
	    if (resultCode == RESULT_CANCELED)
	    {
		// The host activity was closed.
		// _log.info("onActivityResult() : Stopping Jade...");
		// PlayerAgentManager.instance().stopAgentContainer();
	    }
	}
    }
}
