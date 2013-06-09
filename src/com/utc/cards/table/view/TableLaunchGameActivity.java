package com.utc.cards.table.view;

import jade.core.MicroRuntime;
import jade.wrapper.ControllerException;
import jade.wrapper.StaleProxyException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import android.app.Activity;
import android.app.FragmentManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;

import com.utc.cards.Constants;
import com.utc.cards.R;
import com.utc.cards.model.HostModel;
import com.utc.cards.model.game.GameStatus;
import com.utc.cards.player.jade.AgentActivityListener;
import com.utc.cards.table.jade.agent.HostAgentManager;
import com.utc.cards.table.jade.agent.hostAgent.IHostAgent;
import com.utc.cards.utils.Utils;

public class TableLaunchGameActivity extends Activity
{

    private static Logger log = LoggerFactory.getLogger(TableLaunchGameActivity.class);
    private IHostAgent hostAgent;
    protected Handler mHandler = new Handler();
    private MyReceiver myReceiver;

    private static Logger _log = LoggerFactory.getLogger(TableLaunchGameActivity.class);

    private DrawerLayout mDrawerLayout;
    private LinearLayout mDrawerPanel;
    private ActionBarDrawerToggle mDrawerToggle;
    private FragmentTableLaunchGameActivity mContentFrame;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launch_game);

        loadHostAgent();

        registerReceivers();

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
                getActionBar().setTitle("Attente des joueurs");
                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
            }

            public void onDrawerOpened(View drawerView)
            {
                getActionBar().setTitle("Options");
                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
            }
        };

        mDrawerLayout.setDrawerListener(mDrawerToggle);

        setContentFrame();
    }

    private void setContentFrame()
    {
        // update the main content by replacing fragments
        mContentFrame = new FragmentTableLaunchGameActivity();
        FragmentManager fragmentManager = getFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.content_frame, mContentFrame).commit();
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
        switch (item.getItemId())
        {
        case R.id.menu_exit:
            finish();
            break;
        case R.id.menu_settings:
            mDrawerLayout.openDrawer(mDrawerPanel);
            break;

        }

        // The action bar home/up action should open or close the drawer.
        // ActionBarDrawerToggle will take care of this.
        if (mDrawerToggle.onOptionsItemSelected(item))
        {
            return true;
        }

        return false;

    }

    private void registerReceivers()
    {
        myReceiver = new MyReceiver();
        IntentFilter playerListFilter = new IntentFilter();
        playerListFilter.addAction(Constants.PLAYER_LIST);
        registerReceiver(myReceiver, playerListFilter);
    }

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
            if (action.equalsIgnoreCase(Constants.PLAYER_LIST))
            { // met à jour la liste des joueurs
                mContentFrame.updatePlayerList();
            }
        }
    }

    private void loadHostAgent()
    {
        try
        {
            hostAgent = MicroRuntime.getAgent(Constants.CARDS_HOST_AGENT_NAME).getO2AInterface(IHostAgent.class);
            log.debug("hostAgent loaded !");
        }
        catch (StaleProxyException e)
        {
            Utils.showAlertDialog(this, getString(R.string.msg_interface_exc), true);
        }
        catch (ControllerException e)
        {
            Utils.showAlertDialog(this, getString(R.string.msg_controller_exc), true);
        }
    }

    /**
     * lancement du jeu via le bouton
     * 
     * @param view
     */
    public void startGame(View view)
    {
        // FIXME: GESTION DE la création des IAs si pas assez de joueurs ??
        // ici ou dans GameActivity ??

        // FIXME: pour l'instant pas d'IA
        if (HostModel.Instance().getGame().getLegalPlayerCounts().contains(HostModel.Instance().getPlayersMap().size()))
        {
            // arret des inscriptions (le subscription behaviour teste cette
            // valeur)
            HostModel.Instance().getGame().setStatus(GameStatus.IN_GAME);

            // INITIALISATION DES AGENTS
            // GameAgent et RulesAgent
            // (les agents doivent charger leurs behaviours initiaux eux-même)
            HostAgentManager.instance().startGameAndRulesAgent(this, HostModel.Instance(), new AgentActivityListener()
            {

                @Override
                public void onAllAgentsReady()
                {
                    mHandler.post(new Runnable()
                    {

                        @Override
                        public void run()
                        {
                            log.info("onGameAndRuleAgentsReady");
                            // tous les agents pour le jeu sont prets
                            // on passe à la vue du jeu
                            ITableGameActivity activity = HostModel.Instance().getGame().createTableGameActivity();

                            Intent intent = new Intent(TableLaunchGameActivity.this, activity.getClass());
                            // DEMARRAGE de l'activity de jeu
                            // (DameDePiqueTableGameActivity
                            // + classe abstraite)
                            startActivity(intent);
                        }

                    });
                }
            });
        }
        else
        {
            Utils.showAlertDialog(this, "Pas assez de joueurs", false);
        }

    }

    public IHostAgent getHostAgent()
    {
        return hostAgent;
    }

    // Méthode "OnClick" liée à la vue
    public void optionsClick(View view)
    {
        _log.debug("Options");

    }
}
