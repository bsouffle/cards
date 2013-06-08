package com.utc.cards.common.view.impl;

import static com.utc.cards.Constants.JADE_CARDS_PREFS_FILE;
import static com.utc.cards.Constants.LOCAL_IP;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
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
import com.utc.cards.player.jade.PlayerAgentManager;
import com.utc.cards.player.view.PlayerMenuActivity;
import com.utc.cards.table.view.TableSelectGameActivity;

public class CardsActivity extends Activity
{

    private static Logger log = LoggerFactory.getLogger(CardsActivity.class);

    public final static String EXTRA_MESSAGE = "com.utc.cards.EXTRA_MESSAGE";
    public static final int PLAYER_REQUEST = 0;
    public static final int HOST_REQUEST = 1;

    private DrawerLayout mDrawerLayout;
    private LinearLayout mDrawerPanel;
    private ActionBarDrawerToggle mDrawerToggle;

    // private MyReceiver myReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cards);

        setSlidingMenu(savedInstanceState);
    }

    private void setSlidingMenu(Bundle savedInstanceState)
    {
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerPanel = (LinearLayout) findViewById(R.id.left_drawer);

        // set a custom shadow that overlays the main content when the drawer opens
        mDrawerLayout.setDrawerShadow(R.drawable.drawer_shadow, GravityCompat.START);

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
        )
        {
            public void onDrawerClosed(View view)
            {
                getActionBar().setTitle("CARDS");
                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()

                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);

                EditText myEditText = (EditText) findViewById(R.id.hostIpAddressEditText);
                imm.hideSoftInputFromWindow(myEditText.getWindowToken(), 0);
            }

            public void onDrawerOpened(View drawerView)
            {
                getActionBar().setTitle("Options");
                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
            }
        };

        mDrawerLayout.setDrawerListener(mDrawerToggle);

        setIpAddress();

        setContentFrame();
    }

    private void setIpAddress()
    {
        SharedPreferences settings = getSharedPreferences(JADE_CARDS_PREFS_FILE, Context.MODE_PRIVATE);
        String localHost = settings.getString(LOCAL_IP, "");
        // Ce n'est pas la bonne adresse, mais comme il faut ��tre sur le m��me
        // r��seau on peut afficher la m��me adresse ip pour la corriger plus
        // rapidement
        String localIpAddress = localHost;

        final EditText hostIpAddressEditText = (EditText) findViewById(R.id.hostIpAddressEditText);
        hostIpAddressEditText.setText(localIpAddress);
    }

    private void setContentFrame()
    {
        // update the main content by replacing fragments
        Fragment fragment = new FragmentCardsActivity();
        FragmentManager fragmentManager = getFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.content_frame, fragment).commit();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu)
    {
        // If the nav drawer is open, hide action items related to the content view
        boolean drawerOpen = mDrawerLayout.isDrawerOpen(mDrawerPanel);
        menu.findItem(R.id.action_settings).setVisible(!drawerOpen);
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        // The action bar home/up action should open or close the drawer.
        // ActionBarDrawerToggle will take care of this.
        if (mDrawerToggle.onOptionsItemSelected(item))
        {
            return true;
        }

        return false;
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

    // private class MyReceiver extends BroadcastReceiver
    // {
    //
    // @Override
    // public void onReceive(Context context, Intent intent)
    // {
    // String action = intent.getAction();
    // log.debug("Received intent {}", action);
    // if (action.equalsIgnoreCase(Constants.SHOW_GAME))
    // {
    // Intent intent = new Intent(CardsActivity.this,
    // TableSelectGameActivity.class);
    // startActivityForResult(intent, HOST_REQUEST);
    //
    // }
    //
    // }
    // }

}
