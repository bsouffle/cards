package com.utc.cards.player.view;

import static com.utc.cards.Constants.GMAIL;
import static com.utc.cards.Constants.JADE_CARDS_PREFS_FILE;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.utc.cards.Constants;
import com.utc.cards.R;
import com.utc.cards.model.PlayerModel;
import com.utc.cards.model.game.InfoType;
import com.utc.cards.utils.Utils;
//import com.utc.cards.player.jade.PlayerAgentManager;
//import com.utc.cards.player.jade.agent.playerAgent.IPlayerAgent;
//import com.utc.cards.player.jade.agent.playerHelperAgent.IPlayerHelperAgent;

public class PlayerMenuActivity extends Activity
{
    private static Logger log = LoggerFactory
	    .getLogger(PlayerMenuActivity.class);

    // private Handler mHandler = new Handler();
    // private IPlayerAgent playerAgent;
    // private IPlayerHelperAgent playerHelperAgent;

    private MyReceiver myReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
	super.onCreate(savedInstanceState);
	getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
	setContentView(R.layout.activity_player_menu);

	registerReceivers();

	SharedPreferences settings = getSharedPreferences(
		JADE_CARDS_PREFS_FILE, Context.MODE_PRIVATE);
	String gmailAddress = settings.getString(GMAIL, "");

	TextView googleAccountTextView = (TextView) findViewById(R.id.googleAccountTextView);
	googleAccountTextView.setText("Google Account : " + gmailAddress);
    }

    private void registerReceivers()
    {
	myReceiver = new MyReceiver();
	IntentFilter showChatFilter = new IntentFilter();
	showChatFilter.addAction(Constants.SHOW_GAME);
	registerReceiver(myReceiver, showChatFilter);

	IntentFilter popInfoFilter = new IntentFilter();
	popInfoFilter.addAction(Constants.POP_INFO);
	registerReceiver(myReceiver, popInfoFilter);

	IntentFilter playerListFilter = new IntentFilter();
	playerListFilter.addAction(Constants.PLAYER_LIST);
	registerReceiver(myReceiver, playerListFilter);

	IntentFilter gameNameFilter = new IntentFilter();
	gameNameFilter.addAction(Constants.GAME_NAME);
	registerReceiver(myReceiver, gameNameFilter);
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

	    if (action.equalsIgnoreCase(Constants.SHOW_GAME))
	    { // lance l'activity de jeu
		IPlayerGameActivity activity = PlayerModel.Instance().getGame()
			.createPlayerGameActivity();
		Intent showChat = new Intent(PlayerMenuActivity.this,
			activity.getClass());
		PlayerMenuActivity.this.startActivity(showChat);

	    } else if (action.equalsIgnoreCase(Constants.POP_INFO))
	    { // afficher une info
		Utils.showAlertDialog(PlayerMenuActivity.this,
			intent.getStringExtra(InfoType.INFO.name()), false);

	    } else if (action.equalsIgnoreCase(Constants.PLAYER_LIST))
	    { // met à jour la liste des joueurs
		String[] players = intent
			.getStringArrayExtra(Constants.PLAYER_LIST);
		ListView playerList = (ListView) findViewById(R.id.playerList);
		playerList.setAdapter(new ArrayAdapter<String>(
			PlayerMenuActivity.this,
			android.R.layout.simple_list_item_1, players));

	    } else if (action.equalsIgnoreCase(Constants.GAME_NAME))
	    { // met à jour le nom du jeu
		TextView gameName = (TextView) findViewById(R.id.gameNamePlayerMenu);
		gameName.setText(intent.getStringExtra(Constants.GAME_NAME));
	    }

	}
    }

    // private void loadPlayerAgent()
    // {
    // SharedPreferences settings = getSharedPreferences(
    // JADE_CARDS_PREFS_FILE, Context.MODE_PRIVATE);
    // playerAgent = PlayerAgentManager.instance().getAgent(
    // this,
    // Constants.CARDS_PLAYER_AGENT_NAME + "-"
    // + settings.getString(GMAIL, ""), IPlayerAgent.class);
    // log.debug("playerAgent loaded !");
    // }
    //
    // private void loadPlayerHelperAgent()
    // {
    // SharedPreferences settings = getSharedPreferences(
    // JADE_CARDS_PREFS_FILE, Context.MODE_PRIVATE);
    // playerHelperAgent = PlayerAgentManager.instance().getAgent(
    // this,
    // Constants.CARDS_PLAYER_HELPER_AGENT_NAME + "-"
    // + settings.getString(GMAIL, ""),
    // IPlayerHelperAgent.class);
    // log.debug("playerAgent loaded !");
    // }
}
