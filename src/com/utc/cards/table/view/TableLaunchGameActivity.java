package com.utc.cards.table.view;

import jade.core.MicroRuntime;
import jade.wrapper.ControllerException;
import jade.wrapper.StaleProxyException;

import java.util.SortedSet;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.utc.cards.Constants;
import com.utc.cards.R;
import com.utc.cards.games.GameContainer;
import com.utc.cards.model.HostModel;
import com.utc.cards.model.game.GameStatus;
import com.utc.cards.player.jade.AgentActivityListener;
import com.utc.cards.table.jade.agent.HostAgentManager;
import com.utc.cards.table.jade.agent.hostAgent.IHostAgent;
import com.utc.cards.utils.Utils;

public class TableLaunchGameActivity extends Activity
{

    private static Logger log = LoggerFactory
	    .getLogger(TableLaunchGameActivity.class);
    private IHostAgent hostAgent;
    protected Handler mHandler = new Handler();
    private MyReceiver myReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
	super.onCreate(savedInstanceState);
	setContentView(R.layout.activity_launch_game);
	loadHostAgent();

	// chargement des jeux pour afficher la liste
	SortedSet<String> games = GameContainer.getCompleteGameNameList();

	registerReceivers();

	log.debug("Available games :");
	for (String gameName : games)
	{
	    log.debug(gameName);
	}
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
		String[] players = (String[]) HostModel.Instance()
			.getPlayersMap().keySet().toArray();
		ListView playerList = (ListView) findViewById(R.id.subscriberList);
		playerList.setAdapter(new ArrayAdapter<String>(
			TableLaunchGameActivity.this,
			android.R.layout.simple_list_item_1, players));
	    }
	}
    }

    private void loadHostAgent()
    {
	try
	{
	    hostAgent = MicroRuntime.getAgent(Constants.CARDS_HOST_AGENT_NAME)
		    .getO2AInterface(IHostAgent.class);
	    log.debug("hostAgent loaded !");
	} catch (StaleProxyException e)
	{
	    Utils.showAlertDialog(this, getString(R.string.msg_interface_exc),
		    true);
	} catch (ControllerException e)
	{
	    Utils.showAlertDialog(this, getString(R.string.msg_controller_exc),
		    true);
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
	if (HostModel.Instance().getGame().getLegalPlayerCounts()
		.contains(HostModel.Instance().getPlayersMap().size()))
	{
	    // arret des inscriptions (le subscription behaviour teste cette
	    // valeur)
	    HostModel.Instance().getGame().setStatus(GameStatus.IN_GAME);

	    // INITIALISATION DES AGENTS
	    // GameAgent et RulesAgent
	    // (les agents doivent charger leurs behaviours initiaux eux-même)
	    HostAgentManager.instance().startGameAndRulesAgent(this,
		    HostModel.Instance(), new AgentActivityListener() {

			@Override
			public void onAllAgentsReady()
			{
			    mHandler.post(new Runnable() {

				@Override
				public void run()
				{
				    log.info("onGameAndRuleAgentsReady");
				    // tous les agents pour le jeu sont prets
				    // on passe à la vue du jeu
				    ITableGameActivity activity = HostModel
					    .Instance().getGame()
					    .createTableGameActivity();

				    Intent intent = new Intent(
					    TableLaunchGameActivity.this,
					    activity.getClass());
				    // DEMARRAGE de l'activity de jeu
				    // (DameDePiqueTableGameActivity
				    // + classe abstraite)
				    startActivity(intent);
				}

			    });
			}
		    });
	} else
	{
	    Utils.showAlertDialog(this, "Pas assez de joueurs", false);
	}

    }

    public IHostAgent getHostAgent()
    {
	return hostAgent;
    }

}
