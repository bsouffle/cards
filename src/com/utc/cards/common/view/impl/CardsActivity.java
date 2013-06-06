package com.utc.cards.common.view.impl;

import static com.utc.cards.Constants.GMAIL;
import static com.utc.cards.Constants.JADE_CARDS_PREFS_FILE;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;

import com.utc.cards.R;
import com.utc.cards.player.jade.PlayerAgentManager;
import com.utc.cards.player.view.PlayerMenuActivity;
import com.utc.cards.table.view.TableSelectGameActivity;

public class CardsActivity extends Activity
{

    private static Logger log = LoggerFactory.getLogger(CardsActivity.class);

    public final static String EXTRA_MESSAGE = "com.utc.cards.EXTRA_MESSAGE";
    public static final int PLAYER_REQUEST = 0;
    public static final int HOST_REQUEST = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
	super.onCreate(savedInstanceState);
	setContentView(R.layout.activity_cards);
	setUserGmail();
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
		log.info("onActivityResult() : Stopping Jade...");
		PlayerAgentManager.instance().stopAgentContainer();
	    }
	} else if (requestCode == HOST_REQUEST)
	{
	    if (resultCode == RESULT_CANCELED)
	    {
		// The host activity was closed.
		// _log.info("onActivityResult() : Stopping Jade...");
		// PlayerAgentManager.instance().stopAgentContainer();
	    }
	}
    }

    private void setUserGmail()
    {
	AccountManager manager = (AccountManager) getSystemService(ACCOUNT_SERVICE);
	Account[] list = manager.getAccounts();
	for (Account account : list)
	{
	    if (account.type.equalsIgnoreCase("com.google"))
	    {
		SharedPreferences settings = getSharedPreferences(
			JADE_CARDS_PREFS_FILE, Context.MODE_PRIVATE);
		SharedPreferences.Editor editor = settings.edit();
		editor.putString(GMAIL, account.name);
		editor.commit();
		log.info("JADE_CARDS_PREFS_FILE :");
		log.info(GMAIL + " = " + account.name);
	    }
	}
    }
}
