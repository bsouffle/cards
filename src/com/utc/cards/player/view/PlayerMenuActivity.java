package com.utc.cards.player.view;

import static com.utc.cards.Constants.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.utc.cards.R;

import com.utc.cards.player.jade.AgentActivityListener;
import com.utc.cards.player.jade.PlayerAgentManager;
import com.utc.cards.utils.Utils;

public class PlayerMenuActivity extends Activity
{

    private static Logger _log = LoggerFactory
	    .getLogger(PlayerMenuActivity.class);

    private Handler mHandler = new Handler();

    // private MyReceiver myReceiver;
    //
    // private CardsContainer _cardsContainer;
    //
    // private PlayerAgentManager agentManager = PlayerAgentManager.instance();
    // private Point _screenDimention = new Point();

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
	super.onCreate(savedInstanceState);

	setContentView(R.layout.activity_player_menu);
	// if (AndroidHelper.isEmulator())
	// {
	// // Emulator: this is needed to work with emulated devices
	// editText.setText(AndroidHelper.LOOPBACK);
	// } else
	// {

	SharedPreferences settings = getJadeSettings();
	String localHost = settings.getString(LOCAL_IP, "");
	// Ce n'est pas la bonne adresse, mais comme il faut être sur le même
	// réseau on peut afficher la même adresse ip pour la corriger plus
	// rapidement
	String localIpAddress = localHost;

	final EditText hostIpAddressEditText = (EditText) findViewById(R.id.hostIpAddressEditText);
	hostIpAddressEditText.setText(localIpAddress);

	// }
	//
	// myReceiver = new MyReceiver();
	// IntentFilter showChatFilter = new IntentFilter();
	// showChatFilter.addAction("jade.demo.chat.SHOW_CHAT");
	// registerReceiver(myReceiver, showChatFilter);
	AccountManager manager = (AccountManager) getSystemService(ACCOUNT_SERVICE);
	Account[] list = manager.getAccounts();

	for (Account account : list)
	{
	    if (account.type.equalsIgnoreCase("com.google"))
	    {
		String gmailAddress = account.name;
		final TextView googleAccountTextView = (TextView) findViewById(R.id.googleAccountTextView);
		googleAccountTextView.setText("Google Account : "
			+ gmailAddress);
		break;
	    }
	}

    }

    public void connect(View view)
    {
	final EditText ipAddressEditText = (EditText) findViewById(R.id.hostIpAddressEditText);
	String hostIp = ipAddressEditText.getText().toString();

	SharedPreferences settings = getJadeSettings();
	String localIp = settings.getString(LOCAL_IP, "");

	boolean ipsValids = Utils.validateIps(hostIp, localIp);

	if (ipsValids)
	{
	    _log.info("PlayerMenuActivity.connect : ipsValids (local: "
		    + localIp + ", host: " + hostIp + ")");
	    _log.info("PlayerMenuActivity.connect : try starting jade platform");

	    _log.info("JADE_CARDS_PREFS_FILE :");
	    _log.info(HOST_IP + " = " + hostIp);
	    SharedPreferences.Editor editor = settings.edit();
	    editor.putString(HOST_IP, hostIp);
	    editor.commit();

	    PlayerAgentManager.instance().startAgents(this,
		    new AgentActivityListener() {

			@Override
			public void onAllAgentsReady()
			{
			    // mHandler to update view from another thread
			    mHandler.post(new Runnable() {
				@Override
				public void run()
				{
				    _log.info("onAllAgentsReady");
				    final Button button = (Button) PlayerMenuActivity.this
					    .findViewById(R.id.joinButton);
				    button.setEnabled(true);
				    // TODO
				    // get available game and display it
				    // later : enable join button
				}
			    });

			}
		    });
	} else
	{
	    _log.info("ips invalid");
	}

    }

    private SharedPreferences getJadeSettings()
    {
	return getSharedPreferences(JADE_CARDS_PREFS_FILE, Context.MODE_PRIVATE);
    }

    public void join(View view)
    {

	// may be to be done in an other method or other class
	// IPlayer player = new HumanPlayer("Default");
	// controller = new PlayerController(player);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
	// Inflate the menu; this adds items to the action bar if it is present.
	getMenuInflater().inflate(R.menu.player_menu, menu);
	return true;
    }

    //
    // private void getScreenSize() {
    // Display display = getWindowManager().getDefaultDisplay();
    // display.getSize(_screenDimention);
    // }
    //

    //
    // //
    // // private class MyReceiver extends BroadcastReceiver {
    // //
    // // @Override
    // // public void onReceive(Context context, Intent intent) {
    // // String action = intent.getAction();
    // // log.info("Received intent " + action);
    // // if (action.equalsIgnoreCase("jade.demo.chat.KILL")) {
    // // finish();
    // // }
    // // // if (action.equalsIgnoreCase("jade.demo.chat.SHOW_CHAT")) {
    // // // Intent showChat = new Intent(MainActivity.this,
    // // // DameDePiquePlayerActivity.class);
    // // // showChat.putExtra("nickname", nickname);
    // // // MainActivity.this
    // // // .startActivityForResult(showChat, CHAT_REQUEST);
    // // // }
    // // }
    // }
}
