package com.utc.cards.common.view.impl;

import static com.utc.cards.Constants.GMAIL;
import static com.utc.cards.Constants.HOST_IP;
import static com.utc.cards.Constants.JADE_CARDS_PREFS_FILE;
import static com.utc.cards.Constants.LOCAL_IP;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.utc.cards.R;
import com.utc.cards.player.jade.AgentActivityListener;
import com.utc.cards.player.jade.PlayerAgentManager;
import com.utc.cards.player.view.PlayerMenuActivity;
import com.utc.cards.table.jade.agent.HostAgentManager;
import com.utc.cards.table.view.TableSelectGameActivity;
import com.utc.cards.utils.Utils;

public class CardsActivity extends Activity
{

    private abstract class AgentStartupListener implements
	    AgentActivityListener
    {

	@Override
	public abstract void onAllAgentsReady();

    }

    private static Logger log = LoggerFactory.getLogger(CardsActivity.class);

    public final static String EXTRA_MESSAGE = "com.utc.cards.EXTRA_MESSAGE";
    public static final int PLAYER_REQUEST = 0;
    public static final int HOST_REQUEST = 1;

    private DrawerLayout mDrawerLayout;
    private LinearLayout mDrawerPanel;
    private ActionBarDrawerToggle mDrawerToggle;

    protected Handler mHandler = new Handler();

    // private MyReceiver myReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
	super.onCreate(savedInstanceState);
	setContentView(R.layout.activity_cards);
	setUserGmail();
	// myReceiver = new MyReceiver();

	// IntentFilter refreshChatFilter = new IntentFilter();
	// refreshChatFilter.addAction("jade.demo.chat.REFRESH_CHAT");
	// registerReceiver(myReceiver, refreshChatFilter);
	setSlidingMenu(savedInstanceState);

    }

    private void setSlidingMenu(Bundle savedInstanceState)
    {
	mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
	mDrawerPanel = (LinearLayout) findViewById(R.id.left_drawer);

	// set a custom shadow that overlays the main content when the drawer
	// opens
	mDrawerLayout.setDrawerShadow(R.drawable.drawer_shadow,
		GravityCompat.START);

	// enable ActionBar app icon to behave as action to toggle nav drawer
	getActionBar().setDisplayHomeAsUpEnabled(true);
	getActionBar().setHomeButtonEnabled(true);

	// ActionBarDrawerToggle ties together the the proper interactions
	// between the sliding drawer and the action bar app icon
	mDrawerToggle = new ActionBarDrawerToggle(this, /* host Activity */
	mDrawerLayout, /* DrawerLayout object */
	R.drawable.ic_drawer, /* nav drawer image to replace 'Up' caret */
	R.string.drawer_open, /* "open drawer" description for accessibility */
	R.string.drawer_close /* "close drawer" description for accessibility */
	) {
	    public void onDrawerClosed(View view)
	    {
		getActionBar().setTitle("CARDS");
		invalidateOptionsMenu(); // creates call to
					 // onPrepareOptionsMenu()

		InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);

		EditText hostField = (EditText) findViewById(R.id.hostIpAddressEditText);
		imm.hideSoftInputFromWindow(hostField.getWindowToken(), 0);

		SharedPreferences settings = getSharedPreferences(
			JADE_CARDS_PREFS_FILE, Context.MODE_PRIVATE);
		SharedPreferences.Editor editor = settings.edit();
		editor.putString(HOST_IP, hostField.getText().toString());
		editor.commit();
	    }

	    public void onDrawerOpened(View drawerView)
	    {
		getActionBar().setTitle("Options");
		invalidateOptionsMenu(); // creates call to
					 // onPrepareOptionsMenu()
	    }
	};

	mDrawerLayout.setDrawerListener(mDrawerToggle);

	setIpAddress();

	setContentFrame();
    }

    private void setIpAddress()
    {
	SharedPreferences settings = getSharedPreferences(
		JADE_CARDS_PREFS_FILE, Context.MODE_PRIVATE);
	String ip = settings.getString(HOST_IP,
		settings.getString(LOCAL_IP, ""));
	final EditText hostIpAddressEditText = (EditText) findViewById(R.id.hostIpAddressEditText);
	hostIpAddressEditText.setText(ip);
    }

    private void setContentFrame()
    {
	// update the main content by replacing fragments
	Fragment fragment = new FragmentCardsActivity();
	FragmentManager fragmentManager = getFragmentManager();
	fragmentManager.beginTransaction()
		.replace(R.id.content_frame, fragment).commit();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
	// Inflate the menu; this adds items to the action bar if it is present.
	getMenuInflater().inflate(R.menu.cards, menu);
	return true;
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu)
    {
	// If the nav drawer is open, hide action items related to the content
	// view
	boolean drawerOpen = mDrawerLayout.isDrawerOpen(mDrawerPanel);
	menu.findItem(R.id.menu_settings).setVisible(!drawerOpen);
	return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {

	switch (item.getItemId()) {
	// case R.id.menu_settings:
	// Intent showSettings = new Intent(this, SettingsActivity.class);
	// startActivity(showSettings);
	// return true;
	case R.id.menu_exit:
	    finish();

	}

	// The action bar home/up action should open or close the drawer.
	// ActionBarDrawerToggle will take care of this.
	if (mDrawerToggle.onOptionsItemSelected(item))
	{
	    return true;
	}

	return false;

    }

    @Override
    protected void onDestroy()
    {
	super.onDestroy();
	// unregisterReceiver(myReceiver);
	log.debug("Destroy activity!");
    }

    public void playerMode(View view)
    {
	SharedPreferences settings = getSharedPreferences(
		JADE_CARDS_PREFS_FILE, Context.MODE_PRIVATE);
	String hostIp = settings.getString(HOST_IP, "");
	String localIp = settings.getString(LOCAL_IP, "");

	boolean ipsValids = Utils.validateIps(hostIp, localIp);

	if (ipsValids)
	{
	    log.info("connectPlayerMode() : local: " + localIp + ", host: "
		    + hostIp);
	    log.info("connectPlayerMode() : try starting jade platform");

	    // auto add some behaviour in agent.startup()
	    AgentActivityListener playerStartupListener = new AgentStartupListener() {

		@Override
		public void onAllAgentsReady()
		{
		    Intent intent = new Intent(CardsActivity.this,
			    PlayerMenuActivity.class);
		    startActivityForResult(intent, PLAYER_REQUEST);
		}
	    };
	    PlayerAgentManager.instance().startAgents(this,
		    settings.getString(GMAIL, ""), playerStartupListener);
	} else
	{
	    log.info("ips invalid");
	}
    }

    public void hostMode(View view)
    {
	SharedPreferences settings = getSharedPreferences(
		JADE_CARDS_PREFS_FILE, Context.MODE_PRIVATE);
	String hostIp = settings.getString(HOST_IP, "");
	String localIp = settings.getString(LOCAL_IP, "");

	boolean ipsValids = Utils.validateIps(hostIp, localIp);

	if (ipsValids)
	{
	    log.info("connectPlayerMode() : local: " + localIp + ", host: "
		    + hostIp);
	    log.info("connectPlayerMode() : try starting jade platform");

	    // auto add some behaviour in agent.startup()
	    AgentActivityListener hostStartupListener = new AgentStartupListener() {

		@Override
		public void onAllAgentsReady()
		{
		    Intent intent = new Intent(CardsActivity.this,
			    TableSelectGameActivity.class);
		    startActivityForResult(intent, HOST_REQUEST);
		}
	    };
	    HostAgentManager.instance().startAgents(this, null,
		    hostStartupListener);
	} else
	{
	    log.info("ips invalid");
	    Utils.showAlertDialog(CardsActivity.this, "Adresse IP invalide",
		    false);
	}
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {

	if (requestCode == PLAYER_REQUEST)
	{
	    if (resultCode == RESULT_CANCELED)
	    {
		// The player activity was closed.
		log.debug("onActivityResult() : Stopping Jade container...");
		PlayerAgentManager.instance().stopAgentContainer();
	    }
	} else if (requestCode == HOST_REQUEST)
	{
	    if (resultCode == RESULT_CANCELED)
	    {
		// The host activity was closed.
		log.debug("onActivityResult() : Stopping Jade Main container...");
		HostAgentManager.instance().stopAgentContainer();
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
