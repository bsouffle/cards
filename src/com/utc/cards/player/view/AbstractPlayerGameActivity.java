package com.utc.cards.player.view;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.Menu;
import android.view.WindowManager;

import com.utc.cards.Constants;
import com.utc.cards.model.game.InfoType;
import com.utc.cards.utils.Utils;

public abstract class AbstractPlayerGameActivity extends Activity implements
	IPlayerGameActivity
{
    protected MyReceiver myReceiver;

    private static Logger log = LoggerFactory
	    .getLogger(AbstractPlayerGameActivity.class);

    // private MyReceiver myReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
	super.onCreate(savedInstanceState);
	// empeche mise en veille
	getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

	// récupération des agents
	setContentView(getLayout());
	registerReceivers();
	onCreateHook();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
	// Inflate the menu; this adds items to the action bar if it is present.
	getMenuInflater().inflate(getMenu(), menu);
	return true;
    }

    protected abstract int getMenu();

    public abstract int getLayout();

    public abstract void onCreateHook();

    @Override
    protected void onDestroy()
    {
	super.onDestroy();
	unregisterReceiver(myReceiver);
	log.debug("Destroy activity!");
    }

    private class MyReceiver extends BroadcastReceiver
    {

	@Override
	public void onReceive(Context context, Intent intent)
	{
	    String action = intent.getAction();
	    log.info("Received intent " + action);
	    if (action.equalsIgnoreCase(Constants.POP_INFO))
	    {
		Utils.showAlertDialog(getThis(),
			intent.getStringExtra(InfoType.INFO.name()), false);
	    }
	}

    }

    public abstract Activity getThis();

    private void registerReceivers()
    {
	myReceiver = new MyReceiver();
	IntentFilter showChatFilter = new IntentFilter();
	showChatFilter.addAction(Constants.POP_INFO);
	registerReceiver(myReceiver, showChatFilter);
    }
}
